package dataStructures.graph;

import java.util.HashMap;
import java.util.LinkedList;

public abstract class Graph<K extends Comparable<K>, E> implements IGraph<K, E> {

	protected LinkedList<Edge<K, E>> edges;
	protected int time, currentVertexNumber;
	protected final boolean isDirected, multipleEdges, loops;

	protected final HashMap<K, Integer> vertexesIndex;

	protected Graph(GraphType graphType) {
		edges = new LinkedList<>();
		this.vertexesIndex = new HashMap<>();
		currentVertexNumber = 0;
		isDirected = switch (graphType) {
			case SIMPLE, MULTIGRAPH, PSEUDOGRAPH -> false;
			case DIRECTED, MULTIGRAPH_DIRECTED -> true;
		};
		multipleEdges = switch (graphType) {
			case SIMPLE, DIRECTED -> false;
			case MULTIGRAPH, PSEUDOGRAPH, MULTIGRAPH_DIRECTED -> true;
		};
		loops = switch (graphType) {
			case SIMPLE, MULTIGRAPH -> false;
			case DIRECTED, PSEUDOGRAPH, MULTIGRAPH_DIRECTED -> true;
		};
	}

}
