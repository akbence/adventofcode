import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Task {

    public static void main(String[] args) throws IOException {

        List<String> inputLines = Files.readAllLines(Paths.get("./input.txt"));
        List<Integer> inputIntegers = new ArrayList<>();
        for (String line : inputLines) {
            var tempList = Arrays.stream(line.split(","))
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());
            inputIntegers.addAll(tempList);
        }

        System.out.println("Task1 solution: " + taskSolve(inputIntegers,80));
        System.out.println("Task1 solution: " + taskSolve(inputIntegers,256));
    }


    private static BigDecimal taskSolve(List<Integer> inputIntegers,int countTo) {
        var map = inputIntegers
                .stream()
                .collect(Collectors.groupingBy(Integer::intValue))
                .entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> new BigDecimal(e.getValue().size())));
        for (int i = 0; i < countTo; i++) {
            Map<Integer, BigDecimal> newMap = new HashMap<>();
            BigDecimal newFishes = BigDecimal.ZERO;
            for (Integer key : map.keySet()) {
                var value = map.get(key);
                if (key == 0) {
                    newFishes = value;
                    newMap.merge(6, value, BigDecimal::add);
                } else {
                    newMap.merge(key - 1, value, BigDecimal::add);
                }
            }
            newMap.put(8, newFishes);
            map = newMap;
        }

        return map.values().stream().reduce(BigDecimal.ZERO, (BigDecimal::add));
    }


}