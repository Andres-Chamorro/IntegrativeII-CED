package com.example.pipegame.model;

/**
 * The Edge class represents an edge in a graph, with a source vertex, a destination vertex, and a
 * weight.
 */
public class Edge<T> {

    private Vertex<T> source;
    private Vertex<T> destination;
    private int weight;

    // The code you provided is a constructor for the `Edge` class. It takes three parameters:
    // `source`, `destination`, and `weight`.
    public Edge(Vertex<T> source, Vertex<T> destination, int weight) {
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }

    /**
     * The getSource() function returns the source vertex of a graph.
     * 
     * @return The method is returning an object of type Vertex<T>.
     */
    public Vertex<T> getSource() {
        return source;
    }

    /**
     * The function sets the source vertex for a graph.
     * 
     * @param source The source parameter is of type Vertex<T>, which represents a vertex in a graph.
     */
    public void setSource(Vertex<T> source) {
        this.source = source;
    }

    /**
     * The function returns the destination vertex.
     * 
     * @return The method is returning an object of type Vertex<T>.
     */
    public Vertex<T> getDestination() {
        return destination;
    }

   /**
    * The function sets the destination vertex for a given object.
    * 
    * @param destination The destination parameter is of type Vertex<T>, where T is the generic type of
    * the vertex. It represents the vertex that the current vertex is being connected to as a
    * destination.
    */
    public void setDestination(Vertex<T> destination) {
        this.destination = destination;
    }

    // The `getWeight()` method in the `Edge` class is a getter method that returns the weight of the
    // edge. It retrieves the value of the `weight` variable and returns it as an integer.
    public int getWeight() {
        return weight;
    }

    /**
     * The function sets the weight of an object.
     * 
     * @param weight The weight parameter is an integer that represents the weight value to be set.
     */
    public void setWeight(int weight) {
        this.weight = weight;
    }
}