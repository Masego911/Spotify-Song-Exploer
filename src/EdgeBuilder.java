import java.util.*;

public class EdgeBuilder {

    private final int k;

    public EdgeBuilder(int k) {
        if (k < 0) throw new IllegalArgumentException("k must be non-negative");
        this.k = k;
    }

    public void buildEdges(Graph graph) {

        List<Vertex> vertices = graph.getVertices();

        for (Vertex v : vertices) {

            PriorityQueue<Similarity> nearest = new PriorityQueue<>(Comparator
                    .comparingDouble((Similarity similarity) -> similarity.score)
                    .thenComparingInt(similarity -> -similarity.vertex.getSong().getId()));

            for (Vertex u : vertices) {

                if (v.equals(u)) continue;
                nearest.offer(new Similarity(u, SimilarityCalculator.between(v.getSong(), u.getSong())));
                if (nearest.size() > k) nearest.poll();
            }
            List<Similarity> ordered = new ArrayList<>(nearest);
            ordered.sort(Comparator.comparingDouble((Similarity similarity) -> similarity.score)
                    .reversed()
                    .thenComparingInt(similarity -> similarity.vertex.getSong().getId()));
            for (Similarity similarity : ordered) graph.addEdges(v, similarity.vertex);
        }
    }

    private static class Similarity {

        final Vertex vertex;
        final double score;

        public Similarity(Vertex v, double s) {
            this.vertex = v;
            this.score = s;
        }
    }
}
