import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Q1 {
  public static void main(String[] args) {
    try (BufferedReader br = new BufferedReader(new FileReader("input.txt"))) {
      String line;
      List<List<Character>> map = new ArrayList<>();
      while ((line = br.readLine()) != null) {
        List<Character> charList = new ArrayList<>();
        for (char c : line.toCharArray()) {
          charList.add(c);
        }
        map.add(charList);
      }
      int res = 0;
      for (int i = 0; i < map.size(); i++) {
        for (int j = 0; j < map.get(i).size(); j++) {
          if (check(map, i, j)) {
            res++;
          }
        }
      }
      System.out.println(res);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static boolean check(List<List<Character>> map, int i, int j) {
    int count = 0;
    if (i > 0) {
      if (j > 0 && map.get(i - 1).get(j - 1) == '@') {
        count++;
      }
      if (j < map.get(i).size() - 1 && map.get(i - 1).get(j + 1) == '@') {
        count++;
      }
      if (map.get(i - 1).get(j) == '@') {
        count++;
      }
    }
    if (i < map.size() - 1) {
      if (j > 0 && map.get(i + 1).get(j - 1) == '@') {
        count++;
      }
      if (j < map.get(i).size() - 1 && map.get(i + 1).get(j + 1) == '@') {
        count++;
      }
      if (map.get(i + 1).get(j) == '@') {
        count++;
      }
    }
    if (j > 0 && map.get(i).get(j - 1) == '@') {
      count++;
    }
    if (j < map.get(i).size() - 1 && map.get(i).get(j + 1) == '@') {
      count++;
    }
    return count < 4 && map.get(i).get(j) == '@';
  }
}