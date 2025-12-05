import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Q2 {
  public static void main(String[] args) {
    try (BufferedReader br = new BufferedReader(new FileReader("input.txt"))) {
      String line;
      long sum = 0L;
      while ((line = br.readLine()) != null) {
        StringBuilder sb = new StringBuilder();
        int k = line.length() - 12;
        for (int i = 0; i < line.length(); i++) {
          char c = line.charAt(i);
          while (k > 0 && sb.length() > 0 && sb.charAt(sb.length() - 1) < c) {
            sb.deleteCharAt(sb.length() - 1);
            k--;
          }
          sb.append(c);
        }
        while (k > 0 && sb.length() > 0) {
          sb.deleteCharAt(sb.length() - 1);
          k--;
        }
        sum += Long.parseLong(sb.toString());
      }
      System.out.println(sum);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
