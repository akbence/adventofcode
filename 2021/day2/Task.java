import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Task {

    public static void main(String[] args) throws IOException {

        List<String> inputLines = Files.readAllLines(Paths.get("./input.txt"));

        System.out.println("Task1 solution: " + task1Solve(inputLines));
        System.out.println("Task1 solution: " + task2Solve(inputLines));
    }

    private static int task1Solve(List<String> inputLines) {
        int horizontal = 0, depth = 0;
        for (String line : inputLines) {
            var command = line.split(" ");
            int value = Integer.parseInt(command[1]);
            switch (command[0]) {
                case "forward" -> horizontal += value;
                case "down" -> depth += value;
                case "up" -> depth -= value;
            }
        }
        return horizontal * depth;
    }

    private static int task2Solve(List<String> inputLines) {
        int horizontal = 0, depth = 0, aim = 0;
        for (String line : inputLines) {
            var command = line.split(" ");
            int value = Integer.parseInt(command[1]);
            switch (command[0]) {
                case "forward" -> {
                    horizontal += value;
                    depth += aim * value;
                }
                case "down" -> aim += value;
                case "up" -> aim -= value;
            }
        }
        return horizontal * depth;
    }


}