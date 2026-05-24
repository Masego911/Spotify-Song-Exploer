import java.util.List;     // for List interface
import java.util.ArrayList; // for dynamic lists
import java.util.Scanner; // for user input

/*
 * MAIN CLASS
 *
 * PURPOSE:
 * Full system controller:
 * - Loads data
 * - Builds graph
 * - Allows search + selection
 * - Generates recommendations
 * - Runs DFS and BFS
 */
public class Main {

    public static void main(String[] args) {

        try {

            // -----------------------------------------
            // STEP 1: CREATE DATABASE
            // -----------------------------------------
            DBSetup.createTables();

            // -----------------------------------------
            // STEP 2: RUN ETL (ONLY WHEN NEEDED)
            // -----------------------------------------
            boolean runETL = false;

            if (runETL) {

                String filePath = "Most Streamed Spotify Songs 2024.csv";

                ETLPipeline.run(filePath);

                System.out.println("ETL complete.");
            }

            // -----------------------------------------
            // STEP 3: LOAD GRAPH
            // -----------------------------------------
            Graph graph = GraphLoader.loadGraph();

            // -----------------------------------------
            // STEP 4: BUILD EDGES (k-NN)
            // -----------------------------------------
            EdgeBuilder builder = new EdgeBuilder(10);

            builder.buildEdges(graph);

            // -----------------------------------------
            // STEP 5: GRAPH DEBUG
            // -----------------------------------------
            System.out.println("\n--- GRAPH DEBUG ---");
            System.out.println("Vertices: " + graph.getVertices().size());
            System.out.println("Edges: " + graph.getEdges());

            if (graph.getVertices().isEmpty()) {
                System.out.println("Graph is empty.");
                return;
            }

            // -----------------------------------------
            // STEP 6: SEARCH + SELECTION
            // -----------------------------------------
            Scanner scanner = new Scanner(System.in);

            System.out.print("\nEnter artist or song: ");
            String search = scanner.nextLine();

            List<Vertex> matches = findVertices(graph, search);

            Vertex start;

            if (matches.isEmpty()) {

                System.out.println("No matches found. Using default.");
                start = graph.getVertices().get(0);

            } else {

                System.out.println("\nMatches (alphabetical):");

                for (int i = 0; i < matches.size(); i++) {

                    Vertex v = matches.get(i);

                    System.out.println((i + 1) + ". " +
                            v.getSong().getArtist() + " - " +
                            v.getSong().getTitle());
                }

                System.out.print("\nSelect option (1-" + matches.size() + "): ");

                int choice = scanner.nextInt();

                if (choice < 1 || choice > matches.size()) {
                    choice = 1;
                }

                start = matches.get(choice - 1);
            }

            // -----------------------------------------
            // STEP 7: SHOW SELECTED SONG
            // -----------------------------------------
            System.out.println("\nStart Vertex: " +
                    start.getSong().getTitle() + " by " +
                    start.getSong().getArtist());

            // -----------------------------------------
            // STEP 8: RECOMMENDATIONS
            // -----------------------------------------
            Recommender recommender = new Recommender(graph);

            int kRecommendations = 5;

            System.out.println("\nTop " + kRecommendations + " Recommendations:");

            for (Vertex v : recommender.getTopKRecommendations(start, kRecommendations)) {

                System.out.println(
                        v.getSong().getArtist() + " - " +
                                v.getSong().getTitle()
                );
            }

            // -----------------------------------------
            // STEP 9: DFS
            // -----------------------------------------
            System.out.println("\nDFS Traversal:");
            new DFS(graph).dfs(start);

            // -----------------------------------------
            // STEP 10: BFS
            // -----------------------------------------
            System.out.println("\nBFS Traversal:");
            new BFS(graph).bfs(start);

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    /*
     * FIND MULTIPLE MATCHES (ALPHABETICAL)
     */
    public static List<Vertex> findVertices(Graph graph, String query) {

        List<Vertex> matches = new ArrayList<>();

        for (Vertex v : graph.getVertices()) {

            String title = v.getSong().getTitle().toLowerCase();
            String artist = v.getSong().getArtist().toLowerCase();

            if (title.contains(query.toLowerCase()) ||
                    artist.contains(query.toLowerCase())) {

                matches.add(v);
            }
        }

        // sort alphabetically by artist, then title
        matches.sort((a, b) -> {

            int artistCompare = a.getSong().getArtist()
                    .compareToIgnoreCase(b.getSong().getArtist());

            if (artistCompare != 0) return artistCompare;

            return a.getSong().getTitle()
                    .compareToIgnoreCase(b.getSong().getTitle());
        });

        return matches;
    }
}