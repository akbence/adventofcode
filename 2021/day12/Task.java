import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Task {
    static List<Node> nodeList = new ArrayList<>();

    public static void main(String[] args) throws IOException {

        List<String> inputLines = Files.readAllLines(Paths.get("./input.txt"));
        nodeList.add(new Node(true));
        nodeList.add(new Node(false));
        inputLines.forEach(Node::create);
        nodeList.forEach(System.out::println);
//        task1Solve(inputIntegers);

        System.out.println("Task1 solution: " + "incraseCount");
//        System.out.println("Task1 solution: " + incraseCount2);
    }

    private static void task1Solve() {

        visit(nodeList.get(0),nodeList);
    }

    private static void visit(Node node, List<Node> nodeList) {

    }

    static class Node {
        private String name;
        private boolean isBig;
        private boolean isStart;
        private boolean isEnd;
        private boolean visited = false;
        private Set<String> connections = new HashSet<>();

        public static void create(String input) {
            var splitted = input.split("-");
            var optNode = nodeList.stream().filter(e -> e.name.equals(splitted[0])).findFirst();
            if (optNode.isPresent()) {
                optNode.get().connections.add(splitted[1]);
            } else {
                Node node = new Node();
                node.isEnd = false;
                node.isStart = false;
                node.isBig = splitted[0].toUpperCase().equals(splitted[0]);
                node.connections.add(splitted[1]);
                node.name = splitted[0];
                nodeList.add(node);
                var notExistingNodes = node.connections.stream().filter(connection -> nodeList.stream().noneMatch(n1 -> n1.name.equals(connection)))
                        .collect(Collectors.toSet());
                for (String notExistingNode : notExistingNodes) {
                    Node node2 = new Node();
                    node2.isEnd = false;
                    node2.isStart = false;
                    node2.isBig = notExistingNode.toUpperCase().equals(notExistingNode);
                    node2.connections.add(node.name);
                    node2.name=notExistingNode;
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