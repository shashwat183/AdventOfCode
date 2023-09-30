import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Day4 {
  public static void main(String[] args) throws IOException {
    System.out.println(solution2());
  }

  private static Integer solution2() throws IOException {
    List<SecPair> overlapping = new ArrayList<>();
    Files.readAllLines(Path.of("input.txt")).forEach(line -> {
      String[] split = line.split(",");
      NumPair pair1 = new NumPair(Integer.parseInt(split[0].split("-")[0]), Integer.parseInt(split[0].split("-")[1]));
      NumPair pair2 = new NumPair(Integer.parseInt(split[1].split("-")[0]), Integer.parseInt(split[1].split("-")[1]));
      SecPair secPair = new SecPair(pair1, pair2);

      if (secPair.isOverlappingV2()) {
        overlapping.add(secPair);
      }
    });

    return overlapping.size();
  }

  private static Integer solution1() throws IOException {
    List<SecPair> overlapping = new ArrayList<>();
    Files.readAllLines(Path.of("input.txt")).forEach(line -> {
      String[] split = line.split(",");
      NumPair pair1 = new NumPair(Integer.parseInt(split[0].split("-")[0]), Integer.parseInt(split[0].split("-")[1]));
      NumPair pair2 = new NumPair(Integer.parseInt(split[1].split("-")[0]), Integer.parseInt(split[1].split("-")[1]));
      SecPair secPair = new SecPair(pair1, pair2);

      if (secPair.isOverlapping()) {
        overlapping.add(secPair);
      }
    });

    return overlapping.size();
  }

  private static class SecPair {
    NumPair pair1;
    NumPair pair2;

    public SecPair(NumPair pair1, NumPair pair2) {
      this.pair1 = pair1;
      this.pair2 = pair2;
    }

    public Boolean isOverlapping() {
      if (pair1.left <= pair2.left && pair1.right >= pair2.right) {
        return true;
      } else if (pair2.left <= pair1.left && pair2.right >= pair1.right) {
        return true;
      }
      return false;
    }

    public Boolean isOverlappingV2() {
      if (pair1.left < pair2.left && pair1.right < pair2.left) {
        return false;
      } else if (pair1.left > pair2.right && pair1.right > pair2.right) {
        return false;
      }
      return true;
    }
  }
  

  private static class NumPair {
    Integer left;
    Integer right;

    public NumPair(Integer int1, Integer int2) {
      this.left = int1;
      this.right = int2;
    }
  }
}
