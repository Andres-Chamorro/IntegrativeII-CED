package com.example.pipegame.model;

import java.util.*;

public class GraphAdjacentyMatriz<T> implements IGraph<T> {

    private ArrayList<Vertex<T>> vertices;
    private int[][] adjacencyMatrix;
    private int time;

    // The above code is defining a constructor for a class called GraphAdjacencyMatrix. It initializes
    // an empty ArrayList called vertices, an empty 2D array called adjacencyMatrix, and a variable
    // called time with a value of 0.
    public GraphAdjacentyMatriz() {
        vertices = new ArrayList<>();
        adjacencyMatrix = new int[0][0];
        time = 0;
    }

    /**
     * The addVertex function adds a new vertex to a graph and updates the adjacency matrix
     * accordingly.
     * 
     * @param vertex The parameter "vertex" is of type Vertex<T>, where T is the type of data that the
     * vertex holds.
     */
    @Override
    public void addVertex(Vertex<T> vertex) {
        vertices.add(vertex);
        int[][] newMatrix = new int[vertices.size()][vertices.size()];
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            System.arraycopy(adjacencyMatrix[i], 0, newMatrix[i], 0, adjacencyMatrix[i].length);
        }
        adjacencyMatrix = newMatrix;
    }

    /**
     * The function finds a vertex in a graph based on its data.
     * 
     * @param data The parameter "data" is the value that we are searching for in the vertices of the
     * graph. It represents the data stored in each vertex of the graph.
     * @return The method is returning a Vertex object with the specified data, or null if no such
     * vertex is found.
     */
    @Override
    public Vertex<T> findVertex(T data) {
        for (Vertex<T> vertex : vertices) {
            if (vertex.getData().equals(data)) {
                return vertex;
            }
        }
        return null;
    }

   /**
    * The removeVertex function removes a vertex from a graph by removing its corresponding row and
    * column from the adjacency matrix.
    * 
    * @param vertex The parameter "vertex" is of type Vertex<T>, where T is the type of data stored in
    * the vertex. It represents the vertex that needs to be removed from the graph.
    */
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

    /**
     * The addEdge function adds an edge between two vertices in a graph and assigns a weight to it.
     * 
     * @param source The source parameter represents the starting vertex of the edge. It is of type
     * Vertex<T>, where T is the type of data stored in the vertex.
     * @param destination The destination parameter is of type Vertex<T> and represents the vertex that
     * the edge is directed towards.
     * @param weight The weight parameter represents the weight or cost associated with the edge
     * between the source and destination vertices. It is an integer value that indicates the strength,
     * distance, or any other measure of the connection between the two vertices.
     */
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

    /**
     * The removeEdge function removes an edge between two vertices in a graph represented by an
     * adjacency matrix.
     * 
     * @param source The source parameter represents the starting vertex of the edge that you want to
     * remove.
     * @param destination The destination parameter represents the vertex that the edge is directed
     * towards. In other words, it is the vertex that the edge is connecting to from the source vertex.
     */
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

    /**
     * The function performs a depth-first search starting from a given source vertex and returns the
     * order in which the vertices were visited.
     * 
     * @param source The source parameter is the starting vertex for the Depth First Search (DFS)
     * algorithm. It is the vertex from which the DFS traversal will begin.
     * @return The method is returning an ArrayList of Vertex objects.
     */
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

    /**
     * The function performs a depth-first search on a graph, updating the discovery and finish times
     * of each vertex and adding them to a list in the order they are visited.
     * 
     * @param vertexIndex The index of the vertex that we want to start the depth-first search from.
     * @param dfsOrder The dfsOrder parameter is an ArrayList that will store the vertices in the order
     * they are visited during the depth-first search.
     */
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

    /**
     * The function performs a breadth-first search (BFS) on a graph starting from a given source
     * vertex and returns the order in which the vertices are visited.
     * 
     * @param source The source parameter is the starting vertex for the breadth-first search
     * algorithm. It is the vertex from which the search will begin and explore its neighboring
     * vertices.
     * @return The method is returning an ArrayList of Vertex objects, which represents the order of
     * vertices visited during the breadth-first search (BFS) traversal.
     */
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

    /**
     * The function implements Dijkstra's algorithm to find the shortest path between two vertices in a
     * graph.
     * 
     * @param startVertex The starting vertex of the graph from which the Dijkstra's algorithm will
     * find the shortest path.
     * @param endVertex The endVertex parameter is the vertex that we want to find the shortest path to
     * from the startVertex.
     * @return The method is returning an ArrayList of Vertex objects, representing the shortest path
     * from the startVertex to the endVertex.
     */
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

   /**
    * The floydWarshall function implements the Floyd-Warshall algorithm to find the shortest path
    * between all pairs of vertices in a graph.
    * 
    * @return The method is returning a 2D array of integers. This array represents the shortest
    * distances between all pairs of vertices in a graph.
    */
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
                    if (dist[i][j] > (dist[i][k] + dist[k][j]) && dist[k][j] != Integer.MAX_VALUE
                            && dist[i][k] != Integer.MAX_VALUE) {
                        dist[i][j] = dist[i][k] + dist[k][j];
                    }
                }
            }
        }

        return dist;
    }

    /**
     * The function primAL is a method in a Java class that returns a GraphAdjacencyList object using
     * the Prim's algorithm, given a start vertex.
     * 
     * @param startVertex The startVertex parameter is the vertex from which the Prim's algorithm will
     * start building the minimum spanning tree.
     * @return The method is returning a GraphAdjacencyList object. However, in this implementation, it
     * is returning null.
     */
    @Override
    public GraphAdjacentyList<T> primAL(Vertex<T> startVertex) {
        return null;
    }

   /**
    * The function "kruskalAL" returns a GraphAdjacencyList object, but currently it returns null.
    * 
    * @return The method is returning null.
    */
    @Override
    public GraphAdjacentyList<T> kruskalAL() {
        return null;
    }

    /**
     * The function `primAM` implements Prim's algorithm to find the minimum spanning tree of a graph
     * represented using an adjacency matrix.
     * 
     * @param startVertex The start vertex from which the Prim's algorithm will begin constructing the
     * minimum spanning tree.
     * @return The method `primAM` returns a `GraphAdjacentyMatriz<T>` object, which represents the
     * minimum spanning tree (MST) of the original graph.
     */
    @Override
    public GraphAdjacentyMatriz<T> primAM(Vertex<T> startVertex) {
        GraphAdjacentyMatriz<T> mstGraph = new GraphAdjacentyMatriz<>();

        // initialize distance, color, and pred arrays
        for (Vertex<T> u : vertices) {
            u.setDistance(Integer.MAX_VALUE);
            u.setColor(Color.WHITE);
            u.setPredecessor(null);
            mstGraph.addVertex(new Vertex<>(u.getData()));
        }

        startVertex.setDistance(0);

        PriorityQueue<Vertex<T>> priorityQueue = new PriorityQueue<>(vertices.size(),
                Comparator.comparingInt(Vertex::getDistance));
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

    /**
     * The function `kruskalAM` implements the Kruskal's algorithm to find the minimum spanning tree of
     * a graph represented using an adjacency matrix.
     * 
     * @return The method `kruskalAM` returns a `GraphAdjacentyMatriz<T>` object, which represents the
     * minimum spanning tree of the original graph.
     */
    @Override
    public GraphAdjacentyMatriz<T> kruskalAM() {
        GraphAdjacentyMatriz<T> minimumSpanningTree = new GraphAdjacentyMatriz<>();
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
                minimumSpanningTree.addEdge(new Vertex<>(sourceVertex.getData()),
                        new Vertex<>(destinationVertex.getData()), edge.getWeight());
            }
        }

        return minimumSpanningTree;
    }

    /**
     * The function returns an ArrayList of vertices.
     * 
     * @return An ArrayList of Vertex objects.
     */
    @Override
    public ArrayList<Vertex<T>> getVertices() {
        return vertices;
    }

    /**
     * The function removes all edges from all vertices in a graph.
     */
    public void removeAllEdges() {
        for (Vertex<T> vertex : vertices) {
            removeAllEdgesFromVertex(vertex);
        }
    }

    /**
     * The function removes all edges connected to a given vertex in a graph represented by an
     * adjacency matrix.
     * 
     * @param vertex The parameter "vertex" is an object of type Vertex<T>, where T is the type of data
     * stored in the vertex.
     */
    private void removeAllEdgesFromVertex(Vertex<T> vertex) {
        int vertexIndex = vertices.indexOf(vertex);
        // clear all edges going to and from the vertex in the adjacency matrix
        for (int i = 0; i < vertices.size(); i++) {
            adjacencyMatrix[vertexIndex][i] = 0;
            adjacencyMatrix[i][vertexIndex] = 0;
        }
    }

    /**
     * The function getAllEdges() returns a list of all edges in a graph represented by an adjacency
     * matrix.
     * 
     * @return The method is returning a List of Edge objects.
     */
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
/**
 * The function finds and returns an edge between two vertices if it exists in the adjacency matrix.
 * 
 * @param source The source parameter is of type Vertex<T> and represents the starting vertex of the
 * edge.
 * @param destination The `destination` parameter is of type `Vertex<T>`, where `T` is the type of data
 * stored in the vertex. It represents the vertex that we want to find an edge to.
 * @return The method is returning an Edge object.
 */

    public Edge<T> findEdge(Vertex<T> source, Vertex<T> destination) {
        int sourceIndex = vertices.indexOf(source);
        int destinationIndex = vertices.indexOf(destination);

        if (sourceIndex != -1 && destinationIndex != -1 && adjacencyMatrix[sourceIndex][destinationIndex] != 0) {
            int weight = adjacencyMatrix[sourceIndex][destinationIndex];
            return new Edge<>(source, destination, weight);
        }
        return null;
    }

   /**
    * The DisjointSet class is a data structure that implements the disjoint-set data structure, which
    * allows for efficient union and find operations on sets.
    */
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

    /**
     * The function returns a vertex with a specific key value from a list of vertices.
     * 
     * @param keyVertex The keyVertex parameter is the value that we are searching for in the vertices
     * list. It represents the data of the vertex that we want to retrieve.
     * @return The method is returning a Vertex object with the specified keyVertex as its data. If no
     * such vertex is found, it returns null.
     */
    @Override
    public Vertex<T> getVertex(T keyVertex) {
        for (Vertex<T> vertex : vertices) {
            if (vertex.getData().equals(keyVertex)) {
                return vertex;
            }
        }
        return null;
    }

    /**
     * The function deletes a vertex from a graph if it exists.
     * 
     * @param keyVertex The key value of the vertex that needs to be deleted.
     */
    @Override
    public void deleteVertex(T keyVertex) {
        Vertex<T> vertex = getVertex(keyVertex);
        if (vertex != null) {
            removeVertex(vertex);
        }
    }

}

