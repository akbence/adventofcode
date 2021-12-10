import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Task {
    static List<Tuple> lowPositions = new ArrayList<>();

    public static void main(String[] args) throws IOException {

        List<String> inputLines = Files.readAllLines(Paths.get("./input.txt"));
        List<List<Integer>> heightmap = new ArrayList<>();

        for (String line : inputLines) {
            var integerList = Arrays.stream(line.split("")).map(Integer::parseInt).collect(Collectors.toList());
            heightmap.add(integerList);
        }

        System.out.println("Task1 solution: " + task1Solve(heightmap));
        System.out.println("Task2 solution: " + task2Solve(heightmap, lowPositions));
    }

    private static int task1Solve(List<List<Integer>> lines) {
        int sum = 0;
        var lineSize = lines.get(0).size();
        for (int i = 0; i < lines.size(); i++) {
            for (int j = 0; j < lineSize; j++) {
                var actual = lines.get(i).get(j);
                var right = j == lineSize - 1 ? lines.get(i).get(0) : lines.get(i).get(j + 1);
                var left = j == 0 ? lines.get(i).get(lineSize - 1) : lines.get(i).get(j - 1);
                var top = i == 0 ? 10 : lines.get(i - 1).get(j);
                var bottom = i == lines.size() - 1 ? 10 : lines.get(i + 1).get(j);
                if (actual < right && actual < left && actual < top && actual < bottom) {
                    sum += (actual + 1);
                    lowPositions.add(new Tuple(i, j));
                }
            }
        }
        return sum;
    }

    private static int task2Solve(List<List<Integer>> heightmap, List<Tuple> lowPositions) {
        List<Integer> sums = new ArrayList<>();

        for (Tuple lowPosition : lowPositions) {
            Map<String, Boolean> alreadySummed = new HashMap<>();
            System.out.println("LOOP: " + lowPosition.i + ":" + lowPosition.j);
            calcBasin(lowPosition.i, lowPosition.j, heightmap, 0, alreadySummed);
            sums.add(alreadySummed.size());
        }
        return sums.stream().sorted(Collections.reverseOrder()).limit(3).reduce((i1, i2 ) -> i1*i2).get();
    }

    private static void calcBasin(int i, int j, List<List<Integer>> lines, int sum, Map<String, Boolean> alreadySummed) {
        alreadySummed.put(i + "," + j, true);
        var lineSize = lines.get(0).size();
        var actual = lines.get(i).get(j);

        var rightJ = j == lineSize - 1 ? 0 : j + 1;
        var right = lines.get(i).get(rightJ);
        if (actual + 1 == right && right != 9 && alreadySummed.get(i + "," + rightJ) == null) {
            calcBasin(i, rightJ, lines, sum, alreadySummed);
        }

        var leftJ = j == 0 ? lineSize - 1 : j - 1;
        var left = lines.get(i).get(leftJ);

        if (actual + 1 == left && left != 9 && alreadySummed.get(i + "," + leftJ) == null) {
            calcBasin(i, leftJ, lines, sum, alreadySummed);
        }

        var topI = i == 0 ? -1 : i - 1;
        var top = topI == -1 ? 9 : lines.get(topI).get(j);
        if (actual + 1 == top && top != 9 && alreadySummed.get(topI + "," + j) == null) {
            calcBasin(topI, j, lines, sum, alreadySummed);
        }

        var bottomI = i == lines.size() - 1 ? -1 : i + 1;
        var bottom = bottomI == -1 ? 9 : lines.get(bottomI).get(j);
        if (actual + 1 == bottom && bottom != 9 && alreadySummed.get(bottomI + "," + j) == null) {
            calcBasin(bottomI, j, lines, sum, alreadySummed);
        }
    }

    static class Tuple {
        int i;
        int j;

        public Tuple(int i, int j) {
            this.i = i;
            this.j = j;
        }
    }

}