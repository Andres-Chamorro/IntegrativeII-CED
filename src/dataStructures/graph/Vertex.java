package dataStructures.graph;

public class Vertex<K extends Comparable<K>, E> {

    private final K key;
    private final E element;
    private Color color;
    private int distance = 0, discoveryTime = 0, finishTime = 0;
    private Vertex<K, E> predecessor;

    public Vertex(K key, E element) {
        this.key = key;
        this.element = element;
        color = Color.WHITE;
    }

    public K getKey() {
        return key;
    }

    public E getElement() {
        return element;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public void setPredecessor(Vertex<K, E> predecessor) {
        this.predecessor = predecessor;
    }

    public Vertex<K, E> getPredecessor() {
        return this.predecessor;
    }

    public void setDiscoveryTime(int discoverTime) {
        this.discoveryTime = discoverTime;
    }

    public void setFinishTime(int finishTime) {
        this.finishTime = finishTime;
    }

    public int getDiscoveryTime() {
        return discoveryTime;
    }

    public int getFinishTime() {
        return finishTime;
    }

}