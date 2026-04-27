public class GraphService {

    private Graph graph; // holds graph

    public GraphService(Graph graph) {
        this.graph = graph; // assign graph
    }

    // run DFS
    public void runDFS(Vertex start) {

        DFS dfs = new DFS(graph); // create DFS
        dfs.dfs(start);           // execute
    }

    // run BFS
    public void runBFS(Vertex start) {

        BFS bfs = new BFS(graph); // create BFS
        bfs.bfs(start);           // execute
    }
}