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
        Objects.requireNonNull(v, "vertex");
        if (!adjacencyList.containsKey(v)) {
            vertices.add(v);
            adjacencyList.put(v, new ArrayList<>());
        }
    }

    public void addEdges(Vertex v1, Vertex v2) {
        Objects.requireNonNull(v1, "first vertex");
        Objects.requireNonNull(v2, "second vertex");
        if (v1.equals(v2)) return;

        addVertex(v1);
        addVertex(v2);

        if (!adjacencyList.get(v1).contains(v2)) {

            adjacencyList.get(v1).add(v2);
            adjacencyList.get(v2).add(v1);

            edgeCount++;
        }
    }

    public List<Vertex> getVertices() {
        return Collections.unmodifiableList(vertices);
    }

    public Map<Vertex, List<Vertex>> getAdjacencyList() {
        return adjacencyList;
    }

    public int getEdges() {
        return edgeCount;
    }
}
