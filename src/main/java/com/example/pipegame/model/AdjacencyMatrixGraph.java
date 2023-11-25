package com.example.pipegame.model;

import java.util.*;

public class AdjacencyMatrixGraph<T> implements iGraph<T> {

    private ArrayList<Vertex<T>> vertices;
    private int[][] adjacencyMatrix;
    private int time;

    public AdjacencyMatrixGraph() {
        vertices = new ArrayList<>();
        adjacencyMatrix = new int[0][0];
        time = 0;
    }

    @Override
    public void addVertex(Vertex<T> vertex) {
        vertices.add(vertex);
        // increase the size of the adjacency matrix
        int[][] newMatrix = new int[vertices.size()][vertices.size()];
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            System.arraycopy(adjacencyMatrix[i], 0, newMatrix[i], 0, adjacencyMatrix[i].length);
        }
        adjacencyMatrix = newMatrix;
    }

    @Override
    public Vertex<T> findVertex(T data) {
        for (Vertex<T> vertex : vertices) {
            if (vertex.getData().equals(data)) {
                return vertex;
            }
        }
        return null;
    }

    @Override
    public void removeVertex(Vertex<T> vertex) {
        if (!vertices.contains(vertex)) {
            throw new IllegalArgumentException("The vertex is not in the graph.");
        }

        int vertexIndex = vertices.indexOf(vertex);
        vertices.remove(vertex);

        // remove the corresponding row and column from the adjacency matrix
        int[][] newMatrix = new int[vertices.size()][vertices.size()];
        for (int i = 0, newRow = 0; i < adjacencyMatrix.length; i++) {
            if (i == vertexIndex) {
                continue;
            }
            for (int j = 0, newCol = 0; j < adjacencyMatrix[i].length; j++) {
                if (j != vertexIndex) {
                    newMatrix[newRow][newCol] = adjacencyMatrix[i][j];
                    newCol++;
                }
            }
            newRow++;
        }

        adjacencyMatrix = newMatrix;
    }

    @Override
    public void addEdge(Vertex<T> source, Vertex<T> destination, int weight) {
        if (!vertices.contains(source) || !vertices.contains(destination)) {
            throw new IllegalArgumentException("The vertices must be in the graph.");
        }

        int sourceIndex = vertices.indexOf(source);
        int destinationIndex = vertices.indexOf(destination);

        adjacencyMatrix[sourceIndex][destinationIndex] = weight;
        adjacencyMatrix[destinationIndex][sourceIndex] = weight;
    }

    @Override
    public void removeEdge(Vertex<T> source, Vertex<T> destination) {
        if (!vertices.contains(source) || !vertices.contains(destination)) {
            throw new IllegalArgumentException("The vertices must be in the graph.");
        }

        int sourceIndex = vertices.indexOf(source);
        int destinationIndex = vertices.indexOf(destination);

        adjacencyMatrix[sourceIndex][destinationIndex] = 0;
        adjacencyMatrix[destinationIndex][sourceIndex] = 0;
    }



    @Override
    public ArrayList<Vertex<T>> dfs(Vertex<T> source) {
        ArrayList<Vertex<T>> dfsOrder = new ArrayList<>();
        if (vertices.size() > 0) {
            for (Vertex<T> v : vertices) {
                v.setColor(Color.WHITE);
            }
            time = 0;
            dfs(vertices.indexOf(source), dfsOrder);
        }
        return dfsOrder;
    }

    private void dfs(int vertexIndex, ArrayList<Vertex<T>> dfsOrder) {
        time += 1;
        Vertex<T> v = vertices.get(vertexIndex);
        v.setDiscoveryTime(time);
        v.setColor(Color.GRAY);
        dfsOrder.add(v);
        for (int uIndex = 0; uIndex < vertices.size(); uIndex++) {
            if (adjacencyMatrix[vertexIndex][uIndex] != 0 && vertices.get(uIndex).getColor() == Color.WHITE) {
                dfs(uIndex, dfsOrder);
            }
        }
        v.setColor(Color.BLACK);
        time += 1;
        v.setFinishTime(time);
    }

    @Override
    public ArrayList<Vertex<T>> bfs(Vertex<T> source) {
        ArrayList<Vertex<T>> bfsOrder = new ArrayList<>();

        for (Vertex<T> u : vertices) {
            u.setColor(Color.WHITE);
            u.setDistance(Integer.MAX_VALUE);
            u.setPredecessor(null);
        }

        int sourceIndex = vertices.indexOf(source);

        // initialization of the source vertex
        vertices.get(sourceIndex).setColor(Color.GRAY);
        vertices.get(sourceIndex).setDistance(0);
        vertices.get(sourceIndex).setPredecessor(null);

        // queue to take the BFS route
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(sourceIndex);

        while (!queue.isEmpty()) {
            int uIndex = queue.poll();
            Vertex<T> u = vertices.get(uIndex);
            bfsOrder.add(u); // add vertex to BFS result
            // iteration over the neighbors of the current vertex.
            for (int vIndex = 0; vIndex < vertices.size(); vIndex++) {
                if (adjacencyMatrix[uIndex][vIndex] != 0 && vertices.get(vIndex).getColor() == Color.WHITE) {
                    Vertex<T> v = vertices.get(vIndex);
                    v.setColor(Color.GRAY);
                    v.setDistance(u.getDistance() + 1);
                    v.setPredecessor(u);
                    queue.offer(vIndex);
                }
            }
            u.setColor(Color.BLACK);
        }

        return bfsOrder;
    }

    @Override
    public ArrayList<Vertex<T>> dijkstra(Vertex<T> startVertex, Vertex<T> endVertex) {
        Map<Vertex<T>, Integer> distances = new HashMap<>();
        Map<Vertex<T>, Vertex<T>> previousVertices = new HashMap<>();
        Set<Vertex<T>> visitedVertices = new HashSet<>();
        // initialize distances
        for (Vertex<T> vertex : vertices) {
            distances.put(vertex, Integer.MAX_VALUE);
        }

        distances.put(startVertex, 0);
        PriorityQueue<Vertex<T>> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(distances::get));
        priorityQueue.add(startVertex);

        while (!priorityQueue.isEmpty()) {
            Vertex<T> currentVertex = priorityQueue.poll();

            if (currentVertex.equals(endVertex)) {
                break; 
            }

            if (!visitedVertices.contains(currentVertex)) {
                visitedVertices.add(currentVertex);
                int currentIndex = vertices.indexOf(currentVertex);

                for (int neighborIndex = 0; neighborIndex < vertices.size(); neighborIndex++) {

                    if (adjacencyMatrix[currentIndex][neighborIndex] != 0) {
                        Vertex<T> neighborVertex = vertices.get(neighborIndex);
                        int newDistance = distances.get(currentVertex) + adjacencyMatrix[currentIndex][neighborIndex];

                        if (newDistance < distances.get(neighborVertex)) {
                            distances.put(neighborVertex, newDistance);
                            previousVertices.put(neighborVertex, currentVertex);
                            priorityQueue.add(neighborVertex);
                            // Remove and re-add the neighbor to update its position in the PriorityQueue
                            priorityQueue.remove(neighborVertex);
                            priorityQueue.add(neighborVertex);
                        }
                    }
                }
            }
        }

        ArrayList<Vertex<T>> shortestPath = new ArrayList<>();
        Vertex<T> currentVertex = endVertex;

        while (currentVertex != null) {
            shortestPath.add(currentVertex);
            currentVertex = previousVertices.get(currentVertex);
        }

        return shortestPath;
    }

    @Override
    public int[][] floydWarshall() {
        int[][] dist = new int[vertices.size()][vertices.size()];

        // initialize dist matrix with edge weights
        for (int i = 0; i < vertices.size(); i++) {
            for (int j = 0; j < vertices.size(); j++) {
                if (i == j) {
                    dist[i][j] = 0;
                } else {
                    Edge<T> edge = findEdge(vertices.get(i), vertices.get(j));
                    dist[i][j] = (edge != null) ? edge.getWeight() : Integer.MAX_VALUE;
                }
            }
        }

        for (int k = 0; k < vertices.size(); k++) {
            for (int i = 0; i < vertices.size(); i++) {
                for (int j = 0; j < vertices.size(); j++) {
                    if (dist[i][j] > (dist[i][k] + dist[k][j]) && dist[k][j] != Integer.MAX_VALUE && dist[i][k] != Integer.MAX_VALUE) {
                        dist[i][j] = dist[i][k] + dist[k][j];
                    }
                }
            }
        }

        return dist;
    }

    @Override
    public AdjacencyListGraph<T> primAL(Vertex<T> startVertex) {
        return null;
    }

    @Override
    public AdjacencyListGraph<T> kruskalAL() {
        return null;
    }

    @Override
    public AdjacencyMatrixGraph<T> primAM(Vertex<T> startVertex) {
        AdjacencyMatrixGraph<T> mstGraph = new AdjacencyMatrixGraph<>();

        // initialize distance, color, and pred arrays
        for (Vertex<T> u : vertices) {
            u.setDistance(Integer.MAX_VALUE);
            u.setColor(Color.WHITE);
            u.setPredecessor(null);
            mstGraph.addVertex(new Vertex<>(u.getData()));
        }

        startVertex.setDistance(0);

        PriorityQueue<Vertex<T>> priorityQueue = new PriorityQueue<>(vertices.size(), Comparator.comparingInt(Vertex::getDistance));
        priorityQueue.addAll(vertices);

        while (!priorityQueue.isEmpty()) {
            Vertex<T> u = priorityQueue.poll();
            u.setColor(Color.BLACK);

            for (Vertex<T> v : vertices) {
                int weight = adjacencyMatrix[vertices.indexOf(u)][vertices.indexOf(v)];

                if (v.getColor() == Color.WHITE && weight > 0 && weight < v.getDistance()) {
                    v.setDistance(weight);
                    priorityQueue.remove(v); 
                    priorityQueue.add(v);
                    v.setPredecessor(u);

                    Vertex<T> uInMST = mstGraph.findVertex(u.getData());
                    Vertex<T> vInMST = mstGraph.findVertex(v.getData());

                    if (uInMST != null && vInMST != null) {
                        mstGraph.addEdge(uInMST, vInMST, weight);
                    }
                }
            }
        }

        return mstGraph;
    }

    @Override
    public AdjacencyMatrixGraph<T> kruskalAM() {
        AdjacencyMatrixGraph<T> minimumSpanningTree = new AdjacencyMatrixGraph<>();
        List<Edge<T>> allEdges = getAllEdges();
        allEdges.sort(Comparator.comparingInt(Edge::getWeight));

        DisjointSet<T> disjointSet = new DisjointSet<>(vertices);

        for (Vertex<T> vertex : vertices) {
            minimumSpanningTree.addVertex(new Vertex<>(vertex.getData()));
        }

        for (Edge<T> edge : allEdges) {
            Vertex<T> sourceVertex = edge.getSource();
            Vertex<T> destinationVertex = edge.getDestination();

            if (!disjointSet.find(sourceVertex.getData()).equals(disjointSet.find(destinationVertex.getData()))) {
                disjointSet.union(sourceVertex.getData(), destinationVertex.getData());
                minimumSpanningTree.addEdge(new Vertex<>(sourceVertex.getData()), new Vertex<>(destinationVertex.getData()), edge.getWeight());
            }
        }

        return minimumSpanningTree;
    }

    @Override
    public ArrayList<Vertex<T>> getVertices() {
        return vertices;
    }

    public void removeAllEdges() {
        for (Vertex<T> vertex : vertices) {
            removeAllEdgesFromVertex(vertex);
        }
    }

    private void removeAllEdgesFromVertex(Vertex<T> vertex) {
        int vertexIndex = vertices.indexOf(vertex);
        // clear all edges going to and from the vertex in the adjacency matrix
        for (int i = 0; i < vertices.size(); i++) {
            adjacencyMatrix[vertexIndex][i] = 0;
            adjacencyMatrix[i][vertexIndex] = 0;
        }
    }

    private List<Edge<T>> getAllEdges() {
        List<Edge<T>> allEdges = new ArrayList<>();
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            for (int j = i + 1; j < adjacencyMatrix[i].length; j++) {
                if (adjacencyMatrix[i][j] != 0) {
                    Vertex<T> source = vertices.get(i);
                    Vertex<T> destination = vertices.get(j);
                    int weight = adjacencyMatrix[i][j];
                    allEdges.add(new Edge<>(source, destination, weight));
                }
            }
        }
        return allEdges;
    }

    public Edge<T> findEdge(Vertex<T> source, Vertex<T> destination) {
        int sourceIndex = vertices.indexOf(source);
        int destinationIndex = vertices.indexOf(destination);

        if (sourceIndex != -1 && destinationIndex != -1 && adjacencyMatrix[sourceIndex][destinationIndex] != 0) {
            int weight = adjacencyMatrix[sourceIndex][destinationIndex];
            return new Edge<>(source, destination, weight);
        }
        return null;
    }

    private static class DisjointSet<T> {

        private final Map<T, T> parentMap;

        public DisjointSet(Collection<Vertex<T>> vertices) {
            parentMap = new HashMap<>();
            for (Vertex<T> vertex : vertices) {
                parentMap.put(vertex.getData(), vertex.getData());
            }
        }

        public T find(T element) {
            if (!parentMap.containsKey(element)) {
                throw new IllegalArgumentException("Element not found in the disjoint set");
            }

            if (element.equals(parentMap.get(element))) {
                return element;
            } else {
                T root = find(parentMap.get(element));
                parentMap.put(element, root);
                return root;
            }
        }

        public void union(T set1, T set2) {
            T root1 = find(set1);
            T root2 = find(set2);

            if (!root1.equals(root2)) {
                parentMap.put(root1, root2);
            }
        }
    }

}