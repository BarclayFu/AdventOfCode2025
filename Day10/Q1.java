import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Q1 {
  public static void main(String[] args) {
    int res = 0;
    try (BufferedReader br = new BufferedReader(new FileReader("input.txt"))) {
      String line;
      while ((line = br.readLine()) != null) {
        line = line.trim();
        int start = line.indexOf('[');
        int end = line.indexOf(']');
        String initialStr = line.substring(start + 1, end);
        boolean[] target = new boolean[initialStr.length()];
        for (int i = 0; i < initialStr.length(); i++) {
            target[i] = (initialStr.charAt(i) == '#');
        }
        List<HashSet<Integer>> flips = new ArrayList<>();
        int idx = end + 1;
        while (true) {
            int l = line.indexOf('(', idx);
            int r = line.indexOf(')', l + 1);
            if (l == -1 || r == -1) break; // 没有更多括号

            String inside = line.substring(l + 1, r).trim();
            if (!inside.isEmpty()) {
                String[] parts = inside.split(",");
                HashSet<Integer> set = new HashSet<>();
                for (int i = 0; i < parts.length; i++) {
                    set.add(Integer.parseInt(parts[i]));
                }
                flips.add(set);
            }
            idx = r + 1;
        }
        int n = initialStr.length();
        int m = flips.size();
        int best = Integer.MAX_VALUE;

        // 暴力枚举所有按钮子集（每个按钮按 0/1 次）
        int totalMasks = 1 << m;
        for (int mask = 0; mask < totalMasks; mask++) {
          int pressCount = Integer.bitCount(mask);
          if (pressCount >= best) continue; // 剪枝：比当前最优还大，没必要算

          boolean[] state = new boolean[n];

          for (int i = 0; i < m; i++) {
            if ((mask & (1 << i)) != 0) {
              HashSet<Integer> set = flips.get(i);
              for (int pos : set) {
                if (pos >= 0 && pos < n) {
                  state[pos] = !state[pos];
                }
              }
            }
          }

          // 判断是否达到目标状态
          boolean ok = true;
          for (int i = 0; i < n; i++) {
            if (state[i] != target[i]) {
              ok = false;
              break;
            }
          }

          if (ok) {
            best = pressCount;
          }
        }

        res += best;
      }
      System.out.println(res);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}