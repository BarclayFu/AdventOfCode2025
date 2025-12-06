import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Q2 {
  public static void main(String[] args) {
    try (BufferedReader br = new BufferedReader(new FileReader("input.txt"))) {
      List<String> records = new ArrayList<>();
      for (int i = 0; i < 4; i++) {
        String line = br.readLine();
        records.add(line);
      }
      String line = br.readLine();
      // 根据最后一行的字符确定数字开头
      List<Integer> starts = new ArrayList<>();
      List<Character> chars = new ArrayList<>();
      for (int i = 0; i < line.length(); i++) {
        if (line.charAt(i) != ' ') {
          starts.add(i);
          chars.add(line.charAt(i));
        }
      }

      // 根据 starts 定义每列的 [start, end)
      List<int[]> segments = new ArrayList<>();
      for (int i = 0; i < starts.size(); i++) {
        int start = starts.get(i);
        int end = (i + 1 < starts.size()) ? starts.get(i + 1) : line.length();
        segments.add(new int[] { start, end });
      }

      // 切出每个 cell，包含前后空格
      int colCount = segments.size();
      String[][] cells = new String[4][colCount];

      for (int r = 0; r < 4; r++) {
        String l = records.get(r);
        for (int c = 0; c < colCount; c++) {
          int s = segments.get(c)[0];
          int e = Math.min(segments.get(c)[1], l.length());
          cells[r][c] = l.substring(s, e);
        }
      }
      long res = 0;
      for (int i = 0; i < colCount; i++) {
        // 一列数字
        String first = cells[0][i];
        long sum = 0L;
        if(chars.get(i) == '*') sum = 1L;
        // 每一个cell列，长度取第一个的长度就行
        for(int j = first.length() - 1; j >= 0; j--) {
          // 一列中的每一列cell
          int cur = 0;
          for(int k = 0; k < 4; k++){
            if(cells[k][i].charAt(j) != ' '){
              cur = cur * 10 + (cells[k][i].charAt(j) - '0');
            }
          }
          if(cur == 0) continue;
          if(chars.get(i) == '+'){
            sum += cur;
          }else{
            sum *= cur;
          }
        }
        res += sum;
      }
      System.out.println(res);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}