import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class Q2 {

  static class P {
    long x, y;
    P(long x, long y) { this.x = x; this.y = y; }
  }

  public static void main(String[] args) {
    try (BufferedReader br = new BufferedReader(new FileReader("input.txt"))) {
      List<P> poly = new ArrayList<>();
      String line;
      while ((line = br.readLine()) != null) {
        line = line.trim();
        if (line.isEmpty()) continue;
        String[] parts = line.split(",");
        long x = Long.parseLong(parts[0].trim());
        long y = Long.parseLong(parts[1].trim());
        poly.add(new P(x, y));
      }

      int n = poly.size();
      if (n < 3) {
        System.out.println(0);
        return;
      }

      long max = 0L;

      for (int i = 0; i < n - 1; i++) {
        for (int j = i + 1; j < n; j++) {
          P a = poly.get(i);
          P c = poly.get(j);

          long dx = Math.abs(a.x - c.x);
          long dy = Math.abs(a.y - c.y);

          long area;
          try {
            area = Math.multiplyExact(dx + 1, dy + 1);
          } catch (ArithmeticException ex) {
            // 极端情况下溢出：用 BigInteger 也行；这里直接跳过
            continue;
          }
          if (area <= max) continue;

          // 另外两个角
          P b = new P(a.x, c.y);
          P d = new P(c.x, a.y);

          // 1) 两个新角必须在多边形内（含边界）
          if (!pointInPolygonOrOnEdge(b, poly) || !pointInPolygonOrOnEdge(d, poly)) {
            continue;
          }

          // 2) 矩形四条边不能穿越多边形边界（允许端点接触/共线贴边）
          P r1 = new P(Math.min(a.x, c.x), Math.min(a.y, c.y));
          P r2 = new P(Math.max(a.x, c.x), Math.max(a.y, c.y));
          // 规范化矩形四角
          P p1 = new P(r1.x, r1.y);
          P p2 = new P(r2.x, r1.y);
          P p3 = new P(r2.x, r2.y);
          P p4 = new P(r1.x, r2.y);

          // 四条边
          P[] eU = {p1, p2};
          P[] eR = {p2, p3};
          P[] eD = {p3, p4};
          P[] eL = {p4, p1};

          if (rectEdgeCrossesPolygon(eU[0], eU[1], poly)) continue;
          if (rectEdgeCrossesPolygon(eR[0], eR[1], poly)) continue;
          if (rectEdgeCrossesPolygon(eD[0], eD[1], poly)) continue;
          if (rectEdgeCrossesPolygon(eL[0], eL[1], poly)) continue;

          // 3) 你提到的：对角线 AC 不得与任何多边形边相交（允许在端点碰到）
          if (segmentCrossesPolygon(a, c, poly)) continue;

          max = area;
        }
      }

      System.out.println(max);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  // 判定：矩形边是否与多边形边发生“真正穿越式相交”
  // 允许：端点接触、共线重叠/贴边（因为这种不算“超出区域”）
  static boolean rectEdgeCrossesPolygon(P s1, P s2, List<P> poly) {
    int n = poly.size();
    for (int i = 0; i < n; i++) {
      P a = poly.get(i);
      P b = poly.get((i + 1) % n);

      // 如果只是端点接触（共享端点），允许
      if (samePoint(s1, a) || samePoint(s1, b) || samePoint(s2, a) || samePoint(s2, b)) {
        continue;
      }

      // 如果线段发生“严格相交”（proper intersection），就是穿越
      if (segmentsProperIntersect(s1, s2, a, b)) return true;
    }
    return false;
  }

  static boolean segmentCrossesPolygon(P s1, P s2, List<P> poly) {
    int n = poly.size();
    for (int i = 0; i < n; i++) {
      P a = poly.get(i);
      P b = poly.get((i + 1) % n);

      if (samePoint(s1, a) || samePoint(s1, b) || samePoint(s2, a) || samePoint(s2, b)) {
        continue;
      }

      // “相交（包含共线重叠）”按你的口径可能也算不行；
      // 但通常允许共线贴边。你如果想更严格，把 segmentsIntersectInclusive 改成 true 即拒绝。
      if (segmentsProperIntersect(s1, s2, a, b)) return true;
    }
    return false;
  }

  static boolean samePoint(P a, P b) {
    return a.x == b.x && a.y == b.y;
  }

  // 点在多边形内或在边上（ray casting + 边上判定）
  static boolean pointInPolygonOrOnEdge(P p, List<P> poly) {
    int n = poly.size();
    // 边上：算 inside
    for (int i = 0; i < n; i++) {
      P a = poly.get(i);
      P b = poly.get((i + 1) % n);
      if (pointOnSegment(p, a, b)) return true;
    }

    boolean inside = false;
    for (int i = 0, j = n - 1; i < n; j = i++) {
      P pi = poly.get(i);
      P pj = poly.get(j);

      // 判断射线是否穿过边 (pj, pi)
      // (pi.y > p.y) != (pj.y > p.y) 表示点的水平射线与边在 y 方向有交叉
      boolean cond = (pi.y > p.y) != (pj.y > p.y);
      if (cond) {
        BigInteger left = BigInteger.valueOf(p.y - pj.y).multiply(BigInteger.valueOf(pi.x - pj.x));
        BigInteger den  = BigInteger.valueOf(pi.y - pj.y);
        // 比较 p.x 与 pj.x + left/den
        // 为了不做除法：比较 (p.x - pj.x) * den 与 left，注意 den 可能为负
        BigInteger rhs = left;
        BigInteger lhs = BigInteger.valueOf(p.x - pj.x).multiply(den);
        int cmp = lhs.compareTo(rhs);
        if (den.signum() < 0) cmp = -cmp;

        if (cmp < 0) { // p.x < x_intersect
          inside = !inside;
        }
      }
    }
    return inside;
  }

  static boolean pointOnSegment(P p, P a, P b) {
    if (orient(a, b, p) != 0) return false;
    return Math.min(a.x, b.x) <= p.x && p.x <= Math.max(a.x, b.x)
        && Math.min(a.y, b.y) <= p.y && p.y <= Math.max(a.y, b.y);
  }

  // 严格相交：两线段在内部交叉（不含端点接触，不含共线重叠）
  static boolean segmentsProperIntersect(P a, P b, P c, P d) {
    int o1 = orient(a, b, c);
    int o2 = orient(a, b, d);
    int o3 = orient(c, d, a);
    int o4 = orient(c, d, b);

    // 共线不算“proper intersect”
    if (o1 == 0 || o2 == 0 || o3 == 0 || o4 == 0) return false;

    return (o1 != o2) && (o3 != o4);
  }

  // 方向：sign( (b-a) x (c-a) )，用 BigInteger 防溢出
  static int orient(P a, P b, P c) {
    BigInteger x1 = BigInteger.valueOf(b.x - a.x);
    BigInteger y1 = BigInteger.valueOf(b.y - a.y);
    BigInteger x2 = BigInteger.valueOf(c.x - a.x);
    BigInteger y2 = BigInteger.valueOf(c.y - a.y);
    BigInteger cross = x1.multiply(y2).subtract(y1.multiply(x2));
    return cross.signum();
  }
}