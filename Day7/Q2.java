import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Q2 {
  public static void main(String[] args) {
    try (BufferedReader br = new BufferedReader(new FileReader("input.txt"))) {
      // 初始行
      String line = br.readLine();
      int len = line.length();
      int start = 0;
      for(int i = 0; i < line.length(); i++) {
        if(line.charAt(i) == 'S'){
          start = i;
          break;
        }
      }
      List<String> lines = new ArrayList<>();
      while((line = br.readLine()) != null) {
       lines.add(line);
      }
      long[][] memo = new long[lines.size()][len];
      for (int i = 0; i < lines.size(); i++) {
        Arrays.fill(memo[i], -1L);
      }
      long res = dfs(lines, 0, start, memo);
      System.out.println(res);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static long dfs(List<String> lines, int row, int pos, long[][] memo) {
    if(row >= lines.size() || pos < 0 || pos >= lines.get(row).length()) {
        return 1;
    }
    if (memo[row][pos] != -1L) {
        return memo[row][pos];
    }
    long res;
    if(lines.get(row).charAt(pos) == '^'){
      res = dfs(lines, row + 1, pos - 1, memo) + dfs(lines, row + 1, pos + 1, memo);
    } else {
      res = dfs(lines, row + 1, pos, memo);
    }
    memo[row][pos] = res;
    return res;
  }
}