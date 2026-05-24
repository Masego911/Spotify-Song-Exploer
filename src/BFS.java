import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class BFS {
    private final Graph graph;

    public BFS(Graph graph) {
        this.graph = graph;
    }

    public void bfs(Vertex start) {
        for (Vertex vertex : traverse(start, Integer.MAX_VALUE)) {
            System.out.println(vertex.getSong().getTitle() + " by " + vertex.getSong().getArtist());
        }
    }

    public List<Vertex> traverse(Vertex start, int maxVertices) {
        if (start == null || maxVertices <= 0) return Collections.emptyList();

        Set<Vertex> visited = new HashSet<>();
        List<Vertex> result = new ArrayList<>();
        Queue<Vertex> queue = new ArrayDeque<>();
        visited.add(start);
        queue.add(start);

        while (!queue.isEmpty() && result.size() < maxVertices) {
            Vertex current = queue.remove();
            result.add(current);
            for (Vertex neighbour : graph.getAdjacencyList()
                    .getOrDefault(current, Collections.emptyList())) {
                if (visited.add(neighbour)) queue.add(neighbour);
            }
        }
        return result;
    }
}
