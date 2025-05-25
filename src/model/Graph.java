package model;

import java.util.*;

public class Graph {
    private ArrayList<LinkedList<Node>> adjList;

    public Graph() {
        this.adjList = new ArrayList<>();
    }

    public ArrayList<LinkedList<Node>> getAdjList() {
        return adjList;
    }

    public void setAdjList(ArrayList<LinkedList<Node>> adjList) {
        this.adjList = adjList;
    }

    public void addNode(Node node) {
        LinkedList<Node> currentList = new LinkedList<>();
        currentList.add(node);
        this.adjList.add(currentList);
    }

    public void addEdge(int src, int dst) {
        LinkedList<Node> currentList = this.adjList.get(src);
        Node dstNode = this.adjList.get(dst).get(0);
        currentList.add(dstNode);
    }

    public void removeEdge(int src, int dst) {
        LinkedList<Node> currentList = this.adjList.get(src);
        Node dstNode = this.adjList.get(dst).get(0);
        currentList.remove(dstNode);
    }

    public boolean checkEdge(int src, int dst) {
        LinkedList<Node> currentList = this.adjList.get(src);
        Node dstNode = this.adjList.get(dst).get(0);

        for (Node node : currentList) {
            if (node == dstNode) {
                return true;
            }
        }
        return false;
    }

    public void print() {
        for (LinkedList<Node> currentList : this.adjList) {
            for (Node node : currentList) {
                System.out.print(node.getData() + " -> ");
            }
            System.out.println();
        }
    }

    public Queue<Node> breadthFirstSearch(int src, int level) {
        int degree = 0;
        Queue<Node> currentLevel = new LinkedList<>();
        Queue<Node> nextLevel = new LinkedList<>();
        boolean[] visited = new boolean[this.adjList.size()];

        currentLevel.offer(this.adjList.get(src).get(0));
        visited[src] = true;

        while (currentLevel.size() != 0 && degree < level) {
            src = currentLevel.poll().getId();
            System.out.println(this.adjList.get(src).get(0).getData() + " = visited");
            for (Node i : this.adjList.get(src)) {
                if (!visited[i.getId()]) {
                    nextLevel.offer(i);
                    visited[i.getId()] = true;
                }
            }
            if (currentLevel.isEmpty()) {
                Queue<Node> temp = currentLevel;
                currentLevel = nextLevel;
                nextLevel = temp;
                degree++;
            }
        }
        return currentLevel;
    }

    public int[] shortestPath(int src) {
        int[] distance = new int[this.adjList.size()];

        Arrays.fill(distance, Integer.MAX_VALUE);

        distance[src] = 0;

        Queue<Integer> queue = new LinkedList<>();
        queue.add(src);

        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size > 0) {
                int vertex = Objects.requireNonNull(queue.poll());
                LinkedList<Node> adjVertices = this.adjList.get(vertex);
                for (Node node : adjVertices) {
                    if (distance[node.getId()] == Integer.MAX_VALUE) {
                        distance[node.getId()] = distance[vertex] + 1;
                        queue.add(node.getId());
                    }
                }
                size--;
            }
        }

        System.out.println("Distance from " + this.adjList.get(src).get(0).getData());
        for (int i = 0; i < this.adjList.size(); i++) {
            System.out.println(this.adjList.get(i).get(0).getData() + " is " + distance[i]);
        }

        return distance;
    }
}
