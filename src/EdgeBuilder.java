import java.util.*;

public class EdgeBuilder {

    private int k;

    public EdgeBuilder(int k) {
        this.k = k;
    }

    public void buildEdges(Graph graph) {

        List<Vertex> vertices = graph.getVertices();

        for (Vertex v : vertices) {

            List<Similarity> sims = new ArrayList<>();

            for (Vertex u : vertices) {

                if (v == u) continue;

                double score = similarity(v.getSong(), u.getSong());

                sims.add(new Similarity(u, score));
            }

            sims.sort((a, b) -> Double.compare(b.score, a.score));

            for (int i = 0; i < Math.min(k, sims.size()); i++) {

                Vertex neighbour = sims.get(i).vertex;

                graph.addEdges(v, neighbour); // graph prevents duplicates
            }
        }
    }

    private double similarity(Song a, Song b) {

        // STREAM SIMILARITY
        long s1 = a.getStreams();
        long s2 = b.getStreams();

        long max = Math.max(s1, s2);
        double streamSim = (max == 0) ? 0 : 1.0 - (Math.abs(s1 - s2) / (double) max);

        // ARTIST
        double artistSim = a.getArtist().equalsIgnoreCase(b.getArtist()) ? 1.0 : 0.0;

        // GENRE
        double genreSim = a.getGenre().equalsIgnoreCase(b.getGenre()) ? 1.0 : 0.0;

        return (0.5 * streamSim) + (0.3 * artistSim) + (0.2 * genreSim);
    }

    private static class Similarity {

        Vertex vertex;
        double score;

        public Similarity(Vertex v, double s) {
            this.vertex = v;
            this.score = s;
        }
    }
}