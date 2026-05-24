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
        if (source == null) throw new IllegalArgumentException("source is required");
        if (k < 0) throw new IllegalArgumentException("k must be non-negative");

        // get neighbours
        List<Vertex> neighbours = graph.getAdjacencyList().get(source);

        if (neighbours == null || neighbours.isEmpty()) {
            return new ArrayList<>();
        }

        List<ScoredVertex> scored = new ArrayList<>();

        for (Vertex v : neighbours) {

            double score = SimilarityCalculator.between(source.getSong(), v.getSong());

            scored.add(new ScoredVertex(v, score));
        }

        // sort descending
        scored.sort(Comparator.comparingDouble((ScoredVertex value) -> value.score)
                .reversed()
                .thenComparingInt(value -> value.vertex.getSong().getId()));

        List<Vertex> results = new ArrayList<>();

        for (int i = 0; i < Math.min(k, scored.size()); i++) {
            results.add(scored.get(i).vertex);
        }

        return results;
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
