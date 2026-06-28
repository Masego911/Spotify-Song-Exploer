import java.util.List;     // for List interface
import java.util.ArrayList; // for dynamic lists
import java.util.Scanner; // for user input
import java.nio.file.Files;
import java.nio.file.Path;

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
            String filePath = "Most Streamed Spotify Songs 2024.csv";
            boolean importRequested = java.util.Arrays.asList(args).contains("--import");
            if (importRequested) {
                ETLPipeline.run(filePath);

                System.out.println("ETL complete.");
            }

            // -----------------------------------------
            // STEP 3: LOAD GRAPH
            // -----------------------------------------
            Graph graph = GraphLoader.loadGraph();
            if (graph.getVertices().isEmpty() && Files.exists(Path.of(filePath))) {
                System.out.println("Database is empty; importing the bundled dataset.");
                ETLPipeline.run(filePath);
                graph = GraphLoader.loadGraph();
            }

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
            String search = scanner.hasNextLine() ? scanner.nextLine() : "";

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

                int choice = readChoice(scanner, matches.size());

                start = matches.get(choice - 1);
            }

            // -----------------------------------------
            // STEP 7: SHOW SELECTED SONG
            // --------------------C:\Users\maseg\IdeaProjects\Spotify Song ExplorerC:\Users\maseg\IdeaProjects\Spotify Song Explorer---------------------
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
            int traversalLimit = 25;
            System.out.println("\nDFS Traversal (first " + traversalLimit + "):");
            printVertices(new DFS(graph).traverse(start, traversalLimit));

            // -----------------------------------------
            // STEP 10: BFS
            // -----------------------------------------
            System.out.println("\nBFS Traversal (first " + traversalLimit + "):");
            printVertices(new BFS(graph).traverse(start, traversalLimit));

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    private static int readChoice(Scanner scanner, int maximum) {
        String input = scanner.hasNextLine() ? scanner.nextLine().trim() : "";
        try {
            int choice = Integer.parseInt(input);
            return choice >= 1 && choice <= maximum ? choice : 1;
        } catch (NumberFormatException ignored) {
            return 1;
        }
    }

    private static void printVertices(List<Vertex> vertices) {
        for (Vertex vertex : vertices) {
            System.out.println(vertex.getSong().getTitle() + " by " + vertex.getSong().getArtist());
        }
    }

    /*
     * FIND MULTIPLE MATCHES (ALPHABETICAL)
     */
    public static List<Vertex> findVertices(Graph graph, String query) {

        List<Vertex> matches = new ArrayList<>();

        String normalizedQuery = query == null ? "" : query.trim().toLowerCase(java.util.Locale.ROOT);
        for (Vertex v : graph.getVertices()) {

            String title = v.getSong().getTitle().toLowerCase(java.util.Locale.ROOT);
            String artist = v.getSong().getArtist().toLowerCase(java.util.Locale.ROOT);

            if (title.contains(normalizedQuery) || artist.contains(normalizedQuery)) {

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
