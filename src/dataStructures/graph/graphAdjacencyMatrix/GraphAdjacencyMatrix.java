package dataStructures.graph.graphAdjacencyMatrix;

import java.util.*;
import java.util.stream.Collectors;
import dataStructures.graph.*;
import exception.GraphException;

public class GraphAdjacencyMatrix<K extends Comparable<K>, E> extends Graph<K, E> {

	private final HashMap<K, Vertex<K, E>> vertexes;
	private static final int maxVertexes = 50;
	private final ArrayList<Integer>[][] adjacencyMatrix;

	public GraphAdjacencyMatrix(GraphType graphType) {
		this(maxVertexes, graphType);
	}

	public GraphAdjacencyMatrix(int maxVertex, GraphType graphType) {
		super(graphType);
		this.vertexes = new HashMap<>();
		adjacencyMatrix = new ArrayList[maxVertex][maxVertex];
		for (int i = 0; i < maxVertex; i++)
			for (int j = 0; j < maxVertex; j++)
				adjacencyMatrix[i][j] = new ArrayList<>();
	}

	@Override
	public void insertVertex(K key, E element) {
		if (vertexes.get(key) == null) {
			vertexes.put(key, new Vertex<>(key, element));
			vertexesIndex.put(key, currentVertexNumber++);
		}
	}

	@Override
	public void deleteVertex(K keyVertex) {
		Vertex<K, E> v = vertexes.remove(keyVertex);
		if (v != null) {
			int vertexIndex = vertexNumber(keyVertex);
			for (int i = 0; i < maxVertexes; i++) {
				adjacencyMatrix[vertexIndex][i] = new ArrayList<>();
				adjacencyMatrix[i][vertexIndex] = new ArrayList<>();
			}
		}
	}

	@Override
	public Vertex<K, E> getVertex(K keyVertex) {
		return vertexes.get(keyVertex);
	}

	@Override
	public void insertEdge(K keyVertex1, K keyVertex2, int weight) throws GraphException {
		verifyExistence(keyVertex1, keyVertex2);
		int va = vertexNumber(keyVertex1);
		int vb = vertexNumber(keyVertex2);
		if (!loops && va == vb)
			throw new GraphException("This type of graph does not support loops.");
		if (!multipleEdges && adjacencyMatrix[va][vb].size() > 0)
			throw new GraphException("This type of graph does not support multiple edges.");
		adjacencyMatrix[va][vb].add(weight);
		Collections.sort(adjacencyMatrix[va][vb]);
		edges.add(new Edge<>(getVertex(keyVertex1), getVertex(keyVertex2), weight));
		if (!isDirected) {
			adjacencyMatrix[vb][va].add(weight);
			Collections.sort(adjacencyMatrix[vb][va]);
			edges.add(new Edge<>(getVertex(keyVertex2), getVertex(keyVertex1), weight));
		}
	}

	@Override
	public void deleteEdge(K keyVertex1, K keyVertex2) throws GraphException {
		verifyExistence(keyVertex1, keyVertex2);
		int va = vertexNumber(keyVertex1);
		int vb = vertexNumber(keyVertex2);
		if (adjacencyMatrix[va][vb].size() > 0) {
			adjacencyMatrix[va][vb].remove(0);
			edges.removeIf(edge -> edge.start().getKey().compareTo(keyVertex1) == 0
					&& edge.destination().getKey().compareTo(keyVertex2) == 0);
			if (!isDirected) {
				adjacencyMatrix[vb][va].remove(0);
				edges.removeIf(edge -> edge.start().getKey().compareTo(keyVertex2) == 0
						&& edge.destination().getKey().compareTo(keyVertex1) == 0);
			}
		}
	}

	@Override
	public boolean adjacent(K keyVertex1, K keyVertex2) throws GraphException {
		verifyExistence(keyVertex1, keyVertex2);
		return adjacencyMatrix[vertexNumber(keyVertex1)][vertexNumber(keyVertex2)].size() > 0;
	}

	@Override
	public void BFS(K keyVertex) {
		for (Vertex<K, E> u : vertexes.values()) {
			u.setColor(Color.WHITE);
			u.setDistance(Integer.MAX_VALUE);
			u.setPredecessor(null);
		}
		Vertex<K, E> s = vertexes.get(keyVertex);
		s.setColor(Color.GRAY);
		s.setDistance(0);
		Queue<Vertex<K, E>> queue = new LinkedList<>();
		queue.offer(s);
		while (!queue.isEmpty()) {
			Vertex<K, E> u = queue.poll();
			for (Vertex<K, E> vertex : vertexes.values()) {
				if (adjacent(u.getKey(), vertex.getKey()) && vertex.getColor() == Color.WHITE) {
					vertex.setColor(Color.GRAY);
					vertex.setDistance(u.getDistance() + 1);
					vertex.setPredecessor(u);
					queue.offer(vertex);
				}
			}
			u.setColor(Color.BLACK);
		}
	}

	private void verifyExistence(K keyVertex1, K keyVertex2) throws GraphException {
		Vertex<K, E> v1 = vertexes.get(keyVertex1);
		Vertex<K, E> v2 = vertexes.get(keyVertex2);
		if (v1 == null || v2 == null)
			throw new GraphException("Vertex not found");
	}

	@Override
	public ArrayList<Integer> dijkstra(K keyVertexSource) {
		if (vertexes.get(keyVertexSource) == null) {
			throw new GraphException("Source vertex not found.");
		}
		for (Vertex<K, E> vertex : vertexes.values()) {
			if (vertex.getKey().compareTo(keyVertexSource) != 0)
				vertex.setDistance(Integer.MAX_VALUE);
			vertex.setPredecessor(null);
		}
		vertexes.get(keyVertexSource).setDistance(0);
		PriorityQueue<Vertex<K, E>> priorityQueue = new PriorityQueue<>(
				Comparator.comparing(Vertex<K, E>::getDistance));
		for (Vertex<K, E> vertex : vertexes.values()) {
			priorityQueue.offer(vertex);
		}
		while (!priorityQueue.isEmpty()) {
			Vertex<K, E> u = priorityQueue.poll();
			for (Vertex<K, E> vertex : vertexes.values()) {
				if (adjacent(u.getKey(), vertex.getKey())) {
					int alt = u.getDistance()
							+ adjacencyMatrix[vertexNumber(u.getKey())][vertexNumber(vertex.getKey())].get(0);
					if (alt < vertex.getDistance()) {
						vertex.setDistance(alt);
						vertex.setPredecessor(u);
						priorityQueue.offer(vertex);
					}
				}
			}
		}
		return vertexes.values().stream().map(Vertex::getDistance).collect(Collectors.toCollection(ArrayList::new));
	}

	private int vertexNumber(K keyVertex) {
		Integer index = vertexesIndex.get(keyVertex);
		return index == null ? -1 : index;
	}

	@Override
	public ArrayList<Edge<K, E>> kruskal() throws GraphException {
		if (isDirected)
			throw new GraphException("The graph must be undirected.");
		ArrayList<Edge<K, E>> A = new ArrayList<>();
		UnionFind unionFind = new UnionFind(maxVertexes);
		edges.sort(Comparator.comparingInt(Edge::weight));
		for (Edge<K, E> edge : edges) {
			int u = vertexNumber(edge.start().getKey());
			int v = vertexNumber(edge.destination().getKey());
			if (unionFind.find(u) != unionFind.find(v)) {
				A.add(edge);
				unionFind.union(u, v);
			}
		}
		return A;
	}

}
