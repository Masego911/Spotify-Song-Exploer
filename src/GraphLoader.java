import java.sql.*;

public class GraphLoader {

    public static Graph loadGraph() throws Exception {

        Graph graph = new Graph();

        try (Connection conn = DBConnection.connect();
             Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id, title, artist, streams, genre FROM songs ORDER BY id")) {
            while (rs.next()) {
                int id = rs.getInt("id");
                Song song = new Song(
                        id,
                        rs.getString("title"),
                        rs.getString("artist"),
                        rs.getLong("streams"),
                        rs.getString("genre")
                );
                Vertex v = new Vertex(song);

                graph.addVertex(v);
            }
        }
        return graph;
    }
}
