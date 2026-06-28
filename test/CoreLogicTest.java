import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CoreLogicTest {
    @Test
    void extractorHandlesQuotedCommasEscapedQuotesAndTrailingFields() throws Exception {
        Path csv = Files.createTempFile("songs", ".csv");
        Files.writeString(csv, "Track,Artist,Spotify Streams,Genre,Note\n"
                + "\"Song, One\",\"The \"\"Artist\"\"\",\"1,234\",Pop,\n");

        List<String[]> rows = Extractor.extract(csv.toString());

        assertEquals(2, rows.size());
        assertArrayEquals(new String[]{"Song, One", "The \"Artist\"", "1,234", "Pop", ""}, rows.get(1));
    }

    @Test
    void extractorFallsBackToWindows1252ForTheBundledDatasetEncoding() throws Exception {
        Path csv = Files.createTempFile("songs-windows-1252", ".csv");
        Files.write(csv, "Track,Artist,Spotify Streams\nCafé,Artist,100\n"
                .getBytes(Charset.forName("windows-1252")));

        assertEquals("Café", Extractor.extract(csv.toString()).get(1)[0]);
    }

    @Test
    void transformerUsesExactHeadersAndSkipsInvalidRows() {
        List<String[]> rows = Arrays.asList(
                new String[]{"Track", "Track Score", "Artist", "Spotify Streams"},
                new String[]{"Correct title", "99", "Artist", "1,234"},
                new String[]{"Missing streams", "88", "Artist", ""}
        );

        List<Song> songs = Transformer.transform(rows);

        assertEquals(1, songs.size());
        assertEquals("Correct title", songs.get(0).getTitle());
        assertEquals(1234, songs.get(0).getStreams());
    }

    @Test
    void unknownGenresDoNotCreateFalseSimilarity() {
        Song first = new Song(1, "One", "A", 100, "Unknown");
        Song second = new Song(2, "Two", "B", 100, "Unknown");
        assertEquals(0.5, SimilarityCalculator.between(first, second), 0.0001);
    }

    @Test
    void graphRejectsDuplicatesAndSelfEdges() {
        Graph graph = new Graph();
        Vertex vertex = new Vertex(new Song(1, "One", "A", 100, "Pop"));
        graph.addVertex(vertex);
        graph.addVertex(new Vertex(new Song(1, "Duplicate", "B", 200, "Rock")));
        graph.addEdges(vertex, vertex);

        assertEquals(1, graph.getVertices().size());
        assertEquals(0, graph.getEdges());
    }

    @Test
    void traversalsRespectTheirLimit() {
        Graph graph = new Graph();
        Vertex one = vertex(1);
        Vertex two = vertex(2);
        Vertex three = vertex(3);
        graph.addEdges(one, two);
        graph.addEdges(two, three);

        assertEquals(2, new BFS(graph).traverse(one, 2).size());
        assertEquals(2, new DFS(graph).traverse(one, 2).size());
    }

    private static Vertex vertex(int id) {
        return new Vertex(new Song(id, "Song " + id, "Artist", id, "Pop"));
    }
}
