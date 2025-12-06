import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Q1 {
  public static void main(String[] args) {
    try (BufferedReader br = new BufferedReader(new FileReader("input.txt"))) {
      List<String[]> records = new ArrayList<>();
      for (int i = 0; i < 4; i++) {
        String line = br.readLine();
        String[] parts = line.trim().split("\\s+");
        records.add(parts);
      }
      String line = br.readLine();
      String[] parts = line.trim().split("\\s+");
      long res = 0L;
      for (int i = 0; i < parts.length; i++) {
        long sum = 0L;
        if (parts[i].equals("+")) {
          for (String[] record : records) {
            sum += Long.parseLong(record[i]);
          }
        } else {
          sum = 1L;
          for (String[] record : records) {
            sum *= Long.parseLong(record[i]);
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