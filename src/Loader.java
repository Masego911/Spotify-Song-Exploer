import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

public class Loader {

    public static void load(List<Song> songs) throws Exception {

        String sql = "INSERT INTO songs (id, title, artist, streams, genre) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.connect()) {
            conn.setAutoCommit(false);
            try (PreparedStatement deleteEdges = conn.prepareStatement("DELETE FROM edges");
                 PreparedStatement deleteSongs = conn.prepareStatement("DELETE FROM songs");
                 PreparedStatement ps = conn.prepareStatement(sql)) {
                deleteEdges.executeUpdate();
                deleteSongs.executeUpdate();

                for (Song s : songs) {
                    ps.setInt(1, s.getId());
                    ps.setString(2, s.getTitle());
                    ps.setString(3, s.getArtist());
                    ps.setLong(4, s.getStreams());
                    ps.setString(5, s.getGenre());
                    ps.addBatch();
                }
                ps.executeBatch();
                conn.commit();
            } catch (Exception e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }
        }
    }
}
