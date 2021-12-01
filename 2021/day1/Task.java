import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Task {
    private static int incraseCount = 0;
    private static int incraseCount2 = 0;
    public static void main(String[] args) throws IOException {

        List<String> inputLines = Files.readAllLines(Paths.get("./input.txt"));
        var inputIntegers = inputLines.stream().map(Integer::parseInt).collect(Collectors.toList());
        task1Solve(inputIntegers);
        task2Solve(inputIntegers);

        System.out.println("Task1 solution: " + incraseCount);
        System.out.println("Task1 solution: " + incraseCount2);
    }

    private static void task1Solve(List<Integer> inputIntegers) {
        for (int i = 1; i < inputIntegers.size(); i++) {
            if(inputIntegers.get(i-1) < inputIntegers.get(i)){
                incraseCount++;
            }
        }
    }

    private static void task2Solve(List<Integer> inputIntegers) {
        for (int i = 0; i < inputIntegers.size()-3; i++) {
            var a = inputIntegers.get(i) + inputIntegers.get(i+1) + inputIntegers.get(i+2);
            var b = inputIntegers.get(i+1) + inputIntegers.get(i+2) + inputIntegers.get(i+3);
            if(a<b){
                incraseCount2++;
            }
        }
    }

}