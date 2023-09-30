import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class Day5 {
  public static void main(String[] args) throws IOException {
    System.out.println(solution2());
  }

  private static String solution2() throws IOException {
    File inputFile = new File("input.txt");
    Scanner reader = new Scanner(inputFile);

    String[] stackInput = new String[8];
    for (int i = 0; i < 8; i++) {
      stackInput[i] = reader.nextLine();
    }

    var stacks = StacksContainer.fromStrings(stackInput);


    // skip next two lines to reach moves section
    reader.nextLine();
    reader.nextLine();

    while (reader.hasNextLine()) {
      String line = reader.nextLine();
      String[] split = line.split(" ");


      Integer countToMove = Integer.parseInt(split[1]);
      Integer from = Integer.parseInt(split[3]);
      Integer to = Integer.parseInt(split[5]);

      stacks.executeMove2(countToMove, from, to);
    }

    reader.close();

    String result = "";

    for (Stack stack : stacks.stacks) {
      result += stack.pop();
    }

    return result;
  }

  private static String solution1() throws IOException {
    File inputFile = new File("input.txt");
    Scanner reader = new Scanner(inputFile);

    String[] stackInput = new String[8];
    for (int i = 0; i < 8; i++) {
      stackInput[i] = reader.nextLine();
    }

    var stacks = StacksContainer.fromStrings(stackInput);


    // skip next two lines to reach moves section
    reader.nextLine();
    reader.nextLine();

    while (reader.hasNextLine()) {
      String line = reader.nextLine();
      String[] split = line.split(" ");


      Integer countToMove = Integer.parseInt(split[1]);
      Integer from = Integer.parseInt(split[3]);
      Integer to = Integer.parseInt(split[5]);

      stacks.executeMove1(countToMove, from, to);
    }

    reader.close();

    String result = "";

    for (Stack stack : stacks.stacks) {
      result += stack.pop();
    }

    return result;
  }

  public static class StacksContainer {
    List<Stack<Character>> stacks;

    public StacksContainer(List<Stack<Character>> stacks) {
      this.stacks = stacks;
    }

    public static StacksContainer fromStrings(String[] strings) {
      List<Stack<Character>> stacks = new ArrayList<>();
      List<String> stringsList = Arrays.asList(strings);

      // we have 9 stacks, we initialize them here
      for (int i = 0; i < 9; i++) {
        stacks.add(new Stack<>());
      }

      Collections.reverse(stringsList);
      stringsList.forEach(line -> {
        int j = 0;
        for (int i = 0; i < line.length(); i += 4) {
          if (line.charAt(i) == '[') {
            stacks.get(j).add(Character.valueOf(line.charAt(i + 1)));
          }
          j++;
        }
      });

      return new StacksContainer(stacks);
    }

    public void executeMove1(Integer countToMove, Integer from, Integer to) {
      for (int i = 0; i < countToMove; i++) {
        Character popped = stacks.get(from - 1).pop();
        stacks.get(to - 1).push(popped);
      }
    }

    public void executeMove2(Integer countToMove, Integer from, Integer to) {
      List<Character> movedCrates = new ArrayList<>();

      for (int i = 0; i < countToMove; i++) {
        Character popped = stacks.get(from - 1).pop();
        movedCrates.add(popped);
      }

      for (int i = movedCrates.size() - 1; i >= 0; i--) {
        stacks.get(to - 1).push(movedCrates.get(i));
      }

    }

    @Override
    public String toString() {
      String result = "";
      for (Stack stack : stacks) {
        result += stack.toString() + "\n";
      }

      return result;
    }
  }
}
