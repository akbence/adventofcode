import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Task {

    public static void main(String[] args) throws IOException {

        List<String> inputLines = Files.readAllLines(Paths.get("./input.txt"));
        List<List<Integer>> octopuses = new ArrayList<>();
        for (String line : inputLines) {
            var chars = line.split("");
            var intLine = Arrays.stream(chars).mapToInt(Integer::parseInt).boxed().collect(Collectors.toList());
            octopuses.add(intLine);
        }
        List<List<Integer>> octopusesClone = new ArrayList<>();
        octopuses.forEach(line -> octopusesClone.add(new ArrayList<>(line)));

        System.out.println("Task1 solution: " + taskSolve(octopuses, true));
        System.out.println("Task2 solution: " + taskSolve(octopusesClone, false));

    }


    private static int taskSolve(List<List<Integer>> octopuses, Boolean isTask1) {
        int flashed = 0;
        int maxStep = isTask1 ? 100 : 99999;
        for (int step = 0; step < maxStep; step++) {
            int flashedInStep = 0;
            for (List<Integer> octopusLine : octopuses) {
                for (int i = 0; i < octopusLine.size(); i++) {
                    octopusLine.set(i, octopusLine.get(i) + 1);
                }
            }
            var changed = 1;
            Set<String> set = new HashSet<>();
            while (changed != 0) {
                changed--;
                for (int i = 0; i < octopuses.size(); i++) {
                    for (int j = 0; j < octopuses.get(i).size(); j++) {
                        if (octopuses.get(i).get(j) > 9 && !set.contains(i + "," + j)) {
                            set.add(i + "," + j);
                            for (int row = -1; row <= 1; row++) {
                                for (int column = -1; column <= 1; column++) {
                                    if (!(row == 0 && column == 0)
                                            && row + i < octopuses.size() && row + i >= 0
                                            && column + j < octopuses.get(i).size() && column + j >= 0
                                    ) {
                                        octopuses.get(i + row).set(j + column, octopuses.get(i + row).get(j + column) + 1);
                                        if (octopuses.get(i + row).get(j + column) == 10) {
                                            changed++;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            for (int i = 0; i < octopuses.size(); i++) {
                for (int j = 0; j < octopuses.get(i).size(); j++) {
                    if (octopuses.get(i).get(j) > 9) {
                        octopuses.get(i).set(j, 0);
                        flashed++;
                        flashedInStep++;

                    }
                }
            }
            if (!isTask1 && flashedInStep == 100) {
                return step + 1;
            }
        }
        return flashed;
    }


}