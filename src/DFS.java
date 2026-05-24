import java.util.HashSet; // stores visited nodes
import java.util.Set;     // interface for visited tracking

/*
 * DFS (Depth-First Search)
 *
 * PURPOSE:
 * Traverse the graph safely without infinite recursion.
 *
 * GUARANTEES:
 * 1. Each node is visited once
 * 2. Recursion stops on revisits
 * 3. Traversal depth is bounded (prevents stack overflow)
 */
public class DFS {

    private Graph graph;         // reference to graph structure
    private Set<Vertex> visited; // tracks visited nodes

    // limit recursion depth to prevent stack overflow on large graphs
    private static final int MAX_DEPTH = 1000;

    public DFS(Graph graph) {
        this.graph = graph;           // store graph reference
        this.visited = new HashSet<>(); // initialise visited set
    }

    public void dfs(Vertex start) {

        visited.clear(); // reset visited before traversal

        // start recursion with depth = 0
        dfsVisited(start, 0);
    }

    /*
     * Recursive DFS method
     *
     * depth → tracks how deep recursion goes
     */
    private void dfsVisited(Vertex v, int depth) {

        // -----------------------------------------
        // 1. STOP CONDITIONS (CRITICAL)
        // -----------------------------------------

        // stop if null node
        if (v == null) return;

        // stop if already visited (prevents cycles)
        if (visited.contains(v)) return;

        // stop if depth too large (prevents stack overflow)
        if (depth > MAX_DEPTH) return;

        // -----------------------------------------
        // 2. MARK AS VISITED
        // -----------------------------------------
        visited.add(v);

        // -----------------------------------------
        // 3. PROCESS NODE
        // -----------------------------------------
        System.out.println(
                v.getSong().getTitle() + " by " +
                        v.getSong().getArtist()
        );

        // -----------------------------------------
        // 4. GET NEIGHBOURS SAFELY
        // -----------------------------------------
        // adjacency list may return null if not initialised
        if (!graph.getAdjacencyList().containsKey(v)) return;

        // -----------------------------------------
        // 5. RECURSE ON NEIGHBOURS
        // -----------------------------------------
        for (Vertex neighbour : graph.getAdjacencyList().get(v)) {

            dfsVisited(neighbour, depth + 1); // go deeper
        }
    }
}
