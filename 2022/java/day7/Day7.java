import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Day7 {
  public static void main(String[] args) throws IOException {
    var root = generateFileTree();
    List<Integer> listOfSizes = new ArrayList<>();
    var totalUsedSpace = solution1(root, listOfSizes);
    var totalUnusedSpace = 70000000 - totalUsedSpace;
    var extraSpaceNeeded = 30000000 - totalUnusedSpace;
    System.out.println(extraSpaceNeeded);
    // System.out.println(size);
    // System.out.println(listOfSizes);
    // System.out.println(listOfSizes.stream().mapToInt(Integer::intValue).sum());
    listOfSizes = new ArrayList<>();
    solution2(root, listOfSizes, extraSpaceNeeded);
    System.out.println(listOfSizes.stream().sorted().collect(Collectors.toList()));
    // printTree(root);
  }

  private static Integer solution2(DirectoryNode root, List<Integer> results, Integer extraSpaceNeeded) {
    Integer totalDirSize = 0;
    for (DirectoryNode child : root.children.values()) {
      totalDirSize += solution2(child, results, extraSpaceNeeded);
    }
    totalDirSize += root.sumOfFiles;
    // even though Integer is a non primitive type, it is autoboxed
    if (totalDirSize > extraSpaceNeeded) {
      results.add(totalDirSize);
    }
    return totalDirSize;
  }

  private static Integer solution1(DirectoryNode root, List<Integer> results) {
    Integer totalDirSize = 0;
    for (DirectoryNode child : root.children.values()) {
      totalDirSize += solution1(child, results);
    }
    totalDirSize += root.sumOfFiles;
    if (totalDirSize < 100000) {
      results.add(totalDirSize);
    }
    return totalDirSize;
  }


  // helper
  private static void printTree(DirectoryNode root) {
    StringBuilder output = new StringBuilder(root.name + " [ ");
    for (DirectoryNode child : root.children.values()) {
      output.append(child.name).append(", ");
    }
    output.append("]");
    System.out.println(output);

    for (DirectoryNode child : root.children.values()) {
      printTree(child);
    }
  }

  private static DirectoryNode generateFileTree() throws IOException {
    List<String> input = new ArrayList<>(Files.readAllLines(Path.of("input.txt")));

    DirectoryNode root = new DirectoryNode("/");
    DirectoryNode currDirectory = root;

    for (int i = 1; i < input.size(); i++) {
      String line = input.get(i);
      String[] split = line.split(" ");

      if (split[0].equals("$")) { // line is a command

        if (split[1].equals("cd")) { // line is a cd command
          if (split[2].equals("..")) { // .. means go to parent dir
            currDirectory = currDirectory.parent;
          } else {
            currDirectory = currDirectory.children.get(split[2]);
          }
        } else { // line is a ls command
          // record the next lines as ls output.
          int j = i + 1;

          while (j < input.size()) {
            String lsLine = input.get(j);
            String[] lsSplit = lsLine.split(" ");

            if (lsSplit[0].equals("dir")) { // ls output line is a directory line
              DirectoryNode newNode = new DirectoryNode(lsSplit[1]);
              newNode.setParent(currDirectory);
              currDirectory.addNewChild(lsSplit[1], newNode);
            } else { // ls output line is a file line
              Integer fileSize = Integer.parseInt(lsSplit[0]);
              String file = lsSplit[1];
              currDirectory.addFile(file, fileSize);
            }

            if (j == input.size() - 1) {
              break;
            }

            String[] nextSplit = input.get(j + 1).split(" ");
            if (nextSplit[0].equals("$")) {
              i = j;
              break;
            }
            j++;
          }
        }
      }
    }
    return root;
  }

  private static class DirectoryNode {
    String name;
    Integer sumOfFiles;
    Map<String, DirectoryNode> children;
    DirectoryNode parent;
    Set<String> files;

    public DirectoryNode(String name) {
      this.name = name;
      this.sumOfFiles = 0;
      this.parent = null;
      this.children = new HashMap<>();
      this.files = new HashSet<>();
    }

    public void addNewChild(String name, DirectoryNode child) {
      this.children.put(name, child);
    }

    public void addFile(String file, Integer fileSize) {
      if (this.files.add(file)) {
        this.sumOfFiles += fileSize;
      }
    }

    public void setParent(DirectoryNode parent) {
      this.parent = parent;
    }

    @Override
    public String toString() {
      return name;
    }
  }
}
