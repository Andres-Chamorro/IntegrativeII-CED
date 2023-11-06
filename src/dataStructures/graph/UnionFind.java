package dataStructures.graph;

public class UnionFind {

    private final int[] parent;

    public UnionFind(int size) {
        parent = new int[size];
        for (int i = 0; i < size; i++) {
            parent[i] = i;
        }
    }

    public int find(int a) {
        if (parent[a] != a)
            parent[a] = find(parent[a]);
        return parent[a];
    }

    public void union(int a, int b) {
        int rootX = find(a);
        int rootY = find(b);
        if (rootX != rootY)
            parent[rootX] = rootY;
    }

}
