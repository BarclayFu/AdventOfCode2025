import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Q1 {
  public static void main(String[] args) {
    try (BufferedReader br = new BufferedReader(new FileReader("input.txt"))) {
      List<long[]> positions = new ArrayList<>();
      String line;
      while ((line = br.readLine()) != null) {
        String[] parts = line.split(",");
        long x = Long.parseLong(parts[0]);
        long y = Long.parseLong(parts[1]);
        positions.add(new long[] { x, y });
      }
      long max = 0L;
      for (int i = 0; i < positions.size() - 1; i++) {
        for (int j = i + 1; j < positions.size(); j++) {
          long cur = (long) (Math.abs(positions.get(i)[0] - positions.get(j)[0]) + 1) *
              (Math.abs(positions.get(i)[1] - positions.get(j)[1]) + 1);
          if (cur > max) {
            max = cur;
          }
        }
      }
      System.out.println(max);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}