import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class GraphLoader {

    public static Graph loadGraph() throws Exception {

        Graph graph = new Graph();

        Connection conn = DBConnection.connect();
        Statement stmt = conn.createStatement();

        ResultSet rs = stmt.executeQuery("SELECT * FROM songs");

        // Prevent duplicate vertices
        Map<Integer, Vertex> vertexMap = new HashMap<>();

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

            vertexMap.put(id, v);
        }

        rs.close();
        stmt.close();
        conn.close();

        return graph;
    }
}