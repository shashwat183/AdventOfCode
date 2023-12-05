import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Part1 {
  public static void main(String[] args) throws FileNotFoundException {
    var data = new ArrayList<ArrayList<Character>>();
    File file = new File("input.txt");
    Scanner reader = new Scanner(file);

    while (reader.hasNextLine()) {
      String line = reader.nextLine();
      var row = new ArrayList<Character>();
      for (char ch: line.toCharArray()) {
        row.add(ch);
      }
      data.add(row);
    }
    reader.close();

    System.out.println(calcSum(data));
  }

  private static Integer calcSum(List<List<Character>> data) {
    /*
      loop over row, col
      if curr is symbol(not digit or .)
        allDirSrch - find digit
        if digit found
          parse num at currR, currC
    */
    /*
      need to keep a record of already parsed numbers
      can use a map for this. map[Point]->Integer
     */
    for (int r=0; r<data.size(); r++) {
      for (int c=0; c<data.get(0).size(); c++) {
        var ch = data.get(r).get(c);
        if (!Character.isDigit(ch) && ch != '.') {
        }
      }
    }
    return 0;
  }

  private static Integer allDirSrch(List<List<Character>> data, Integer r, Integer c) {
    if (r > 0) {
      if (Character.isDigit(data.get(r-1).get(c))) {

      }
    }
    return 0;
  }

  private static Integer parseNum(List<List<Character>> data, Integer r, Integer c) {

  }

  private static class Point {
    Integer row;
    Integer col;

    public Point(Integer row, Integer col) {
      this.row = row;
      this.col = col;
    }
  }

}
