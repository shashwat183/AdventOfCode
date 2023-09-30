import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Day6 {
  public static void main(String[] args) throws IOException {
    System.out.println(solution2());
  }

  private static Integer solution2() throws IOException {
    String input = Files.readString(Path.of("input.txt"));
    Set<Character> charSet;

    // load first 4 chars

    for (int i = 0; i < input.length(); i++) {
      charSet = new HashSet<Character>(Arrays.asList(
          input.substring(i, i + 14).chars().mapToObj(c -> (char) c).toArray(Character[]::new)));
      if (charSet.size() == 14) {
        return i + 14;
      }
    }

    return -1;
  }

  private static Integer solution1() throws IOException {
    String input = Files.readString(Path.of("input.txt"));
    Set<Character> charSet;

    // load first 4 chars

    for (int i = 0; i < input.length(); i++) {
      charSet = new HashSet<Character>(Arrays.asList(
          input.substring(i, i + 4).chars().mapToObj(c -> (char) c).toArray(Character[]::new)));
      if (charSet.size() == 4) {
        return i + 4;
      }
    }

    return -1;
  }
}
