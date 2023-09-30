import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Day1 {
  public static void main(String[] args) {
    System.out.println(solution2());
  }

  private static Integer solution2() {
    List<List<Integer>> listOfListOfCals = readInput();
    List<Integer> calsPerElf =
        listOfListOfCals.stream().map(listOfCals -> listOfCals.stream().reduce(0, Integer::sum))
            .sorted().collect(Collectors.toList());
    return calsPerElf.get(calsPerElf.size() - 1) + calsPerElf.get(calsPerElf.size() - 2) + calsPerElf.get(calsPerElf.size() - 3);
  }

  private static Integer solution1() {
    List<List<Integer>> listOfListOfCals = readInput();
    List<Integer> calsPerElf =
        listOfListOfCals.stream().map(listOfCals -> listOfCals.stream().reduce(0, Integer::sum))
            .sorted().collect(Collectors.toList());
    return calsPerElf.get(calsPerElf.size() - 1);
  }

  private static List<List<Integer>> readInput() {
    List<List<Integer>> inputCalories = new ArrayList<List<Integer>>();

    try {
      File inputFile = new File("input.txt");
      Scanner reader = new Scanner(inputFile);

      // int i = 0;
      // inputCalories.add(new ArrayList<>());
      //
      // while (reader.hasNextLine()) {
      // String line = reader.nextLine();
      //
      // if (line == "") {
      // i++;
      // inputCalories.add(new ArrayList<>());
      // continue;
      // }
      //
      // inputCalories.get(i).add(Integer.parseInt(line));
      // }

      inputCalories.add(new ArrayList<>());

      for (int i = 0; reader.hasNextLine();) {
        String line = reader.nextLine();

        if (line == "") {
          i++;
          inputCalories.add(new ArrayList<>());
          continue;
        }

        inputCalories.get(i).add(Integer.parseInt(line));
      }

      reader.close();

    } catch (FileNotFoundException e) {
      System.out.println("File not found");
      e.printStackTrace();
    } catch (NumberFormatException e) {
      System.out.println("Wierd number string could not be parsed");
      e.printStackTrace();
    }

    return inputCalories;
  }
}
