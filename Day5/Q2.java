import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Q2 {
  public static void main(String[] args) {
    try (BufferedReader br = new BufferedReader(new FileReader("input.txt"))) {
      String line;
      List<long[]> ranges = new ArrayList<>();
      while ((line = br.readLine()) != null) {
        if (line.trim().isEmpty())
          break;
        String[] range = line.split("-");
        ranges.add(new long[] { Long.parseLong(range[0]), Long.parseLong(range[1]) });
      }
      Collections.sort(ranges, Comparator.comparingLong(a -> a[0]));
      for (int i = 0; i < ranges.size() - 1; i++) {
        if (ranges.get(i)[1] >= ranges.get(i + 1)[0]) {
          ranges.get(i + 1)[0] = Math.min(ranges.get(i)[0], ranges.get(i + 1)[0]);
          ranges.get(i + 1)[1] = Math.max(ranges.get(i)[1], ranges.get(i + 1)[1]);
          ranges.remove(i);
          i--;
        }
      }
      long res = 0L;
      for (long[] range : ranges) {
        res += (range[1] - range[0] + 1);
      }
      System.out.println(res);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}