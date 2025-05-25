package model;

import java.util.*;

public class UnionFind {
    private final int[] rank;
    private final int[] parent;
    private final int n;

    public UnionFind(int size) {
        this.n = size;
        this.rank = new int[this.n];
        this.parent = new int[this.n];
        makeSet();
    }

    private void makeSet() {
        for (int i = 0; i < this.n; i++) {
            this.parent[i] = i;
        }
    }

    private int find(int x) {
        if (parent[x] != x) {
            parent[x] = find(parent[x]);
        }
        return parent[x];
    }

    private void union(int x, int y) {
        int xRoot = find(x);
        int yRoot = find(y);

        if (xRoot == yRoot) {
            return;
        }
        if (this.rank[xRoot] < this.rank[yRoot]) {
            parent[xRoot] = yRoot;
        } else if (this.rank[xRoot] > this.rank[xRoot]) {
            parent[yRoot] = xRoot;
        } else {
            parent[yRoot] = xRoot;
            this.rank[xRoot] = this.rank[xRoot] + 1;
        }
    }

    public Map<Integer, List<Integer>> connectedComponents(ArrayList<ArrayList<Integer>> edges) {
        for (ArrayList<Integer> x : edges) {
            union(x.get(0), x.get(1));
        }

        Map<Integer, List<Integer>> map = new HashMap<>();
        for (int i = 0; i < this.n; i++) {
            map.computeIfAbsent(parent[i], k -> new ArrayList<>()).add(i);
        }

        return map;
    }
}
