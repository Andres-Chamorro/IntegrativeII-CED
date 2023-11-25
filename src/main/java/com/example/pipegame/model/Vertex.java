package com.example.pipegame.model;


import java.util.ArrayList;

public class Vertex<T> {

    private T data;
    private Color color;
    private int distance;
    private Vertex<T> predecessor;
    private int discoveryTime;
    private int finishTime;
    private ArrayList<Vertex<T>> neighbors;

    // The `public Vertex(T data)` constructor is initializing a new instance of the `Vertex` class. It
    // takes a parameter `data` of type `T` and assigns it to the `data` field of the `Vertex` object.
    public Vertex(T data) {
        this.data = data;
        this.color = Color.WHITE;
        this.distance = Integer.MAX_VALUE;
        this.predecessor = null;
        neighbors = new ArrayList<>();
    }

    /**
     * The function sets the value of the data variable.
     * 
     * @param data The parameter "data" is of type T, which means it can be any type specified when the
     * class or method is used. It is used to set the value of the data variable in the class.
     */
    public void setData(T data) {
        this.data = data;
    }

    /**
     * The function returns the discovery time.
     * 
     * @return The method is returning the value of the variable discoveryTime.
     */
    public int getDiscoveryTime() {
        return discoveryTime;
    }
    

    /**
     * The function sets the discovery time of an object.
     * 
     * @param discoveryTime The parameter "discoveryTime" is an integer value that represents the time
     * at which something is discovered.
     */
    public void setDiscoveryTime(int discoveryTime) {
        this.discoveryTime = discoveryTime;
    }

    /**
     * The function returns the finish time.
     * 
     * @return The finish time.
     */
    public int getFinishTime() {
        return finishTime;
    }

    /**
     * The function sets the finish time for a task.
     * 
     * @param finishTime The finishTime parameter is an integer that represents the time at which a
     * task or process is completed.
     */
    public void setFinishTime(int finishTime) {
        this.finishTime = finishTime;
    }

    /**
     * The function returns a list of neighboring vertices.
     * 
     * @return An ArrayList of Vertex objects.
     */
    public ArrayList<Vertex<T>> getNeighbors() {
        return neighbors;
    }

    /**
     * The addNeighbor function adds a neighbor vertex to the current vertex's list of neighbors.
     * 
     * @param neighbor The parameter "neighbor" is of type Vertex<T>, where T is the generic type of
     * the Vertex class. It represents a neighboring vertex that is being added to the current vertex's
     * list of neighbors.
     */
    public void addNeighbor(Vertex<T> neighbor) {
        neighbors.add(neighbor);
    }

    /**
     * The function removes a neighbor from a vertex.
     * 
     * @param neighbor The parameter "neighbor" is of type Vertex<T>, which represents a vertex in a
     * graph.
     */
    public void removeNeighbor(Vertex<T> neighbor) {
        neighbors.remove(neighbor);
    }

    /**
     * The function returns the value of the data variable.
     * 
     * @return The method is returning the value of the variable "data".
     */
    public T getData() {
        return data;
    }

    /**
     * The getColor() function returns the color of an object.
     * 
     * @return The method is returning a Color object.
     */
    public Color getColor() {
        return color;
    }

    /**
     * The function sets the color of an object.
     * 
     * @param color The "color" parameter is of type "Color".
     */
    public void setColor(Color color) {
        this.color = color;
    }

    public int getDistance() {
        return distance;
    }

    /**
     * The function sets the value of the distance variable.
     * 
     * @param distance The distance parameter is an integer that represents the distance value to be
     * set.
     */
    public void setDistance(int distance) {
        this.distance = distance;
    }

   /**
    * The function returns the predecessor vertex of a given vertex.
    * 
    * @return The method is returning an object of type Vertex<T>.
    */
    public Vertex<T> getPredecessor() {
        return predecessor;
    }

    /**
     * The function sets the predecessor of a vertex in a graph.
     * 
     * @param predecessor The "predecessor" parameter is of type Vertex<T>, where T is a generic type.
     * It represents the previous vertex in a graph or tree data structure.
     */
    public void setPredecessor(Vertex<T> predecessor) {
        this.predecessor = predecessor;
    }
}
