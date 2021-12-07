import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Task {

    public static void main(String[] args) throws IOException {

        List<String> inputLines = Files.readAllLines(Paths.get("./input.txt"));
        var inputIntegers = Arrays.stream(inputLines.get(0).split(",")).map(Integer::parseInt).collect(Collectors.toList());
//        task2Solve(inputIntegers);

        System.out.println("Task1 solution: " + task1Solve(inputIntegers, false));
        System.out.println("Task2 solution: " + task1Solve(inputIntegers, true));
    }

    private static long task1Solve(List<Integer> inputIntegers, boolean isTask2) {
        Map<Integer, Integer> mapOfIntegers = inputIntegers.stream().collect(Collectors.toMap(Function.identity(), x -> 1, (a, b) -> a + 1));
        long minFuel = Long.MAX_VALUE;
        int baseLineKey = -1;
        for (int baseLine = 0; baseLine < mapOfIntegers.keySet().stream().max(Integer::compare).get(); baseLine++) {
            long actualFuel = 0;
            for (Integer innerKey : mapOfIntegers.keySet()) {
                if (!isTask2) {
                    actualFuel += (long) Math.abs(innerKey - baseLine) * mapOfIntegers.get(innerKey);
                } else {
                    var fuelWeight = 0;
                    for (long i = 0; i <= Math.abs(innerKey - baseLine); i++) {
                        fuelWeight += i;
                    }
                    actualFuel += fuelWeight * mapOfIntegers.get(innerKey);
                    if (actualFuel > minFuel) {
                        break;
                    }
                }
            }
            if (actualFuel < minFuel) {
                minFuel = actualFuel;
                baseLineKey = baseLine;
            }
        }
        return minFuel;
    }


}