import java.util.*; // import collections

/*
 * BFS (Breadth-First Search)
 *
 * PURPOSE:
 * Traverse graph level by level starting from a node
 */
public class BFS {

    private Graph graph; // reference to graph

    public BFS(Graph graph) {
        this.graph = graph; // store graph
    }

    public void bfs(Vertex start) {

        Set<Vertex> visited = new HashSet<>(); // track visited nodes

        Queue<Vertex> queue = new LinkedList<>(); // FIFO structure

        visited.add(start); // mark start as visited
        queue.add(start);   // enqueue start

        while (!queue.isEmpty()) { // loop until queue empty

            Vertex current = queue.poll(); // remove next node

            // PROCESS NODE
            System.out.println(
                    current.getSong().getTitle() + " by " +
                            current.getSong().getArtist()
            );

            // GET NEIGHBOURS SAFELY
            for (Vertex neighbour :
                    graph.getAdjacencyList().getOrDefault(current, new ArrayList<>())) {

                // CHECK IF NOT VISITED
                if (!visited.contains(neighbour)) {

                    visited.add(neighbour); // mark visited
                    queue.add(neighbour);   // enqueue
                }
            }
        }
    }
}