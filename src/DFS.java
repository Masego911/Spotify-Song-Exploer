import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DFS {
    private final Graph graph;
    private final Set<Vertex> visited = new HashSet<>();

    public DFS(Graph graph) {
        this.graph = graph;
    }

    public void dfs(Vertex start) {
        for (Vertex vertex : traverse(start, Integer.MAX_VALUE)) {
            System.out.println(vertex.getSong().getTitle() + " by " + vertex.getSong().getArtist());
        }
    }

    public List<Vertex> traverse(Vertex start, int maxVertices) {
        if (start == null || maxVertices <= 0) return Collections.emptyList();
        visited.clear();
        List<Vertex> result = new ArrayList<>();
        Deque<Vertex> stack = new ArrayDeque<>();
        stack.push(start);

        while (!stack.isEmpty() && result.size() < maxVertices) {
            Vertex current = stack.pop();
            if (!visited.add(current)) continue;
            result.add(current);

            List<Vertex> neighbours = graph.getAdjacencyList()
                    .getOrDefault(current, Collections.emptyList());
            for (int i = neighbours.size() - 1; i >= 0; i--) {
                Vertex neighbour = neighbours.get(i);
                if (!visited.contains(neighbour)) stack.push(neighbour);
            }
        }
        return result;
    }
}
