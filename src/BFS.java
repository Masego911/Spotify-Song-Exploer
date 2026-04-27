import java.util.*;

public class BFS {

    private Graph graph;

    public BFS(Graph graph) {
        this.graph = graph;
    }

    public void bfs(Vertex start) {

        Set<Vertex> visited = new HashSet<>();
        Queue<Vertex> queue = new LinkedList<>();

        visited.add(start);
        queue.add(start);

        while (!queue.isEmpty()) {

            Vertex current = queue.poll();

            System.out.println(
                    current.getSong().getTitle() + " by " +
                            current.getSong().getArtist()
            );

            for (Vertex neighbour : graph.getAdjacencyList().get(current)) {

                if (!visited.contains(neighbour)) {

                    visited.add(neighbour);
                    queue.add(neighbour);
                }
            }
        }
    }
}