import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Q2 {
  public static void main(String[] args) {
    try (BufferedReader br = new BufferedReader(new FileReader("input.txt"))) {
      String line;
      Long total = 0L;
      while ((line = br.readLine()) != null) {
        String[] parts = line.split(",");
        for (String part : parts) {
          String[] pair = part.split("-");
          Long start = Long.parseLong(pair[0]);
          Long end = Long.parseLong(pair[1]);
          for (long i = start; i <= end; i++) {
            String cur = String.valueOf(i);
            if (cur.length() % 2 == 0) {
              if (cur.substring(0, cur.length() / 2).equals(cur.substring(cur.length() / 2))) {
                total += Long.parseLong(cur);
              }
            }
          }
        }
      }
      System.out.println(total);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}