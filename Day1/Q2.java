import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Q2 {
  public static void main(String[] args) {
    int start = 50;
    int res = 0;
    try (BufferedReader br = new BufferedReader(new FileReader("input.txt"))) {
      String line;
      while ((line = br.readLine()) != null) {
        char flag = line.charAt(0);
        String numberStr = line.substring(1);
        int num = Integer.parseInt(numberStr);
        if (flag == 'L') {
          res += num / 100;
          if (start != 0 && start - num % 100 <= 0) {
            res++;
          }
          start = (start + 100 - num % 100) % 100;
        } else {
          res += num / 100;
          if (start != 0 && start + num % 100 > 99) {
            res++;
          }
          start = (start + num % 100) % 100;
        }

      }
      System.out.println(res);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
