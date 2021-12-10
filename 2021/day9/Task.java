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
                var right = j == lineSize - 1 ? 10 : lines.get(i).get(j + 1);
                var left = j == 0 ? 10 : lines.get(i).get(j - 1);
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
            Set<String> alreadySummed = new HashSet<>();
            System.out.println("LOOP: " + lowPosition.i + ":" + lowPosition.j);
            calcBasin(lowPosition.i, lowPosition.j, heightmap, alreadySummed);
            sums.add(alreadySummed.size());
        }
        return sums.stream().sorted(Collections.reverseOrder()).limit(3).reduce((i1, i2) -> i1 * i2).get();
    }

    private static void calcBasin(int i, int j, List<List<Integer>> lines, Set<String> alreadySummed) {
        alreadySummed.add(i + "," + j);
        var lineSize = lines.get(0).size();
        var actual = lines.get(i).get(j);

        var rightJ = j == lineSize - 1 ? -1 : j + 1;
        var right = rightJ == -1 ? 9 : lines.get(i).get(rightJ);
        if (actual < right && right != 9 && !alreadySummed.contains(i + "," + rightJ)) {
            calcBasin(i, rightJ, lines, alreadySummed);
        }

        var leftJ = j == 0 ? -1 : j - 1;
        var left = leftJ == -1 ? 9 : lines.get(i).get(leftJ);

        if (actual < left && left != 9 && !alreadySummed.contains(i + "," + leftJ)) {
            calcBasin(i, leftJ, lines, alreadySummed);
        }

        var topI = i == 0 ? -1 : i - 1;
        var top = topI == -1 ? 9 : lines.get(topI).get(j);
        if (actual < top && top != 9 && !alreadySummed.contains(topI + "," + j)) {
            calcBasin(topI, j, lines, alreadySummed);
        }

        var bottomI = i == lines.size() - 1 ? -1 : i + 1;
        var bottom = bottomI == -1 ? 9 : lines.get(bottomI).get(j);
        if (actual < bottom && bottom != 9 && !alreadySummed.contains(bottomI + "," + j)) {
            calcBasin(bottomI, j, lines, alreadySummed);
        }
        System.out.println("AT: " + i + "," + j + " => " + alreadySummed);
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