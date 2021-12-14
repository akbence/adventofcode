import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Task {

    public static void main(String[] args) throws IOException {

        List<String> inputLines = Files.readAllLines(Paths.get("./input.txt"));
        var startingTemplate = inputLines.get(0);
        inputLines.remove(0);
        inputLines.remove(0);
        Map<String, String> rules = new HashMap<>();
        for (String line : inputLines) {
            var splitted = line.split("->");
            rules.put(splitted[0].trim(), splitted[1].trim());
        }


        System.out.println("Task1 solution: " + task1Solve(startingTemplate, rules));
//        System.out.println("Task1 solution: " + incraseCount2);
    }

    private static long task1Solve(String template, Map<String, String> rules) {
        String newTemplate = "";
        var templateSplitted = template.split("");
        for (int step = 0; step < 10; step++) {
            newTemplate="";
            for (int i = 0; i < templateSplitted.length - 1; i++) {
                var current = templateSplitted[i] + templateSplitted[i + 1];
                var currKey = rules.keySet().stream().filter(key -> key.equals(current)).findFirst().get();
                if (i == 0) {
                    newTemplate = newTemplate + templateSplitted[i] + rules.get(currKey) + templateSplitted[i + 1];
                } else {
                    newTemplate = newTemplate + rules.get(currKey) + templateSplitted[i + 1];
                }
            }
            templateSplitted = newTemplate.split("");
        }
        var groupedBy = Arrays.stream(newTemplate.split("")).collect(Collectors.groupingBy(String::valueOf));
        var max = groupedBy.values().stream().map(List::size).max(Integer::compare).get();
        var min = groupedBy.values().stream().map(List::size).min(Integer::compare).get();
        return max - min;
    }

}