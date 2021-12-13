import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Task {
    static List<Node> nodeList = new ArrayList<>();
    static Set<String> paths = new HashSet<>();
    static Set<String> paths2 = new HashSet<>();

    public static void main(String[] args) throws IOException {

        List<String> inputLines = Files.readAllLines(Paths.get("./input.txt"));
        nodeList.add(new Node(true));
        nodeList.add(new Node(false));
        inputLines.forEach(e -> {
            Node.create(e, 0, 1);
            Node.create(e, 1, 0);
        });
        task1Solve();
        task2Solve();

        System.out.println("Task1 solution: " + paths.size());
        System.out.println("Task2 solution: " + paths2.size());
    }

    private static void task1Solve() {
        visit(nodeList.get(0), "start", new HashSet<>(), false, false);
    }

    private static void task2Solve() {
        visit(nodeList.get(0), "start", new HashSet<>(), true, false);
    }

    private static void visit(Node node, String path, Set<String> visited, boolean isTask2, boolean twiceVisited) {
        if (node.isEnd) {
            if (isTask2) {
                paths2.add(path);
            } else {
                paths.add(path);
            }
            return;
        }
        visited.add(node.name);
        node.connections.forEach(connection -> {
            var optNode = nodeList.stream()
                    .filter(e -> e.name.equals(connection)).findFirst();
            if (optNode.isPresent()) {
                var nextNode = optNode.get();
                if (!isTask2) {
                    if ((nextNode.isBig || !visited.contains(nextNode.name)) && !nextNode.isStart) {
                        visit(nextNode, path + nextNode.name, new HashSet<>(visited), isTask2, twiceVisited);
                    }
                } else {
                    if (!nextNode.isStart) {
                        if (nextNode.isBig) {
                            visit(nextNode, path + nextNode.name, new HashSet<>(visited), isTask2, twiceVisited);
                        } else if (!visited.contains(nextNode.name)) {
                            visit(nextNode, path + nextNode.name, new HashSet<>(visited), isTask2, twiceVisited);
                        } else if (visited.contains(nextNode.name) && !twiceVisited) {
                            visit(nextNode, path + nextNode.name, new HashSet<>(visited), isTask2, true);
                        }
                    }


                }
            }
        });
    }

    static class Node {
        private String name;
        private boolean isBig;
        private boolean isStart;
        private boolean isEnd;
        private Set<String> connections = new HashSet<>();

        public static void create(String input, int from, int to) {
            var splitted = input.split("-");
            var optNode = nodeList.stream().filter(e -> e.name.equals(splitted[from])).findFirst();
            if (optNode.isPresent()) {
                if (!optNode.get().name.equals(splitted[to])) {
                    optNode.get().connections.add(splitted[to]);
                }
            } else {
                Node node = new Node();
                node.isEnd = false;
                node.isStart = false;
                node.isBig = splitted[from].toUpperCase().equals(splitted[from]);
                node.connections.add(splitted[to]);
                node.name = splitted[from];
                nodeList.add(node);
                var notExistingNodes = node.connections.stream().filter(connection -> nodeList.stream().noneMatch(n1 -> n1.name.equals(connection)))
                        .collect(Collectors.toSet());
                for (String notExistingNode : notExistingNodes) {
                    Node node2 = new Node();
                    node2.isEnd = false;
                    node2.isStart = false;
                    node2.isBig = notExistingNode.toUpperCase().equals(notExistingNode);
                    node2.connections.add(node.name);
                    node2.name = notExistingNode;
                    nodeList.add(node2);
                }
            }
        }

        public Node() {
        }

        public Node(boolean start) {
            isBig = false;
            isStart = start;
            isEnd = !start;
            name = start ? "start" : "end";
        }

        @Override
        public String toString() {
            return "Node{" +
                    "name='" + name + '\'' +
                    ", isBig=" + isBig +
                    ", isStart=" + isStart +
                    ", isEnd=" + isEnd +
                    ", connections=" + connections +
                    '}';
        }
    }


}