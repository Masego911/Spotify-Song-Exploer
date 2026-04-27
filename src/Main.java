/*
 * MAIN CLASS
 *
 * PURPOSE:
 * Build system → launch dashboard UI
 */
import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {

        try {

            // -----------------------------------------
            // STEP 1: CREATE DATABASE TABLES
            // -----------------------------------------
            DBSetup.createTables();

            // -----------------------------------------
            // STEP 2: RUN ETL (ONLY FIRST TIME)
            // -----------------------------------------
            boolean runETL = false; // set true only once

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
            // STEP 4: BUILD EDGES (SIMILARITY)
            // -----------------------------------------
            EdgeBuilder builder = new EdgeBuilder(10);
            builder.buildEdges(graph);

            // -----------------------------------------
            // STEP 5: DEBUG CHECK
            // -----------------------------------------
            System.out.println("\n--- GRAPH DEBUG ---");
            System.out.println("Vertices: " + graph.getVertices().size());
            System.out.println("Edges: " + graph.getEdges());

            if (graph.getVertices().isEmpty()) {
                System.out.println("Graph is empty. Check ETL.");
                return;
            }

            // -----------------------------------------
            // STEP 6: LAUNCH DASHBOARD UI
            // -----------------------------------------
            SwingUtilities.invokeLater(() -> {

                DashboardUI ui = new DashboardUI(graph);

                ui.setVisible(true);
            });

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    /*
     * SEARCH FUNCTION (REQUIRED BY UI)
     */
    public static java.util.List<Vertex> findVertices(Graph graph, String query) {

        java.util.List<Vertex> matches = new java.util.ArrayList<>();

        for (Vertex v : graph.getVertices()) {

            String title = v.getSong().getTitle().toLowerCase();
            String artist = v.getSong().getArtist().toLowerCase();

            if (title.contains(query.toLowerCase()) ||
                    artist.contains(query.toLowerCase())) {

                matches.add(v);
            }
        }

        // sort alphabetically
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