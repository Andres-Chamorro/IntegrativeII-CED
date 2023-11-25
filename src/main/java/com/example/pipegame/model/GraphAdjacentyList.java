package com.example.pipegame.model;

import java.util.*;

public class GraphAdjacentyList<T> implements IGraph<T> {

    private ArrayList<Vertex<T>> vertices;
    private ArrayList<Edge<T>> edges;
    private int time;

    // The above code is defining a constructor for a class called GraphAdjacencyList. It initializes
    // an empty ArrayList called edges and an empty ArrayList called vertices. It also initializes a
    // variable called time to 0.
    public GraphAdjacentyList() {
        edges = new ArrayList<>();
        vertices = new ArrayList<>();
        time = 0;
    }

    /**
     * The addVertex function adds a vertex to a list of vertices.
     * 
     * @param vertex The parameter "vertex" is of type Vertex<T>, where T is a generic type
     * representing the data type of the vertex.
     */
    @Override
    public void addVertex(Vertex<T> vertex) {
        vertices.add(vertex);
    }

    @Override
    // The above code is a method in a Java class that is used to find a vertex in a graph based on its
    // data. It takes a parameter "data" which represents the data of the vertex to be found. It
    // iterates through the list of vertices in the graph and checks if the data of each vertex is
    // equal to the given data. If a match is found, the method returns the vertex. If no match is
    // found, it returns null.
    public Vertex<T> findVertex(T data) {
        for (Vertex<T> vertex : vertices) {
            if (vertex.getData().equals(data)) {
                return vertex;
            }
        }
        return null;
    }

    /**
     * The removeVertex function removes a vertex from a graph, along with all associated edges and
     * updates the neighbors of the remaining vertices.
     * 
     * @param vertex The parameter "vertex" is an object of type Vertex<T>, where T is the type of data
     * stored in the vertex.
     */
    @Override
    public void removeVertex(Vertex<T> vertex) {
        if (!vertices.contains(vertex)) {
            throw new IllegalArgumentException("The vertex is not in the graph.");
        }
        // delete the vertex and all associated edges
        vertices.remove(vertex);
        edges.removeIf(edge -> edge.getSource().equals(vertex) || edge.getDestination().equals(vertex));
        for (Vertex<T> v : vertices) {
            v.removeNeighbor(vertex);
        }
    }

    /**
     * The addEdge function adds an edge between two vertices in a graph, with a specified weight.
     * 
     * @param source The source parameter represents the starting vertex of the edge. It is of type
     * Vertex<T>, where T is the type of data stored in the vertex.
     * @param destination The destination parameter represents the vertex that the edge is directed
     * towards.
     * @param weight The weight parameter represents the weight or cost associated with the edge
     * between the source and destination vertices. It is used to determine the cost of traversing or
     * using that edge in certain algorithms, such as shortest path algorithms.
     */
    @Override
    public void addEdge(Vertex<T> source, Vertex<T> destination, int weight) {
        if (!vertices.contains(source) || !vertices.contains(destination)) {
            throw new IllegalArgumentException("The vertices must be in the graph.");
        }
        source.addNeighbor(destination);
        destination.addNeighbor(source);
        Edge<T> edge = new Edge<>(source, destination, weight);
        edges.add(edge);
    }

    /**
     * The removeEdge function removes an edge between two vertices in a graph.
     * 
     * @param source The source parameter represents the starting vertex of the edge that needs to be
     * removed.
     * @param destination The destination parameter represents the vertex that is the destination of
     * the edge to be removed.
     */
    @Override
    public void removeEdge(Vertex<T> source, Vertex<T> destination) {
        if (!vertices.contains(source) || !vertices.contains(destination)) {
            throw new IllegalArgumentException("The vertices must be in the graph.");
        }
        source.removeNeighbor(destination);
        destination.removeNeighbor(source);
        Edge<T> edgeToRemove = findEdge(source, destination);
        if (edgeToRemove != null) {
            edges.remove(edgeToRemove);
        }
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
            dfs(source, dfsOrder);
        }
        return dfsOrder;
    }

   /**
    * The function performs a depth-first search on a graph, keeping track of the discovery and finish
    * times of each vertex.
    * 
    * @param v The parameter "v" is of type Vertex<T>, which represents a vertex in a graph.
    * @param dfsOrder The dfsOrder parameter is an ArrayList that will store the vertices in the order
    * they are visited during the depth-first search traversal.
    */
    private void dfs(Vertex<T> v, ArrayList<Vertex<T>> dfsOrder) {
        time += 1;
        v.setDiscoveryTime(time);
        v.setColor(Color.GRAY);
        dfsOrder.add(v);
        for (Vertex<T> u : v.getNeighbors()) {
            if (u.getColor() == Color.WHITE) {
                dfs(u, dfsOrder);
            }
        }
        v.setColor(Color.BLACK);
        time += 1;
        v.setFinishTime(time);
    }

   /**
    * The function performs a breadth-first search (BFS) on a graph starting from a given source vertex
    * and returns the order in which the vertices are visited.
    * 
    * @param source The source parameter is the starting vertex for the breadth-first search algorithm.
    * It is the vertex from which the search will begin and explore its neighbors.
    * @return The method is returning an ArrayList of Vertex objects, which represents the order in
    * which the vertices were visited during the breadth-first search (BFS) traversal.
    */
    @Override
    public ArrayList<Vertex<T>> bfs(Vertex<T> source) {
        ArrayList<Vertex<T>> bfsOrder = new ArrayList<>();

        for (Vertex<T> u : vertices) {
            u.setColor(Color.WHITE);
            u.setDistance(Integer.MAX_VALUE);
            u.setPredecessor(null);
        }

        // initialization of the source vertex
        source.setColor(Color.GRAY);
        source.setDistance(0);
        source.setPredecessor(null);

        // queue to take the BFS route
        Queue<Vertex<T>> queue = new LinkedList<>();
        queue.offer(source);

        while (!queue.isEmpty()) {
            Vertex<T> u = queue.poll();
            bfsOrder.add(u); // add vertex to BFS result
            // iteration over the neighbors of the current vertex.
            for (Vertex<T> v : u.getNeighbors()) {
                if (v.getColor() == Color.WHITE) {
                    v.setColor(Color.GRAY);
                    v.setDistance(u.getDistance() + 1);
                    v.setPredecessor(u);
                    queue.offer(v);
                }
            }
            u.setColor(Color.BLACK);
        }

        return bfsOrder;
    }

    /**
     * The function implements Dijkstra's algorithm to find the shortest path between a source vertex
     * and a destination vertex in a graph.
     * 
     * @param source The source parameter is the starting vertex from which the Dijkstra's algorithm
     * will find the shortest path.
     * @param destination The destination parameter is the vertex that you want to find the shortest
     * path to from the source vertex.
     * @return The method is returning an ArrayList of Vertex objects, representing the shortest path
     * from the source vertex to the destination vertex.
     */
    @Override
    public ArrayList<Vertex<T>> dijkstra(Vertex<T> source, Vertex<T> destination) {
        Map<Vertex<T>, Integer> distances = new HashMap<>();
        Map<Vertex<T>, Vertex<T>> previousVertices = new HashMap<>();
        Set<Vertex<T>> S = new HashSet<>();
        PriorityQueue<Vertex<T>> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(distances::get));
        for (Vertex<T> vertex : vertices) {
            distances.put(vertex, Integer.MAX_VALUE);
        }
        distances.put(source, 0);
        priorityQueue.add(source);

        while (!priorityQueue.isEmpty()) {
            Vertex<T> u = priorityQueue.poll();
            S.add(u);
            if (u.equals(destination)) {
                break;
            }

            for (Edge<T> edge : getEdges(u)) {
                Vertex<T> v = edge.getDestination();
                int newDistance = distances.get(u) + edge.getWeight();
                if (!S.contains(v) && newDistance < distances.get(v)) {
                    distances.put(v, newDistance);
                    previousVertices.put(v, u);
                    priorityQueue.add(v);
                }
            }
        }

        // reconstructing the path from the destination to the source
        ArrayList<Vertex<T>> shortestPath = new ArrayList<>();
        Vertex<T> currentVertex = destination;
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
     * @return The method floydWarshall() returns a 2D array of integers. This array represents the
     * shortest distances between all pairs of vertices in a graph.
     */
    @Override
    public int[][] floydWarshall() {
        int size = vertices.size();
        int[][] dist = new int[size][size];

        // initialize dist matrix with edge weights
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i == j) {
                    dist[i][j] = 0;
                } else {
                    Edge<T> edge = findEdge(vertices.get(i), vertices.get(j));
                    dist[i][j] = (edge != null) ? edge.getWeight() : Integer.MAX_VALUE;
                }
            }
        }

        // apply Floyd-Warshall algorithm
        for (int k = 0; k < size; k++) {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (dist[i][k] != Integer.MAX_VALUE && dist[k][j] != Integer.MAX_VALUE
                            && dist[i][k] + dist[k][j] < dist[i][j]) {
                        dist[i][j] = dist[i][k] + dist[k][j];
                    }
                }
            }
        }

        return dist;
    }

    /**
     * The `primAL` function implements Prim's algorithm to find the minimum spanning tree of a graph
     * represented using an adjacency list.
     * 
     * @param startVertex The startVertex parameter is the vertex from which the Prim's algorithm will
     * start building the minimum spanning tree.
     * @return The method `primAL` returns a `GraphAdjacentyList<T>` object, which represents the
     * minimum spanning tree (MST) of the original graph.
     */
    @Override
    public GraphAdjacentyList<T> primAL(Vertex<T> startVertex) {
        GraphAdjacentyList<T> mstGraph = new GraphAdjacentyList<>();

        // Initialize key, color, and pred arrays
        for (Vertex<T> u : vertices) {
            u.setDistance(Integer.MAX_VALUE);
            u.setColor(Color.WHITE);
            u.setPredecessor(null);
            mstGraph.addVertex(new Vertex<>(u.getData()));
        }

        // Start with the given start vertex
        startVertex.setDistance(0);

        PriorityQueue<Vertex<T>> priorityQueue = new PriorityQueue<>(vertices.size(),
                Comparator.comparingInt(Vertex::getDistance));
        priorityQueue.addAll(vertices);

        while (!priorityQueue.isEmpty()) {
            Vertex<T> u = priorityQueue.poll();
            u.setColor(Color.BLACK);

            for (Vertex<T> v : u.getNeighbors()) {
                int weight = findEdge(u, v) != null ? Objects.requireNonNull(findEdge(u, v)).getWeight()
                        : Integer.MAX_VALUE;
                if (v.getColor() == Color.WHITE && weight < v.getDistance()) {
                    v.setDistance(weight);
                    priorityQueue.remove(v); // Remove and re-add to update the priority queue
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
     * The function returns a vertex with a specific key value from a list of vertices.
     * 
     * @param keyVertex The keyVertex parameter is the value that we are searching for in the vertices
     * list. It represents the data of the vertex that we want to retrieve.
     * @return The method is returning a Vertex object with the specified keyVertex as its data. If no
     * such vertex is found, it returns null.
     */
    public Vertex<T> getVertex(T keyVertex) {
        for (Vertex<T> vertex : vertices) {
            if (vertex.getData().equals(keyVertex)) {
                return vertex;
            }
        }
        return null;
    }

    /**
     * The function deletes a vertex from a graph.
     * 
     * @param keyVertex The key value of the vertex that needs to be deleted.
     */
    public void deleteVertex(T keyVertex) {
        Vertex<T> vertex = getVertex(keyVertex);
        if (vertex != null) {
            removeVertex(vertex);
        }
    }

    /**
     * The function `kruskalAL` implements the Kruskal's algorithm to find the minimum spanning tree of
     * a graph represented using an adjacency list.
     * 
     * @return The method is returning a minimum spanning tree of type GraphAdjacentyList<T>.
     */
    @Override
    public GraphAdjacentyList<T> kruskalAL() {
        GraphAdjacentyList<T> minimumSpanningTree = new GraphAdjacentyList<>();
        edges.sort(Comparator.comparingInt(Edge::getWeight));

        DisjointSet<T> disjointSet = new DisjointSet<>(vertices);

        for (Vertex<T> vertex : vertices) {
            minimumSpanningTree.addVertex(new Vertex<>(vertex.getData()));
        }

        for (Edge<T> edge : edges) {
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
     * The function primAM returns a GraphAdjacentyMatriz object using the Prim's algorithm starting
     * from a given startVertex.
     * 
     * @param startVertex The startVertex parameter is the vertex from which the Prim's algorithm will
     * start building the minimum spanning tree.
     * @return The method is returning a GraphAdjacentyMatriz object. However, in this case, it is
     * returning null.
     */
    @Override
    public GraphAdjacentyMatriz<T> primAM(Vertex<T> startVertex) {
        return null;
    }

    /**
     * The function "kruskalAM" returns a GraphAdjacencyMatrix object.
     * 
     * @return The method is returning a GraphAdjacentyMatriz object. However, in this case, it is
     * returning null.
     */
    @Override
    public GraphAdjacentyMatriz<T> kruskalAM() {
        return null;
    }

    @Override
    public ArrayList<Vertex<T>> getVertices() {
        return vertices;
    }

    // auxiliars
    /**
     * The function removes all edges from all vertices in a graph.
     */
    public void removeAllEdges() {
        for (Vertex<T> vertex : vertices) {
            removeAllEdgesFromVertex(vertex);
        }
    }

    /**
     * The function removes all edges connected to a given vertex.
     * 
     * @param vertex The parameter "vertex" is an object of type Vertex<T>, where T represents the type
     * of data stored in the vertex.
     */
    private void removeAllEdgesFromVertex(Vertex<T> vertex) {
        ArrayList<Vertex<T>> neighbors = new ArrayList<>(vertex.getNeighbors());
        for (Vertex<T> neighbor : neighbors) {
            removeEdge(vertex, neighbor);
        }
    }

    /**
     * The function finds and returns an edge between two vertices in a graph.
     * 
     * @param source The source parameter is a Vertex object representing the starting vertex of the
     * edge.
     * @param destination The "destination" parameter is the vertex that we want to find an edge to.
     * @return The method is returning an object of type Edge<T> or null.
     */
    private Edge<T> findEdge(Vertex<T> source, Vertex<T> destination) {
        for (Edge<T> edge : edges) {
            if (edge.getSource().equals(source) && edge.getDestination().equals(destination)) {
                return edge;
            }
        }
        return null;
    }

    /**
     * The function returns a list of edges connected to a given vertex.
     * 
     * @param vertex The parameter "vertex" is of type Vertex<T>, where T is the type of data stored in
     * the vertex.
     * @return The method is returning an ArrayList of edges that are connected to the given vertex.
     */
    public ArrayList<Edge<T>> getEdges(Vertex<T> vertex) {
        ArrayList<Edge<T>> vertexEdges = new ArrayList<>();
        for (Edge<T> edge : edges) {
            if (edge.getSource().equals(vertex) || edge.getDestination().equals(vertex)) {
                vertexEdges.add(edge);
            }
        }
        return vertexEdges;
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

}
