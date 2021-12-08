import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Task {
    public static void main(String[] args) throws IOException {

        List<String> inputLines = Files.readAllLines(Paths.get("./input.txt"));
        List<Line> lines = inputLines.stream().map(Line::new).collect(Collectors.toList());

        System.out.println("Task1 solution: " + task1Solve(lines));
        System.out.println("Task1 solution: " + task2Solve(lines));
    }

    private static Long task1Solve(List<Line> lines) {
        return lines.stream()
                .map(line -> line.result
                        .stream()
                        .filter(digit -> (digit.length() == 2) || (digit.length() == 4) || (digit.length() == 3) || (digit.length() == 7))
                        .count())
                .mapToLong(Long::longValue)
                .sum();
    }

    private static int task2Solve(List<Line> lines) {
        int sum = 0;
        for (Line line : lines) {
            Set<String> one = line.information.stream().filter(digit -> digit.length() == 2).map(e -> e.split("")).flatMap(Stream::of).collect(Collectors.toSet());
            Set<String> four = line.information.stream().filter(digit -> digit.length() == 4).map(e -> e.split("")).flatMap(Stream::of).collect(Collectors.toSet());
            Set<String> seven = line.information.stream().filter(digit -> digit.length() == 3).map(e -> e.split("")).flatMap(Stream::of).collect(Collectors.toSet());
            Set<String> eight = line.information.stream().filter(digit -> digit.length() == 7).map(e -> e.split("")).flatMap(Stream::of).collect(Collectors.toSet());

            Map<String, Integer> countMap = new HashMap<>();
            for (String information : line.information) {
                Arrays.stream(information.split("")).forEach(element -> countMap.merge(element, 1, Integer::sum));
            }
            String b = "";
            String e = "";
            String f = "";
            for (Map.Entry<String, Integer> entry : countMap.entrySet()) {
                String key = entry.getKey();
                Integer value = entry.getValue();
                switch (value) {
                    case 6:
                        b = key;
                        break;
                    case 4:
                        e = key;
                        break;
                    case 9:
                        f = key;
                        break;
                }
            }
            //b,e,f done

            Set<String> aSet = new HashSet<>(seven);
            aSet.removeAll(one);
            String a = aSet.stream().findFirst().get();
            //a done

            Set<String> eg = new HashSet<>(eight);
            eg.removeAll(four);
            eg.removeAll(seven);
            // eg done

            Set<String> cSet = new HashSet<>(one);
            cSet.remove(f);
            String c = cSet.stream().findFirst().get();
            //c done
            Set<String> dSet = new HashSet<>(four);
            dSet.removeAll(List.of(b, c, f));
            String d = dSet.stream().findFirst().get();
            //d done
            Set<String> gSet = new HashSet<>(eight);
            gSet.removeAll(List.of(a, b, c, d, e, f));
            String g = gSet.stream().findFirst().get();
            //g done

            Set<String> zero = Set.of(a, b, c, e, f, g);
            Set<String> two = Set.of(a, c, d, e, g);
            Set<String> three = Set.of(a, c, d, f, g);
            Set<String> five = Set.of(a, b, d, f, g);
            Set<String> six = Set.of(a, b, d, e, f, g);
            Set<String> nine = Set.of(a, b, c, d, f, g);

            String result = "";
            for (String number : line.result) {
                var numberSet = Arrays.stream(number.split("")).collect(Collectors.toSet());
                if (numberSet.equals(zero)) {
                    result += "0";
                }
                if (numberSet.equals(one)) {
                    result += "1";
                }
                if (numberSet.equals(two)) {
                    result += "2";
                }
                if (numberSet.equals(three)) {
                    result += "3";
                }
                if (numberSet.equals(four)) {
                    result += "4";
                }
                if (numberSet.equals(five)) {
                    result += "5";
                }
                if (numberSet.equals(six)) {
                    result += "6";
                }
                if (numberSet.equals(seven)) {
                    result += "7";
                }
                if (numberSet.equals(eight)) {
                    result += "8";
                }
                if (numberSet.equals(nine)) {
                    result += "9";
                }
            }
            var resultInt = Integer.parseInt(result);
            sum += resultInt;
        }
        return sum;
    }

    static class Line {
        List<String> information;
        List<String> result;

        public Line(String plainLine) {
            var parts = plainLine.split("\\|");
            this.information = Arrays.stream(parts[0].trim().split(" ")).collect(Collectors.toList());
            this.result = Arrays.stream(parts[1].trim().split(" ")).collect(Collectors.toList());
        }
    }
}