package dataStructures.graph;

/**
 * The GraphType enum represents different types of graphs.
 */
public enum GraphType {
	SIMPLE, // Represents a simple graph
	MULTIGRAPH, // Represents a multigraph, which allows multiple edges between the same pair of
				// vertices
	PSEUDOGRAPH, // Represents a pseudograph, which allows self-loops and multiple edges between
					// the same pair of vertices
	DIRECTED, // Represents a directed graph
	MULTIGRAPH_DIRECTED // Represents a multigraph with directed edges, allowing multiple edges between
						// the same pair of vertices
}
