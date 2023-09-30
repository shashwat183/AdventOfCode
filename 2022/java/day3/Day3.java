import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day3 {
    public static void main(String[] args) throws IOException {
        System.out.println(solution2());
    }

    public static Integer solution2() throws IOException {
        List<Integer> priorities = new ArrayList<>();
        List<String> lines = Files.readAllLines(Path.of("input.txt"));
        for (int i=0; i<lines.size(); i += 3) {
            String items1 = lines.get(i);
            String items2 = lines.get(i+1);
            String items3 = lines.get(i+2);

            Set<Character> charSet1 = new HashSet<>(List.of(items1.chars().mapToObj(c -> (char) c).toArray(Character[]::new)));
            Set<Character> charSet2 = new HashSet<>(List.of(items2.chars().mapToObj(c -> (char) c).toArray(Character[]::new)));
            Set<Character> charSet3 = new HashSet<>(List.of(items3.chars().mapToObj(c -> (char) c).toArray(Character[]::new)));

            for (Character character: charSet1) {
                if (charSet2.contains(character) && charSet3.contains(character)) {
                    priorities.add(ItemType.of(character).priority);
                }
            }
        }

        return priorities.stream().mapToInt(Integer::intValue).sum();
    }
    private static Integer solution1() throws IOException {
        List<Integer> priorities = new ArrayList<>();
        Files.readAllLines(Path.of("input.txt")).forEach(line -> {
            Rucksack rucksack = new Rucksack(line);
            ItemType errorItem = rucksack.getErrorItem();
            priorities.add(errorItem.priority);
        });

        return priorities.stream().mapToInt(Integer::intValue).sum();
    }

    public enum ItemType {
        LA(1), LB(2), LC(3), LD(4), LE(5), LF(6), LG(7), LH(8), LI(9), LJ(10), LK(11), LL(12), LM(13), LN(14), LO(15), LP(16), LQ(17), LR(18), LS(19), LT(20), LU(21), LV(22), LW(23), LX(24), LY(25), LZ(26), UA(27), UB(28), UC(29), UD(30), UE(31), UF(32), UG(33), UH(34), UI(35), UJ(36), UK(37), UL(38), UM(39), UN(40), UO(41), UP(42), UQ(43), UR(44), US(45), UT(46), UU(47), UV(48), UW(49), UX(50), UY(51), UZ(52);

        final int priority;

        ItemType(int priority) {
            this.priority = priority;
        }

        static ItemType of(Character character) {
            return switch (character) {
                case 'a' -> LA;
                case 'b' -> LB;
                case 'c' -> LC;
                case 'd' -> LD;
                case 'e' -> LE;
                case 'f' -> LF;
                case 'g' -> LG;
                case 'h' -> LH;
                case 'i' -> LI;
                case 'j' -> LJ;
                case 'k' -> LK;
                case 'l' -> LL;
                case 'm' -> LM;
                case 'n' -> LN;
                case 'o' -> LO;
                case 'p' -> LP;
                case 'q' -> LQ;
                case 'r' -> LR;
                case 's' -> LS;
                case 't' -> LT;
                case 'u' -> LU;
                case 'v' -> LV;
                case 'w' -> LW;
                case 'x' -> LX;
                case 'y' -> LY;
                case 'z' -> LZ;
                case 'A' -> UA;
                case 'B' -> UB;
                case 'C' -> UC;
                case 'D' -> UD;
                case 'E' -> UE;
                case 'F' -> UF;
                case 'G' -> UG;
                case 'H' -> UH;
                case 'I' -> UI;
                case 'J' -> UJ;
                case 'K' -> UK;
                case 'L' -> UL;
                case 'M' -> UM;
                case 'N' -> UN;
                case 'O' -> UO;
                case 'P' -> UP;
                case 'Q' -> UQ;
                case 'R' -> UR;
                case 'S' -> US;
                case 'T' -> UT;
                case 'U' -> UU;
                case 'V' -> UV;
                case 'W' -> UW;
                case 'X' -> UX;
                case 'Y' -> UY;
                case 'Z' -> UZ;
                default -> throw new IllegalArgumentException("Unknown symbol: " + character);
            };
        }

    }

    public static class Rucksack {
        String items;
        String leftCompartment;
        String rightCompartment;
        public Rucksack(String items) {
            this.items = items;
            int mid = items.length() / 2;
            this.leftCompartment = items.substring(0, mid);
            this.rightCompartment = items.substring(mid);
        }
        public ItemType getErrorItem() {
            Set<Character> leftCharSet = this.getLeftCompaSet();
            Set<Character> rightCharSet = this.getReftCompaSet();

            for (Character character : leftCharSet) {
                if (rightCharSet.contains(character)) {
                    return ItemType.of(character);
                }
            }

            // There is always a common item so this would never happen
            return ItemType.of('a');
        }

        private Set<Character> getLeftCompaSet() {
            char[] chars = this.leftCompartment.toCharArray();
            Set<Character> charSet = new HashSet<>();
            for (char character : chars) {
                charSet.add(character);
            }
            return charSet;
        }

        private Set<Character> getReftCompaSet() {
            char[] chars = this.rightCompartment.toCharArray();
            Set<Character> charSet = new HashSet<>();
            for (char character : chars) {
                charSet.add(character);
            }
            return charSet;
        }

        public String getItems() {
            return items;
        }
    }
}
