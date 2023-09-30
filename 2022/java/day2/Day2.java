import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Day2 {
  public static void main(String[] args) {
    System.out.println(solution2());
  }

  private static Integer solution2() {
    // X - loss, Y - draw, Z - win
    var input = readInput();
    return input.stream()
        .map(move -> getScoreForMove2(move.split(" ")[1])
            + getScoreForStrat2(move.split(" ")[0], move.split(" ")[1]))
        .mapToInt(Integer::valueOf).sum();
  }

  private static Integer solution1() {
    var input = readInput();
    return input.stream()
        .map(move -> getScoreForMove(move.split(" ")[1])
            + getScoreForStrat1(move.split(" ")[0], move.split(" ")[1]))
        .mapToInt(Integer::valueOf).sum();
  }

  private static Integer getScoreForMove2(String r) {
    switch (r) {
      case "X":
        return 0;
      case "Y":
        return 3;
      case "Z":
        return 6;
    }

    return -1;
  }

  private static Integer getScoreForStrat2(String l, String r) {
    var score = 0;

    switch (String.format("%s %s", l, r)) {
      case "A X":
        return getScoreForMove("Z");
      case "A Y":
        return getScoreForMove("X");
      case "A Z":
        return getScoreForMove("Y");
      case "B X":
        return getScoreForMove("X");
      case "B Y":
        return getScoreForMove("Y");
      case "B Z":
        return getScoreForMove("Z");
      case "C X":
        return getScoreForMove("Y");
      case "C Y":
        return getScoreForMove("Z");
      case "C Z":
        return getScoreForMove("X");
    }
    return score;
  }

  private static Integer getScoreForStrat1(String pl1Move, String pl2Move) {
    var score = 0;

    switch (String.format("%s %s", pl1Move, pl2Move)) {
      case "A X":
        score += 3;
        break;
      case "A Y":
        score += 6;
        break;
      case "A Z":
        score += 0;
        break;
      case "B X":
        score += 0;
        break;
      case "B Y":
        score += 3;
        break;
      case "B Z":
        score += 6;
        break;
      case "C X":
        score += 6;
        break;
      case "C Y":
        score += 0;
        break;
      case "C Z":
        score += 3;
        break;
    }
    return score;
  }

  private static Integer getScoreForMove(String move) {
    switch (move) {
      case "X":
        return 1;
      case "Y":
        return 2;
      case "Z":
        return 3;
    }
    return -1;
  }

  private static List<String> readInput() {
    var input = new ArrayList<String>();
    try {
      File inputFile = new File("input.txt");
      Scanner reader = new Scanner(inputFile);


      while (reader.hasNextLine()) {
        String line = reader.nextLine();
        input.add(line);
      }

      reader.close();

    } catch (FileNotFoundException e) {
      System.out.println("input.txt not found");
      e.printStackTrace();
    }

    return input;
  }
}
