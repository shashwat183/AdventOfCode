import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day8 {
    public static void main(String[] args) throws IOException {
        System.out.println(solution1());
    }

    private static Integer solution2() {
      return 0;
    }

    private static Integer solution1() throws IOException {
        Integer[][] inputMatrix = new Integer[99][99];

        List<String> inputLines = Files.readAllLines(Path.of("input.txt"));
        for (int i = 0; i < inputLines.size(); i++) {
            Integer[] lineNumbers = inputLines.get(i).chars().mapToObj(Character::getNumericValue)
                    .toArray(Integer[]::new);
            inputMatrix[i] = lineNumbers;
        }

        Node[][] parsingMat = new Node[99][99];

        for (int i = 0; i < inputMatrix.length; i++) {
            for (int j = 0; j < inputMatrix[0].length; j++) {
                Node newNode = new Node();
                if ((i == 0) || (parsingMat[i - 1][j].maxTop < inputMatrix[i][j])) {
                    newNode.visible = true;
                    newNode.maxTop = inputMatrix[i][j];
                } else {
                    newNode.maxTop = parsingMat[i - 1][j].maxTop;
                }
                if ((j == 0) || (parsingMat[i][j - 1].maxLeft < inputMatrix[i][j])) {
                    newNode.visible = true;
                    newNode.maxLeft = inputMatrix[i][j];
                } else {
                    newNode.maxLeft = parsingMat[i][j - 1].maxLeft;
                }
                parsingMat[i][j] = newNode;
            }
        }

        for (int i = 98; i > 0; i--) {
            for (int j = 98; j > 0; j--) {
                Node currNode = parsingMat[i][j];
                if ((i == 98) || (parsingMat[i + 1][j].maxBottom < inputMatrix[i][j])) {
                    currNode.visible = true;
                    currNode.maxBottom = inputMatrix[i][j];
                } else {
                    currNode.maxBottom = parsingMat[i + 1][j].maxBottom;
                }
                if ((j == 98) || (parsingMat[i][j + 1].maxRight < inputMatrix[i][j])) {
                    currNode.visible = true;
                    currNode.maxRight = inputMatrix[i][j];
                } else {
                    currNode.maxRight = parsingMat[i][j + 1].maxRight;
                }
            }
        }

        Integer count = 0;
        for (int i = 0; i < parsingMat.length; i++) {
            for (int j = 0; j < parsingMat[0].length; j++) {
                if (parsingMat[i][j].visible) {
                    count++;
                }
            }
        }

        return count;
    }

    private static class Node {
        Boolean visible;
        Integer maxTop;
        Integer maxBottom;
        Integer maxLeft;
        Integer maxRight;

        public Node() {
            this.visible = false;
            this.maxTop = -1;
            this.maxBottom = -1;
            this.maxLeft = -1;
            this.maxRight = -1;

        }

        @Override
        public String toString() {
            String output = "";
            output += this.visible;
            return output;
        }
    }
}


/*
 * For this problem, i remember the DP problem where we did 2 passes of a matrix from top left to
 * bottom right and then the other way. We can start out by setting all the edge nodes to be
 * visible. Then at each pass we check current node is visible from top, left(in first pass) and
 * bottom, right(in second pass). At each node, we store 2 values - if it is visible and the length
 * of the tallest tree on each direction at the node. This will mark nodes as visible or not and
 * then we can do another pass and check which trees are visible and do a count.
 */
