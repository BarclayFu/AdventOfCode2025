import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Q1 {
  public static void main(String[] args) {
    try (BufferedReader br = new BufferedReader(new FileReader("input.txt"))) {
      String line;
      long sum = 0L;
      while ((line = br.readLine()) != null) {
        int end = line.length();
        int first = 0, idx = -1;
        int second = 0;
        int after = 0;
        // 遍历找到最大的和第二大的数字
        // 如果最大的数字在最后，那么第二大作为十位，第一大作为个位
        // 如果最大的数字不在最后，那么第一大作为十位，第一大后面的最大值作为个位
        for (int i = 0; i < end; i++) {
          // 遍历，找到最大的和第二大
          // 注意这里不能是大于等于
          // 比如8781这种，我们如果等于，那么87会被81覆盖掉
          // 我们希望第一大尽量越靠前越好，所以大于就行，如果相等，直接更新after就行
          if (line.charAt(i) - '0' > first) {
            second = first;
            first = line.charAt(i) - '0';
            idx = i;
            // 有新第一大，第一大后的第二大得重新找
            after = 0;
          } else if (line.charAt(i) - '0' >= after) {
            // 第一大已经找到了没更新，那我们也不用管第二大，直接找第一大后面的最大值就行
            after = line.charAt(i) - '0';
          }
        }
        if (idx == end - 1) {
          sum += (long) second * 10 + first;
        } else {
          sum += (long) first * 10 + after;
        }
      }
      System.out.println(sum);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}