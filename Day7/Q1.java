import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

public class Q1 {
  public static void main(String[] args) {
    try (BufferedReader br = new BufferedReader(new FileReader("input.txt"))) {
      // 初始行
      String line = br.readLine();
      int res = 0;
      HashSet<Integer> visited = new HashSet<>();
      for(int i = 0; i < line.length(); i++) {
        if(line.charAt(i) == 'S'){
          visited.add(i);
        }
      }
      while ((line = br.readLine()) != null) {
        HashSet<Integer> newVisited = new HashSet<>();
        for(int i : visited){
          if(line.charAt(i) == '^'){
            res++;
            newVisited.add(i - 1);
            newVisited.add(i + 1);
          }else if(line.charAt(i) == '.'){
            newVisited.add(i);
          }
        }
        visited = newVisited;
      }
      System.out.println(res);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}