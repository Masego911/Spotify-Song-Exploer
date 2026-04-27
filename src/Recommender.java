import java.util.*;

/*
 * RECOMMENDER
 *
 * PURPOSE:
 * Returns top-K recommended songs based on neighbours.
 */
public class Recommender {

    private Graph graph;

    public Recommender(Graph graph) {
        this.graph = graph;
    }

    public List<Vertex> getTopKRecommendations(Vertex source, int k) {

        // get neighbours
        List<Vertex> neighbours = graph.getAdjacencyList().get(source);

        if (neighbours == null || neighbours.isEmpty()) {
            return new ArrayList<>();
        }

        List<ScoredVertex> scored = new ArrayList<>();

        for (Vertex v : neighbours) {

            double score = similarity(source.getSong(), v.getSong());

            scored.add(new ScoredVertex(v, score));
        }

        // sort descending
        scored.sort((a, b) -> Double.compare(b.score, a.score));

        List<Vertex> results = new ArrayList<>();

        for (int i = 0; i < Math.min(k, scored.size()); i++) {
            results.add(scored.get(i).vertex);
        }

        return results;
    }

    // SAME LOGIC AS EDGE BUILDER (must stay consistent)
    private double similarity(Song a, Song b) {

        long s1 = a.getStreams();
        long s2 = b.getStreams();

        long max = Math.max(s1, s2);

        double streamSim = (max == 0)
                ? 0
                : 1.0 - (Math.abs(s1 - s2) / (double) max);

        double artistSim = a.getArtist().equalsIgnoreCase(b.getArtist()) ? 1.0 : 0.0;

        double genreSim = a.getGenre().equalsIgnoreCase(b.getGenre()) ? 1.0 : 0.0;

        return (0.5 * streamSim) + (0.3 * artistSim) + (0.2 * genreSim);
    }

    private static class ScoredVertex {
        Vertex vertex;
        double score;

        ScoredVertex(Vertex v, double s) {
            this.vertex = v;
            this.score = s;
        }
    }
}