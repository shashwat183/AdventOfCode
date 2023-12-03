import javax.swing.plaf.synth.SynthTextAreaUI;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Day7 {
  public static void main(String[] args) throws IOException {
    var root = generateFileTree();
    Set<String> visited = new HashSet<>();
    List<DirectoryNode> queue = new ArrayList<>();
    queue.add(root);

    while (!queue.isEmpty()) {
      var popped = queue.remove(0);
      if (!visited.add(popped.name)) {
        System.out.println("here is the cultprit " + popped.name);
        System.exit(1);
      }
      System.out.println(popped + popped.children.toString());

      queue.addAll(popped.children);
    }
  }

  // private static void printTree(DirectoryNode root, Set<String> visited) {
  //   visited.add(root.name);
  //   System.out.println(visited);
  //   StringBuilder output = new StringBuilder(root.name + " [ ");
  //   for (DirectoryNode child : root.children) {
  //     output.append(child.name).append(", ");
  //   }
  //   output.append("]");
  //   System.out.println(output);
  //
  //   for (DirectoryNode child : root.children) {
  //     printTree(child, visited);
  //   }
  // }

  private static DirectoryNode generateFileTree() throws IOException {
    List<String> input = new ArrayList<>(Files.readAllLines(Path.of("input.txt")));

    String current_directory = null;
    Map<String, DirectoryNode> map = new HashMap<>();

    int i = 0;

    while (i < input.size()) {
      String line = input.get(i);
      String[] split = line.split(" ");

      if (split[0].equals("$")) { // line is a command
          if (split.length > 2 && split[2].equals("wdtm↴")) {
            System.out.println("reached");
          }

        if (split[1].equals("cd")) { // line is a cd command
          String newCurrentDir = split[2];
          if (split[2].equals("..")) { // .. means go to parent dir
            newCurrentDir = map.get(current_directory).parent.name;
          } else if (!map.containsKey(split[2])) {
            map.put(split[2], new DirectoryNode(split[2]));
          }
          current_directory = newCurrentDir;
        }

        if (split[1].equals("ls")) { // line is a ls command
          // record the next lines as ls output.
          int j = i + 1;

          while (j < input.size()) {
            String lsLine = input.get(j);
            String[] lsSplit = lsLine.split(" ");

            if (lsSplit[0].equals("dir")) { // ls output line is a directory line
              if (!map.containsKey(lsSplit[1])) {
                DirectoryNode newNode = new DirectoryNode(lsSplit[1]);
                map.put(lsSplit[1], newNode);
              }
              map.get(lsSplit[1]).setParent(map.get(current_directory));
              map.get(current_directory).addNewChild(map.get(lsSplit[1]));
            } else { // ls output line is a file line
              Integer fileSize = Integer.parseInt(lsSplit[0]);
              String file = lsSplit[1];
              map.get(current_directory).addFile(file, fileSize);
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
      i++;
    }
    return map.get("/");
  }

  private static class DirectoryNode {
    String name;
    Integer sumOfFiles;
    Set<DirectoryNode> children;
    DirectoryNode parent;
    Set<String> files;

    public DirectoryNode(String name) {
      this.name = name;
      this.sumOfFiles = 0;
      this.parent = null;
      this.children = new HashSet<>();
      this.files = new HashSet<>();
    }

    public void addNewChild(DirectoryNode child) {
      this.children.add(child);
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


/*
 * This problem requires us to simulate the directory traversal steps, generating a file tree
 * structure. Once we have that we can traverse the tree to find directories with specified size.
 * 
 * Thoughts: - Read input line by line. - A line with command can be identified by checking if the
 * first char is '$'. - Every time we encounter a command we do one of the following:- ==Initial
 * Setup==: Create a node type, representing a node in the tree and representing a directory. The node
 * type will also store the sum of size of files directly in the directory. It will also store a
 * list of child nodes(child directories). This means the tree will be a k-ary tree. We also create
 * and store a map of directory name to node of that directory. This is for easy access to the node
 * when we see a directory name in input. We also initialize a current_directory variable to null
 * for the directory traversal simulation. If command is cd "directory", we set current_directory to
 * "directory". We also check if the directory exists in map. If not, we create a new node and
 * add it to the map key=directory name. If command is ls, this marks that the next lines until we
 * see another command denote the contents of the directory. We go into ls-processing mode. For the
 * ls output, if a line is dir "directory", we check if node exists in map, if not we create a node
 * for it and then add it as a child of current_directory. If we see a file, denoted by a numeric
 * first value and then name, we simply add the size of file to total_size of directory. - At the
 * end of the simulation, we will have a directory tree. We then do a post-order DFS to calculate
 * the total_size of a directory including the subdirectories. For every directory whose size comes
 * out less than required, we add that size to result counter. At this point we have our result.
 * 
 */
