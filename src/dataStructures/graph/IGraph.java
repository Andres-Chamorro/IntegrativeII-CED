package dataStructures.graph;

import java.util.ArrayList;
import exception.GraphException;

public interface IGraph<K extends Comparable<K>, E> {
	public void insertVertex(K key, E element);

	public void deleteVertex(K keyVertex);

	public Vertex<K, E> getVertex(K keyVertex);

	public void insertEdge(K keyVertex1, K keyVertex2, int weight) throws GraphException;

	public void deleteEdge(K keyVertex1, K keyVertex2) throws GraphException;

	public boolean adjacent(K keyVertex1, K keyVertex2) throws GraphException;

	public void BFS(K keyVertex);

	public ArrayList<Integer> dijkstra(K keyVertexSource);

	public ArrayList<Edge<K, E>> kruskal() throws GraphException;

}