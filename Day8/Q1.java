import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class Q1 {
  public static void main(String[] args) {
    try (BufferedReader br = new BufferedReader(new FileReader("input.txt"))) {
      int[][] positions = new int[1000][3];
      int p = 0;
      String line;
      while ((line = br.readLine()) != null) {
        String[] parts = line.split(",");
        int x = Integer.parseInt(parts[0]);
        int y = Integer.parseInt(parts[1]);
        int z = Integer.parseInt(parts[2]);
        positions[p][0] = x;
        positions[p][1] = y;
        positions[p][2] = z;
        p++;
      }
      List<long[]> edges = new ArrayList<>();
      for (int i = 0; i < p; i++) {
        for (int j = i + 1; j < p; j++) {
          int xi = positions[i][0], yi = positions[i][1], zi = positions[i][2];
          int xj = positions[j][0], yj = positions[j][1], zj = positions[j][2];
          long dx = (long) xi - xj;
          long dy = (long) yi - yj;
          long dz = (long) zi - zj;
          long dist2 = dx * dx + dy * dy + dz * dz;
          edges.add(new long[] { i, j, dist2 });
        }
      }
      Collections.sort(edges, (a, b) -> {
        return Long.compare(a[2], b[2]);
      });
      UF uf = new UF(p);
      for (int i = 0; i <= 1000; i++) {
        long[] edge = edges.get(i);
        int u = (int) edge[0];
        int v = (int) edge[1];
        long weight = edge[2];
        uf.union(u, v);
      }
      List<Integer> comps = new ArrayList<>();
      for (int i = 0; i < p; i++) {
        if (uf.find(i) == i) {
          comps.add(uf.size[i]);
        }
      }
      Collections.sort(comps, Collections.reverseOrder());
      long result = 1;
      for (int i = 0; i < Math.min(3, comps.size()); i++) {
        result *= comps.get(i);
      }
      System.out.println(result);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}

class UF {
  private int[] parent;
  private int count;
  public int[] size;

  public UF(int n) {
    parent = new int[n];
    size = new int[n];
    count = n;
    for (int i = 0; i < n; i++) {
      parent[i] = i;
      size[i] = 1;
    }
  }

  public int find(int p) {
    while (p != parent[p]) {
      parent[p] = parent[parent[p]]; // path compression
      p = parent[p];
    }
    return p;
  }

  public boolean union(int p, int q) {
    int rootP = find(p);
    int rootQ = find(q);
    if (rootP == rootQ)
      return false;

    if (size[rootP] < size[rootQ]) {
      parent[rootP] = rootQ;
      size[rootQ] += size[rootP];
    } else {
      parent[rootQ] = rootP;
      size[rootP] += size[rootQ];
    }
    count--;
    return true;
  }

  public boolean connected(int p, int q) {
    return find(p) == find(q);
  }

  public int count() {
    return count;
  }
}