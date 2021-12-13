import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Task {
    static Set<Tuple> positions = new HashSet<>();
    static List<String> folds = new ArrayList<>();

    public static void main(String[] args) throws IOException {

        List<String> inputLines = Files.readAllLines(Paths.get("./input.txt"));
        for (String inputLine : inputLines) {
            if (inputLine.isBlank()) {
                continue;
            }
            if (inputLine.startsWith("fold")) {
                folds.add(inputLine.split(" ")[2]);
            } else {
                var splitted = inputLine.split(",");
                positions.add(new Tuple(Integer.parseInt(splitted[0]), Integer.parseInt(splitted[1])));
            }
        }

        System.out.println("Task1 solution: " + task1Solve(positions, folds.get(0)).size());
        System.out.println("Task2 solution: "  );
        task2Solve(positions,folds);
    }

    private static void task2Solve(Set<Tuple> positions, List<String> folds) {
        Set<Tuple> startPositions=positions;
        for (String fold : folds) {
            startPositions = task1Solve(startPositions,fold);
        }
        var positionList = new ArrayList<>(startPositions);
        var maxX= positionList.stream().max(Comparator.comparingInt(Tuple::getX)).get().getX();
        positionList.sort(Comparator.comparingInt(Tuple::getY).thenComparingInt(Tuple::getX));
        int lastY=0;
        Set<Integer> linePositions = new HashSet<>();
        for (Tuple tuple : positionList) {
            if(tuple.y == lastY){
                linePositions.add(tuple.x);
            }else {
                String actual ="";
                for (int x = 0; x < maxX+1; x++) {
                    if(linePositions.contains(x)){
                        actual+="#";
                    }else {
                        actual+=".";
                    }
                }
                System.out.println(actual);
                linePositions = new HashSet<>();
                lastY = tuple.y;
                linePositions.add(tuple.x);
            }
        }
        //last line to print
        String actual ="";
        for (int x = 0; x < maxX+1; x++) {
            if(linePositions.contains(x)){
                actual+="#";
            }else {
                actual+=".";
            }
        }
        System.out.println(actual);
    }

    private static Set<Tuple> task1Solve(Set<Tuple> startPositions, String fold) {
        var foldValue = Integer.parseInt(fold.substring(2));
        Set<Tuple> newPositions = new HashSet<>();
        if (fold.startsWith("y")) {
            startPositions.forEach(tuple -> {
                if (tuple.y < foldValue) {
                    newPositions.add(tuple);
                }
                if (tuple.y > foldValue) {
                    newPositions.add(new Tuple(tuple.x, foldValue - (tuple.y - foldValue)));
                }
            });
        } else {
            startPositions.forEach(tuple -> {
                if (tuple.x < foldValue) {
                    newPositions.add(tuple);
                }
                if (tuple.x > foldValue) {
                    newPositions.add(new Tuple(foldValue - (tuple.x - foldValue), tuple.y));
                }
            });
        }
        return newPositions;
    }

    static class Tuple {
        int x;
        int y;

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public Tuple(int i, int j) {
            this.x = i;
            this.y = j;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Tuple tuple = (Tuple) o;
            return x == tuple.x && y == tuple.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        @Override
        public String toString() {
            return "Tuple{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }
    }

}