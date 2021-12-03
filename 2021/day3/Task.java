import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Task {

    public static void main(String[] args) throws IOException {

        List<String> inputLines = Files.readAllLines(Paths.get("./input.txt"));
        int length = 0;
        if (inputLines.get(0) != null) {
            for (int i = 0; i < inputLines.get(0).length(); i++) {
                length++;
            }
        }

        System.out.println("Task1 solution: " + task1Solve(inputLines, length));
        System.out.println("Task1 solution: " + task2Solve(inputLines, length));
    }

    private static int task1Solve(List<String> inputLines, int length) {
        List<Integer> sumOfEachBit = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            sumOfEachBit.add(0);
        }

        for (String inputLine : inputLines) {
            for (int i = 0; i < inputLine.length(); i++) {
                if (inputLine.charAt(i) == '1') {
                    sumOfEachBit.set(i, sumOfEachBit.get(i) + 1);
                }
            }
        }
        String binaryText = "";

        for (int i = 0; i < sumOfEachBit.size(); i++) {
            var value = sumOfEachBit.get(i);
            if (value > inputLines.size() / 2) {
                binaryText += "1";
            } else {
                binaryText += "0";
            }
        }
        var gamma = Integer.parseInt(binaryText, 2);
        var epsilonBinary = binaryText.chars().map(e -> e == '1' ? '0' : '1')
                .mapToObj(Character::toString)
                .reduce("", (subtotal, element) -> subtotal + element);
        var epsilon = Integer.parseInt(epsilonBinary, 2);
        return gamma * epsilon;
    }

    private static int task2Solve(List<String> inputLines, int length) {
        List<Integer> sumOfEachBit = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            sumOfEachBit.add(0);
        }

        var co2BinaryString = task2HelperSolver(inputLines, 0, length, '0', '1');
        var oxygenBinaryString = task2HelperSolver(inputLines, 0, length, '1', '0');

        return Integer.parseInt(co2BinaryString, 2) * Integer.parseInt(oxygenBinaryString, 2);
    }

    private static String task2HelperSolver(List<String> inputLines, int position, int length, char bitParam1, char bitParam2) {
        int sumOfPosition = 0;
        for (String inputLine : inputLines) {
            if (inputLine.charAt(position) == '1') {
                sumOfPosition += 1;
            }
        }
        int mostSignificant = inputLines.size() - sumOfPosition <= sumOfPosition ? bitParam1 : bitParam2;
        var remainingLines = inputLines.stream().filter(line -> line.charAt(position) == mostSignificant).collect(Collectors.toList());
        if (remainingLines.size() == 1) {
            return remainingLines.get(0);
        }
        if (position < length) {
            return task2HelperSolver(remainingLines, position + 1, length, bitParam1, bitParam2);
        }
        return null;
    }

}