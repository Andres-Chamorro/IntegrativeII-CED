package dataStructures.graph;

public record Edge<K extends Comparable<K>, E>(Vertex<K, E> start, Vertex<K, E> destination, int weight) {

}