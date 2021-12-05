import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Task {

    public static void main(String[] args) throws IOException {

        List<String> inputLines = Files.readAllLines(Paths.get("./input.txt"));
        List<Command> commandList = inputLines.stream().map(Command::createFromInput).collect(Collectors.toList());
        //task2Solve(inputIntegers);

        System.out.println("Task1 solution: " + taskSolver(commandList, false));
        System.out.println("Task1 solution: " + taskSolver(commandList, true));
    }

    private static long taskSolver(List<Command> inputCommands, boolean isTask2) {
        Map<String, Integer> field = new HashMap<>();
        var filteredCommands = inputCommands
                .stream()
                .filter(command -> command.getX1() == command.getX2() || command.getY1() == command.getY2())
                .collect(Collectors.toList());
        if (isTask2) {
            var diagonalCommands = inputCommands
                    .stream()
                    .filter(command -> Math.abs(command.getX1() - command.getX2()) == Math.abs(command.getY1() - command.getY2()))
                    .collect(Collectors.toList());
            filteredCommands.addAll(diagonalCommands);
        }
        filteredCommands.forEach(command -> calcFieldValues(command, field));
        return field.entrySet().stream().filter(entry -> entry.getValue() >= 2).count();

    }

    static void calcFieldValues(Command command, Map<String, Integer> field) {
        if (command.getX1() == command.getX2()) {
            var lower = Math.min(command.getY1(), command.getY2());
            for (int i = 0; i <= Math.abs(command.y1 - command.y2); i++) {
                var key = command.x1 + "," + (lower + i);
                field.merge(key, 1, Integer::sum);
            }
        } else if (command.getY1() == command.getY2()) {
            var lower = Math.min(command.getX1(), command.getX2());
            for (int i = 0; i <= Math.abs(command.x1 - command.x2); i++) {
                var key = (lower + i) + "," + command.y1;
                field.merge(key, 1, Integer::sum);
            }
        } else {
            var xSign = command.getX1() < command.getX2() ? 1 : -1;
            var ySign = command.getY1() < command.getY2() ? 1 : -1;
            for (int i = 0; i <= Math.abs(command.x1 - command.x2); i++) {
                var key = (command.getX1() + i * xSign) + "," + (command.getY1() + i * ySign);
                field.merge(key, 1, Integer::sum);
            }
        }

    }


    static class Command {
        private int x1, x2, y1, y2;

        public Command(int x1, int x2, int y1, int y2) {
            this.x1 = x1;
            this.x2 = x2;
            this.y1 = y1;
            this.y2 = y2;
        }

        public int getX1() {
            return x1;
        }

        public void setX1(int x1) {
            this.x1 = x1;
        }

        public int getX2() {
            return x2;
        }

        public void setX2(int x2) {
            this.x2 = x2;
        }

        public int getY1() {
            return y1;
        }

        public void setY1(int y1) {
            this.y1 = y1;
        }

        public int getY2() {
            return y2;
        }

        public void setY2(int y2) {
            this.y2 = y2;
        }

        @Override
        public String toString() {
            return "Command{" +
                    x1 +
                    "," + y1 +
                    " -> " + x2 +
                    "," + y2 +
                    '}';
        }

        public static Command createFromInput(String inputLine) {
            var splitted = inputLine.split("->");
            var from = splitted[0].trim().split(",");
            var to = splitted[1].trim().split(",");
            return new Command(Integer.parseInt(from[0]), Integer.parseInt(to[0]), Integer.parseInt(from[1]), Integer.parseInt(to[1]));
        }
    }


}