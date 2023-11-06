package dataStructures.graph;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import exception.GraphException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;
import dataStructures.graph.graphAdjacencyMatrix.GraphAdjacencyMatrix;

public class GraphTest {

	private IGraph<Integer, String> simpleGraph;
	private IGraph<String, Integer> pseudoGraph;
	private IGraph<Integer, String> directedGraph1;

	private IGraph<String, String> simpleGraph2;

	private IGraph<Integer, String> multiGraph;
	private IGraph<Integer, String> multiGraphDirected;

	private void setupStageSimpleGraph() {
		// simpleGraph = new GraphAdjacencyList<>(GraphType.SIMPLE);
		simpleGraph = new GraphAdjacencyMatrix<>(GraphType.SIMPLE);
		// Insert vertexes in the graph
		simpleGraph.insertVertex(1, "A");
		simpleGraph.insertVertex(2, "B");
		simpleGraph.insertVertex(3, "C");
		simpleGraph.insertVertex(4, "D");
		simpleGraph.insertVertex(5, "E");
		simpleGraph.insertVertex(6, "F");
		simpleGraph.insertVertex(7, "G");
		simpleGraph.insertVertex(10, "I");
	}

	private void setupStagePseudoGraph() {
		// pseudoGraph = new GraphAdjacencyList<>(GraphType.PSEUDOGRAPH);
		pseudoGraph = new GraphAdjacencyMatrix<>(GraphType.PSEUDOGRAPH);
		// Insert vertexes in the graph
		pseudoGraph.insertVertex("1", 1);
		pseudoGraph.insertVertex("2", 2);
		pseudoGraph.insertVertex("3", 3);
		pseudoGraph.insertVertex("4", 4);
		pseudoGraph.insertVertex("5", 4);

		pseudoGraph.insertEdge("1", "2", 1);
		pseudoGraph.insertEdge("1", "1", 1);
		pseudoGraph.insertEdge("1", "3", 1);
		pseudoGraph.insertEdge("2", "4", 1);
		pseudoGraph.insertEdge("5", "1", 1);
		pseudoGraph.insertEdge("5", "2", 1);
		pseudoGraph.insertEdge("5", "3", 1);
		pseudoGraph.insertEdge("5", "4", 1);
		pseudoGraph.insertEdge("5", "5", 1);

	}

	private void setupStageMultiGraph() {
		// multiGraph = new GraphAdjacencyList<>(GraphType.MULTIGRAPH);
		multiGraph = new GraphAdjacencyMatrix<>(GraphType.MULTIGRAPH);
		multiGraph.insertVertex(1, "1");
		multiGraph.insertVertex(2, "2");
		multiGraph.insertVertex(3, "3");
		multiGraph.insertVertex(4, "4");
		multiGraph.insertVertex(5, "5");
		multiGraph.insertVertex(6, "6");

		multiGraph.insertEdge(1, 2, 1);
		multiGraph.insertEdge(2, 3, 1);
		multiGraph.insertEdge(3, 4, 1);
		multiGraph.insertEdge(4, 5, 1);
		multiGraph.insertEdge(5, 1, 1);

		multiGraph.insertEdge(1, 6, 1);
		multiGraph.insertEdge(2, 6, 1);
		multiGraph.insertEdge(3, 6, 1);
		multiGraph.insertEdge(4, 6, 1);
		multiGraph.insertEdge(5, 6, 1);
	}

	private void setupStageDirectedGraph() {
		// directedGraph1 = new GraphAdjacencyList<>(GraphType.DIRECTED);
		directedGraph1 = new GraphAdjacencyMatrix<>(GraphType.DIRECTED);
		directedGraph1.insertVertex(1, "1");
		directedGraph1.insertVertex(2, "2");
		directedGraph1.insertVertex(3, "3");
		directedGraph1.insertVertex(4, "4");
		directedGraph1.insertVertex(5, "5");
		directedGraph1.insertVertex(6, "6");
		directedGraph1.insertVertex(7, "7");
		directedGraph1.insertVertex(8, "8");
		directedGraph1.insertVertex(9, "9");
		directedGraph1.insertVertex(10, "10");
		directedGraph1.insertVertex(11, "11");

		directedGraph1.insertEdge(1, 2, 1);
		directedGraph1.insertEdge(2, 1, 1);
		directedGraph1.insertEdge(1, 3, 1);
		directedGraph1.insertEdge(3, 1, 1);
		directedGraph1.insertEdge(2, 4, 1);
		directedGraph1.insertEdge(9, 7, 1);
		directedGraph1.insertEdge(9, 11, 1);
		directedGraph1.insertEdge(6, 10, 1);
		directedGraph1.insertEdge(11, 7, 1);
		directedGraph1.insertEdge(8, 6, 1);
		directedGraph1.insertEdge(10, 11, 1);
		directedGraph1.insertEdge(5, 1, 1);
		directedGraph1.insertEdge(5, 6, 1);
		directedGraph1.insertEdge(5, 3, 1);
		directedGraph1.insertEdge(9, 4, 1);
		directedGraph1.insertEdge(5, 7, 1);
		directedGraph1.insertEdge(3, 6, 1);
		directedGraph1.insertEdge(4, 7, 1);
		directedGraph1.insertEdge(6, 7, 1);
		directedGraph1.insertEdge(8, 10, 1);
		directedGraph1.insertEdge(8, 11, 1);
		directedGraph1.insertEdge(5, 2, 1);
		directedGraph1.insertEdge(5, 4, 1);
		directedGraph1.insertEdge(2, 9, 1);
		directedGraph1.insertEdge(8, 7, 1);

	}

	private void setupStageMultiGraphDirected() {
		// multiGraphDirected = new GraphAdjacencyList<>(GraphType.MULTIGRAPH_DIRECTED);
		multiGraphDirected = new GraphAdjacencyMatrix<>(GraphType.MULTIGRAPH_DIRECTED);
	}

	private void setupStage6() {
		// simpleGraph2 = new GraphAdjacencyList<>(GraphType.SIMPLE);
		simpleGraph2 = new GraphAdjacencyMatrix<>(GraphType.SIMPLE);
		simpleGraph2.insertVertex("r", "r");
		simpleGraph2.insertVertex("s", "s");
		simpleGraph2.insertVertex("t", "t");
		simpleGraph2.insertVertex("u", "u");
		simpleGraph2.insertVertex("v", "v");
		simpleGraph2.insertVertex("w", "w");
		simpleGraph2.insertVertex("x", "x");
		simpleGraph2.insertVertex("y", "y");
		simpleGraph2.insertVertex("z", "z");
	}

	private void setupStage7() {
		// simpleGraph2 = new GraphAdjacencyList<>(GraphType.SIMPLE);
		simpleGraph2 = new GraphAdjacencyMatrix<>(GraphType.SIMPLE);

		simpleGraph2.insertVertex("a", "a");
		simpleGraph2.insertVertex("b", "b");
		simpleGraph2.insertVertex("c", "c");
		simpleGraph2.insertVertex("d", "d");
		simpleGraph2.insertVertex("e", "e");
		simpleGraph2.insertVertex("z", "z");

	}

	private void setupStage8() {
		// simpleGraph = new GraphAdjacencyList<>(GraphType.SIMPLE);
		simpleGraph = new GraphAdjacencyMatrix<>(GraphType.SIMPLE);

		simpleGraph.insertVertex(1, "a");
		simpleGraph.insertVertex(2, "b");
		simpleGraph.insertVertex(3, "c");
		simpleGraph.insertVertex(4, "d");

	}

	// insertVertex() method
	// Test 1
	@Test
	public void testInsertVertex1() {
		setupStageMultiGraphDirected();

		multiGraphDirected.insertVertex(1, "A");

		Assertions.assertEquals("A", multiGraphDirected.getVertex(1).getElement());
	}

	// Test 2
	// Valid that vertices that are already in the network cannot be entered.
	@Test
	public void testInsertVertex2() {
		setupStageSimpleGraph();
		simpleGraph.insertVertex(1, "AAaa");
		Assertions.assertEquals("A", simpleGraph.getVertex(1).getElement());
	}

	// Test 3
	@Test
	public void testInsertVertex3() {
		setupStageMultiGraphDirected();

		multiGraphDirected.insertVertex(1, "A");
		multiGraphDirected.insertVertex(2, "B");
		multiGraphDirected.insertVertex(3, "C");
		multiGraphDirected.insertVertex(4, "D");
		multiGraphDirected.insertVertex(5, "E");
		Assertions.assertEquals("A", multiGraphDirected.getVertex(1).getElement());
		Assertions.assertEquals("B", multiGraphDirected.getVertex(2).getElement());
		Assertions.assertEquals("C", multiGraphDirected.getVertex(3).getElement());
		Assertions.assertEquals("D", multiGraphDirected.getVertex(4).getElement());
		Assertions.assertEquals("E", multiGraphDirected.getVertex(5).getElement());
	}

	// deleteVertex() method
	// Test 1
	@Test
	public void testDeleteVertex1() {
		setupStageSimpleGraph();
		simpleGraph.deleteVertex(1);
		simpleGraph.deleteVertex(2);
		Assertions.assertNull(simpleGraph.getVertex(1));
		Assertions.assertNull(simpleGraph.getVertex(2));
	}

	// Test 2
	@Test
	public void testDeleteVertex2() {
		setupStageDirectedGraph();

		directedGraph1.deleteVertex(7);

		Assertions.assertNull(directedGraph1.getVertex(7));
		Assertions.assertThrows(GraphException.class, () -> directedGraph1.adjacent(4, 7));
		Assertions.assertThrows(GraphException.class, () -> directedGraph1.adjacent(5, 7));
		Assertions.assertThrows(GraphException.class, () -> directedGraph1.adjacent(6, 7));
		Assertions.assertThrows(GraphException.class, () -> directedGraph1.adjacent(8, 7));
		Assertions.assertThrows(GraphException.class, () -> directedGraph1.adjacent(9, 7));
		Assertions.assertThrows(GraphException.class, () -> directedGraph1.adjacent(11, 7));
	}

	// Test 3
	@Test
	public void testDeleteVertex3() {
		setupStageMultiGraph();
		multiGraph.deleteVertex(6);

		Assertions.assertNull(multiGraph.getVertex(6));

		Assertions.assertThrows(GraphException.class, () -> multiGraph.adjacent(1, 6));
		Assertions.assertThrows(GraphException.class, () -> multiGraph.adjacent(2, 6));
		Assertions.assertThrows(GraphException.class, () -> multiGraph.adjacent(3, 6));
		Assertions.assertThrows(GraphException.class, () -> multiGraph.adjacent(4, 6));
		Assertions.assertThrows(GraphException.class, () -> multiGraph.adjacent(5, 6));
	}

	// insertEdge() method
	// Test 1
	@Test
	public void testInsertEdge1() {
		setupStageSimpleGraph();
		// Insert edges in the graph
		simpleGraph.insertEdge(1, 2, 1);
		simpleGraph.insertEdge(2, 3, 1);
		simpleGraph.insertEdge(3, 4, 1);
		simpleGraph.insertEdge(4, 5, 1);
		simpleGraph.insertEdge(5, 1, 1);
		// Verify the order of adjacency lists
		Assertions.assertTrue(simpleGraph.adjacent(1, 2));
		Assertions.assertTrue(simpleGraph.adjacent(2, 3));
		Assertions.assertTrue(simpleGraph.adjacent(3, 4));
		Assertions.assertTrue(simpleGraph.adjacent(4, 5));
		Assertions.assertTrue(simpleGraph.adjacent(5, 1));
	}

	// Test 2
	@Test
	public void testInsertEdge2() {
		setupStageMultiGraphDirected();

		multiGraphDirected.insertVertex(1, "A");
		multiGraphDirected.insertEdge(1, 1, 1);

		try {
			multiGraphDirected.insertEdge(1, 1, 1);
			multiGraphDirected.insertEdge(1, 1, 1);
			multiGraphDirected.insertEdge(1, 1, 1);
			Assertions.assertTrue(multiGraphDirected.adjacent(1, 1));
		} catch (GraphException e) {
			fail("The multi directed graph accepts multiple edges and loops.");
		}
	}

	// Test 3
	@Test
	public void testInsertEdge3() {
		setupStageSimpleGraph();
		simpleGraph.insertEdge(1, 2, 1);
		Assertions.assertThrows(GraphException.class, () -> simpleGraph.insertEdge(1, 2, 1));
	}

	// insertEdge() method
	// Test 1
	// Remove loops from the graph
	@Test
	public void testDeleteEdge1() {
		setupStagePseudoGraph();
		pseudoGraph.deleteEdge("1", "1");
		pseudoGraph.deleteEdge("1", "5");
		Assertions.assertFalse(pseudoGraph.adjacent("1", "1"));
		Assertions.assertFalse(pseudoGraph.adjacent("1", "5"));
		Assertions.assertFalse(pseudoGraph.adjacent("5", "1"));
	}

	// Test 2
	// Remove only one address in a directed graph
	@Test
	public void testDeleteEdge2() {
		setupStageDirectedGraph();
		directedGraph1.deleteEdge(1, 2);
		directedGraph1.deleteEdge(1, 3);
		Assertions.assertFalse(directedGraph1.adjacent(1, 2));
		Assertions.assertFalse(directedGraph1.adjacent(1, 3));

		Assertions.assertTrue(directedGraph1.adjacent(2, 1));
		Assertions.assertTrue(directedGraph1.adjacent(3, 1));
	}

	// Test 3
	@Test
	public void testDeleteEdge3() {
		setupStagePseudoGraph();
		Assertions.assertThrows(GraphException.class, () -> pseudoGraph.deleteEdge("1", "9"));
	}

	// adjacent() method
	// Test 1
	@Test
	public void testAdjacent1() {
		setupStageDirectedGraph();
		Assertions.assertTrue(directedGraph1.adjacent(4, 7));
		Assertions.assertTrue(directedGraph1.adjacent(5, 7));
		Assertions.assertTrue(directedGraph1.adjacent(6, 7));
		Assertions.assertTrue(directedGraph1.adjacent(8, 7));
		Assertions.assertTrue(directedGraph1.adjacent(9, 7));
		Assertions.assertTrue(directedGraph1.adjacent(11, 7));
	}

	// Test 2
	@Test
	public void testAdjacent2() {
		setupStageDirectedGraph();
		Assertions.assertFalse(directedGraph1.adjacent(7, 4));
		Assertions.assertFalse(directedGraph1.adjacent(7, 5));
		Assertions.assertFalse(directedGraph1.adjacent(7, 6));
		Assertions.assertFalse(directedGraph1.adjacent(7, 8));
		Assertions.assertFalse(directedGraph1.adjacent(7, 9));
		Assertions.assertFalse(directedGraph1.adjacent(7, 11));
	}

	// Test 3
	@Test
	public void testAdjacent3() {
		setupStageDirectedGraph();
		Assertions.assertThrows(GraphException.class, () -> directedGraph1.adjacent(1, 18));
		Assertions.assertThrows(GraphException.class, () -> directedGraph1.adjacent(18, 1));
	}

	// BFS() method
	// Test 1
	@Test
	public void testBFS1() {
		setupStage6();

		simpleGraph2.insertEdge("v", "r", 1);
		simpleGraph2.insertEdge("r", "s", 1);
		simpleGraph2.insertEdge("s", "w", 1);
		simpleGraph2.insertEdge("w", "t", 1);
		simpleGraph2.insertEdge("w", "x", 1);
		simpleGraph2.insertEdge("t", "x", 1);
		simpleGraph2.insertEdge("t", "u", 1);
		simpleGraph2.insertEdge("x", "u", 1);
		simpleGraph2.insertEdge("u", "y", 1);
		simpleGraph2.insertEdge("x", "y", 1);

		simpleGraph2.BFS("s");

		Assertions.assertEquals(0, simpleGraph2.getVertex("s").getDistance());
		Assertions.assertEquals(1, simpleGraph2.getVertex("r").getDistance());
		Assertions.assertEquals(1, simpleGraph2.getVertex("w").getDistance());
		Assertions.assertEquals(2, simpleGraph2.getVertex("v").getDistance());
		Assertions.assertEquals(2, simpleGraph2.getVertex("t").getDistance());
		Assertions.assertEquals(2, simpleGraph2.getVertex("x").getDistance());
		Assertions.assertEquals(3, simpleGraph2.getVertex("u").getDistance());
		Assertions.assertEquals(3, simpleGraph2.getVertex("y").getDistance());
	}

	// Test 2
	@Test
	public void testBFS2() {
		setupStageSimpleGraph();

		simpleGraph.insertEdge(1, 10, 1);
		simpleGraph.insertEdge(10, 3, 1);
		simpleGraph.insertEdge(10, 2, 1);
		simpleGraph.insertEdge(3, 5, 1);
		simpleGraph.insertEdge(5, 2, 1);
		simpleGraph.insertEdge(2, 6, 1);
		simpleGraph.insertEdge(6, 4, 1);
		simpleGraph.insertEdge(4, 7, 1);

		simpleGraph.BFS(10);

		Assertions.assertEquals(1, simpleGraph.getVertex(1).getDistance());
		Assertions.assertEquals(1, simpleGraph.getVertex(2).getDistance());
		Assertions.assertEquals(1, simpleGraph.getVertex(3).getDistance());
		Assertions.assertEquals(2, simpleGraph.getVertex(5).getDistance());
		Assertions.assertEquals(2, simpleGraph.getVertex(6).getDistance());
		Assertions.assertEquals(3, simpleGraph.getVertex(4).getDistance());
		Assertions.assertEquals(4, simpleGraph.getVertex(7).getDistance());

	}

	// Test 3
	// Validate that the BFS method does not use paths that do not exist (by edges
	// not created).
	@Test
	public void testBFS3() {
		setupStageSimpleGraph();

		simpleGraph.insertEdge(1, 10, 1);
		simpleGraph.insertEdge(1, 5, 1);
		simpleGraph.insertEdge(1, 3, 1);

		simpleGraph.BFS(1);

		Assertions.assertEquals(Integer.MAX_VALUE, simpleGraph.getVertex(7).getDistance());

	}

	// dijkstra() method
	// Test 1
	@Test
	public void testDijkstra1() {
		setupStage7();

		simpleGraph2.insertEdge("a", "b", 4);
		simpleGraph2.insertEdge("a", "d", 2);
		simpleGraph2.insertEdge("d", "e", 3);
		simpleGraph2.insertEdge("b", "c", 3);
		simpleGraph2.insertEdge("b", "e", 3);
		simpleGraph2.insertEdge("c", "z", 2);
		simpleGraph2.insertEdge("e", "z", 1);

		ArrayList<Integer> distances = new ArrayList<>(Arrays.asList(0, 4, 7, 2, 5, 6));
		assertEquals(distances, simpleGraph2.dijkstra("a"));
	}

	// Test 2
	@Test
	public void testDijkstra2() {
		setupStageSimpleGraph();
		ArrayList<Integer> distances = new ArrayList<>(Arrays.asList(0, Integer.MAX_VALUE, Integer.MAX_VALUE,
				Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE));
		assertEquals(distances, simpleGraph.dijkstra(1));
	}

	// Test 3
	@Test
	public void testDijkstra3() {
		setupStagePseudoGraph();
		ArrayList<Integer> distances = new ArrayList<>(Arrays.asList(1, 2, 0, 2, 1));
		assertEquals(distances, pseudoGraph.dijkstra("3"));
	}

	// kruskal() method
	// Test 1
	@Test
	public void testKruskal1() {
		setupStage8();

		simpleGraph.insertEdge(1, 2, 10);
		simpleGraph.insertEdge(1, 3, 7);
		simpleGraph.insertEdge(2, 3, 5);
		simpleGraph.insertEdge(2, 4, 3);

		ArrayList<Edge<Integer, String>> path = simpleGraph.kruskal();
		assertEquals(new Edge<>(simpleGraph.getVertex(2), simpleGraph.getVertex(4), 3), path.get(0));
		assertEquals(new Edge<>(simpleGraph.getVertex(2), simpleGraph.getVertex(3), 5), path.get(1));
		assertEquals(new Edge<>(simpleGraph.getVertex(1), simpleGraph.getVertex(3), 7), path.get(2));
	}

	// Test 2
	@Test
	public void testKruskal2() {
		setupStageSimpleGraph();
		Assertions.assertTrue(simpleGraph.kruskal().isEmpty());
	}

	// Test 3
	@Test
	public void testKruskal3() {
		setupStageDirectedGraph();
		Assertions.assertThrows(GraphException.class, () -> directedGraph1.kruskal());
	}

}
