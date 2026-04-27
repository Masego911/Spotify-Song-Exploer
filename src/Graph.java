import java.util.*;

public class Graph {

    private List<Vertex> vertices;
    private Map<Vertex, List<Vertex>> adjacencyList;
    private int edgeCount;

    public Graph() {
        vertices = new ArrayList<>();
        adjacencyList = new HashMap<>();
        edgeCount = 0;
    }

    public void addVertex(Vertex v) {
        vertices.add(v);
        adjacencyList.putIfAbsent(v, new ArrayList<>());
    }

    public void addEdges(Vertex v1, Vertex v2) {

        adjacencyList.putIfAbsent(v1, new ArrayList<>());
        adjacencyList.putIfAbsent(v2, new ArrayList<>());

        if (!adjacencyList.get(v1).contains(v2)) {

            adjacencyList.get(v1).add(v2);
            adjacencyList.get(v2).add(v1);

            edgeCount++;
        }
    }

    public List<Vertex> getVertices() {
        return vertices;
    }

    public Map<Vertex, List<Vertex>> getAdjacencyList() {
        return adjacencyList;
    }

    public int getEdges() {
        return edgeCount;
    }
}