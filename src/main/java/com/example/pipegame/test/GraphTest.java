package com.example.pipegame.test;

import com.example.pipegame.model.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * IMPORTANT NOTE: In order to test both versions of the graph using the same
 * tests,
 * you need to perform the following steps: you must comment the lines of code
 * that
 * initialize the graph in the setups using lists as data structure, and in
 * turn,
 * the corresponding lines that initialize the graph using arrays must be
 * uncommented.
 */
public class GraphTest {

    private IGraph<Integer> simpleGraph;
    private IGraph<Integer> simpleGraph2;

    private void setupStageSimpleGraph() {
        simpleGraph = new GraphAdjacentyMatriz<>();
        simpleGraph.addVertex(new Vertex<>(1));
        simpleGraph.addVertex(new Vertex<>(2));
        simpleGraph.addVertex(new Vertex<>(3));
        simpleGraph.addVertex(new Vertex<>(4));
        simpleGraph.addVertex(new Vertex<>(5));
        simpleGraph.addVertex(new Vertex<>(6));
        simpleGraph.addVertex(new Vertex<>(7));
        simpleGraph.addVertex(new Vertex<>(8));
        simpleGraph.addVertex(new Vertex<>(9));
        simpleGraph.addVertex(new Vertex<>(10));
    }
    

    // Test 2
    // Valid that vertices that are already in the network cannot be entered.
    @Test
    public void testInsertVertex2() {
        setupStageSimpleGraph();
        simpleGraph.addVertex(new Vertex<>(20));
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

    // insertEdge() method
    // Test 1
    @Test
    public void testInsertEdge1() {
        setupStageSimpleGraph();
        // Insert edges in the graph
        simpleGraph.addEdge(simpleGraph.getVertex(1), simpleGraph.getVertex(2), 1);
        simpleGraph.addEdge(simpleGraph.getVertex(2), simpleGraph.getVertex(3), 1);
        simpleGraph.addEdge(simpleGraph.getVertex(3), simpleGraph.getVertex(4), 1);
        simpleGraph.addEdge(simpleGraph.getVertex(4), simpleGraph.getVertex(5), 1);
        simpleGraph.addEdge(simpleGraph.getVertex(5), simpleGraph.getVertex(6), 1);

        // Validate that the edges were inserted correctly
        Assertions.assertTrue(simpleGraph.adjacent(1, 2));
    }

    // Test 3
    @Test
    public void testInsertEdge3() {
        setupStageSimpleGraph();
        simpleGraph.addEdge(simpleGraph.getVertex(1), simpleGraph.getVertex(2), 1);
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
