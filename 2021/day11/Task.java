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

        System.out.println("Task1 solution: " + task1Solve(octopuses));

    }


    private static int task1Solve(List<List<Integer>> octopuses) {
        int flashed = 0;
        for (int step = 0; step < 2; step++) {

            for (List<Integer> octopusLine : octopuses) {
                for (int i = 0; i < octopusLine.size(); i++) {
                    octopusLine.set(i, octopusLine.get(i) + 1);
                }
            }
            var changed = true;
            while (changed) {
                for (int i = 0; i < octopuses.size(); i++) {
                    for (int j = 0; j < octopuses.get(i).size(); j++) {
                        changed = false;
                        if (octopuses.get(i).get(j) > 9) {
                            System.out.println("current:" + i + ":" + j);
                            for (int row = -1; row < 2; row++) {
                                for (int column = -1; column < 2; column++) {
                                    if (!(row == 0 && column == 0)
                                            && row + i != octopuses.size() && row + i >= 0
                                            && column + j != octopuses.get(i).size() && column + j >= 0
                                    ) {
                                        System.out.println(row + i + ":" + (column + j));
                                        octopuses.get(i + row).set(j + column, octopuses.get(i + row).get(j + column) + 1);
                                        if (octopuses.get(i).get(j) == 9) {
                                            changed = true;
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
                    }
                }
            }
            System.out.println("step "+ step);
            octopuses.forEach(System.out::println);
        }
        return flashed;
    }


}