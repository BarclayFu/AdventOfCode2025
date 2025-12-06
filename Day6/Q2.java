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
      for (int i = 0; i < line.length(); i++) {
        if (line.charAt(i) != ' ') {
          starts.add(i);
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
      int rowCount = records.size();
      int colCount = segments.size();
      String[][] cells = new String[rowCount][colCount];

      for (int r = 0; r < rowCount; r++) {
        String l = records.get(r);
        for (int c = 0; c < colCount; c++) {
          int s = segments.get(c)[0];
          int e = Math.min(segments.get(c)[1], l.length());
          cells[r][c] = l.substring(s, e);
        }
      }
      for (int i = 0; i < colCount; i++) {

      }

    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}