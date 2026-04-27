import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

public class Loader {

    public static void load(List<Song> songs) throws Exception {

        Connection conn = DBConnection.connect();

        String sql = "INSERT OR IGNORE INTO songs (id, title, artist, streams, genre) VALUES (?, ?, ?, ?, ?)";

        PreparedStatement ps = conn.prepareStatement(sql);

        for (Song s : songs) {

            ps.setInt(1, s.getId());
            ps.setString(2, s.getTitle());
            ps.setString(3, s.getArtist());
            ps.setLong(4, s.getStreams()); // FIX
            ps.setString(5, s.getGenre());

            ps.executeUpdate();
        }

        ps.close();
        conn.close();
    }
}