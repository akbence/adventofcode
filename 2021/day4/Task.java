import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Task {

    public static void main(String[] args) throws IOException {

        List<String> inputLines = Files.readAllLines(Paths.get("./input.txt"));
        var bingoNumbers = Arrays.stream(inputLines.get(0).split(",")).mapToInt(Integer::parseInt).boxed().collect(Collectors.toList());
        List<BingoTicket> bingoTickets1 = BingoTicket.createBingoTickets(inputLines);
        System.out.println("Task1 solution: " + taskSolve(bingoNumbers, bingoTickets1, false));
        List<BingoTicket> bingoTickets2 = BingoTicket.createBingoTickets(inputLines);
        System.out.println("Task2 solution: " + taskSolve(bingoNumbers, bingoTickets2, true));
    }

    private static int taskSolve(List<Integer> bingoNumbers, List<BingoTicket> bingoTickets, boolean isTask2) {
        LinkedHashMap<Integer, Integer> winRecords = new LinkedHashMap<>();
        for (int actualBingoIndex = 0; actualBingoIndex < bingoNumbers.size(); actualBingoIndex++) {
            for (BingoTicket bingoTicket : bingoTickets) {
                for (int row = 0; row < 5; row++) {
                    for (int column = 0; column < 5; column++) {
                        if (bingoTicket.getFields()[row][column].equals(bingoNumbers.get(actualBingoIndex))) {
                            bingoTicket.getMarked()[row][column] = true;
                        }
                    }
                }
            }
            for (int i = 0, bingoTicketsSize = bingoTickets.size(); i < bingoTicketsSize; i++) {
                BingoTicket bingoTicket = bingoTickets.get(i);
                if (bingoTicket.isWon(true, bingoTicket.marked)) {
                    if (isTask2) {
                        if (winRecords.get(i) == null) {
                            winRecords.put(i, bingoNumbers.get(actualBingoIndex) * bingoTicket.sumOfUnmarked());
                        }
                    } else {
                        return bingoNumbers.get(actualBingoIndex) * bingoTicket.sumOfUnmarked();
                    }
                }
            }
        }

        var recordOpt = winRecords.entrySet().stream().reduce((first, second) -> second);
        if (recordOpt != null) {
            return recordOpt.get().getValue();
        } else return 0;
    }

    static class BingoTicket {
        private Integer[][] fields;
        private Boolean[][] marked;

        @Override
        public String toString() {
            return "BingoTicket{\n" +
                    Arrays.toString(fields[0]) + Arrays.toString(marked[0]) +
                    "\n" +
                    Arrays.toString(fields[1]) + Arrays.toString(marked[1]) +
                    "\n" +
                    Arrays.toString(fields[2]) + Arrays.toString(marked[2]) +
                    "\n" +
                    Arrays.toString(fields[3]) + Arrays.toString(marked[3]) +
                    "\n" +
                    Arrays.toString(fields[4]) + Arrays.toString(marked[4]) +
                    '}' + "\n";
        }

        public Integer[][] getFields() {
            return fields;
        }

        public void setFields(Integer[][] fields) {
            this.fields = fields;
        }

        public Boolean[][] getMarked() {
            return marked;
        }

        public void setMarked(Boolean[][] marked) {
            this.marked = marked;
        }

        public BingoTicket(Integer[][] fields, Boolean[][] marked) {
            this.fields = fields;
            this.marked = marked;
        }

        public static List<BingoTicket> createBingoTickets(List<String> inputIntegers) {
            List<BingoTicket> list = new ArrayList<>();
            for (int i = 2; i < inputIntegers.size(); i++) {
                if (inputIntegers.get(i).isEmpty()) {
                    continue;
                } else {
                    Integer[][] fields = new Integer[5][5];
                    Boolean[][] marked = new Boolean[5][5];
                    for (int rows = 0; rows < 5; rows++) {
                        var numbers = Arrays
                                .stream(inputIntegers.get(i + rows).split(" "))
                                .filter(e -> !e.isEmpty())
                                .collect(Collectors.toList())
                                .toArray(new String[5]);

                        for (int columns = 0; columns < 5; columns++) {
                            fields[rows][columns] = Integer.parseInt(numbers[columns]);
                            marked[rows][columns] = false;
                        }
                    }
                    list.add(new BingoTicket(fields, marked));
                    i += 4;
                }
            }
            return list;
        }

        public boolean isWon(boolean transpose, Boolean[][] A) {
            boolean isWon = true;
            for (int i = 0; i < 5; i++) {
                isWon = true;
                for (int j = 0; j < 5; j++) {
                    if (!A[i][j]) {
                        isWon = false;
                        break;
                    }
                }
                if (isWon) {
                    return true;
                }
            }
            if (transpose) {
                return isWon(false, transpose(A));
            }

            return isWon;
        }

        public static Boolean[][] transpose(Boolean[][] A) {
            Boolean[][] C = new Boolean[5][5];
            for (int i = 0; i < 5; i++)
                for (int j = 0; j < 5; j++)
                    C[j][i] = A[i][j];
            return C;
        }

        public int sumOfUnmarked() {
            int sum = 0;
            for (int row = 0; row < 5; row++) {
                for (int column = 0; column < 5; column++) {
                    if (!marked[row][column]) {
                        sum += fields[row][column];
                    }
                }
            }
            return sum;
        }
    }


}