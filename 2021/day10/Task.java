import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Task {

    static List<String> validLines = new ArrayList<>();

    public static void main(String[] args) throws IOException {

        List<String> inputLines = Files.readAllLines(Paths.get("./input.txt"));

        System.out.println("Task1 solution: " + task1Solve(inputLines));
        System.out.println("Task2 solution: " + task2Solve());
    }


    private static int task1Solve(List<String> inputLines) {
        int fullScore = 0;
        for (String line : inputLines) {
            int score = 0;
            var lineList = Arrays.asList(line.split(""));
            List<String> changingList = new ArrayList<>();
            for (int i = 0; i < lineList.size(); i++) {
                var actual = lineList.get(i);
                if (actual.equals("[") || actual.equals("(") || actual.equals("{") || actual.equals("<")) {
                    changingList.add(actual);
                } else {
                    var changeListSize = changingList.size();
                    switch (actual) {
                        case ")":
                            if (changingList.get(changeListSize - 1).equals("(")) {
                                changingList.remove(changeListSize - 1);
                            } else score += 3;
                            break;
                        case "}":
                            if (changingList.get(changeListSize - 1).equals("{")) {
                                changingList.remove(changeListSize - 1);
                            } else score += 1197;
                            break;
                        case ">":
                            if (changingList.get(changeListSize - 1).equals("<")) {
                                changingList.remove(changeListSize - 1);
                            } else score += 25137;
                            break;
                        case "]":
                            if (changingList.get(changeListSize - 1).equals("[")) {
                                changingList.remove(changeListSize - 1);
                            } else score += 57;
                            break;
                    }
                }
                if (score != 0) {
                    fullScore += score;
                    break;
                }
            }
            if (score == 0) {
                validLines.add(line);
            }
        }
        return fullScore;
    }

    private static long task2Solve() {
        List<Long> scoreList = new ArrayList<>();
        for (String line : validLines) {
            long score = 0;

            List<String> lineList = new ArrayList<>(Arrays.asList(line.split("")));
            var done = false;
            while (!done) {
                for (int i = 0; i < lineList.size(); i++) {
                    var actual = lineList.get(i);
                    if (actual.equals("[") && i + 1 < lineList.size() && lineList.get(i + 1).equals("]")
                            || actual.equals("<") && i + 1 < lineList.size() && lineList.get(i + 1).equals(">")
                            || actual.equals("{") && i + 1 < lineList.size() && lineList.get(i + 1).equals("}")
                            || actual.equals("(") && i + 1 < lineList.size() && lineList.get(i + 1).equals(")")) {
                        lineList.remove(i + 1);
                        lineList.remove(i);
                        break;
                    }
                    if (i == lineList.size() - 1) {
                        done = true;
                    }
                }

            }
            while (lineList.size() != 0) {
                var actual = lineList.get(lineList.size() - 1);
                switch (actual) {
                    case "(":
                        score = score * 5 + 1;
                        lineList.remove(lineList.size() - 1);
                        break;
                    case "{":
                        score = score * 5 + 3;
                        lineList.remove(lineList.size() - 1);
                        break;
                    case "<":
                        score = score * 5 + 4;
                        lineList.remove(lineList.size() - 1);
                        break;
                    case "[":
                        score = score * 5 + 2;
                        lineList.remove(lineList.size() - 1);
                        break;
                }
            }
            scoreList.add(score);
        }
        scoreList = scoreList.stream().sorted().collect(Collectors.toList());
        return scoreList.get(scoreList.size()/2);
    }


}