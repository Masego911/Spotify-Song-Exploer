import java.sql.Connection;
import java.sql.Statement;

/*
 * DBSetup
 *
 * PURPOSE:
 * Creates database tables if they do not exist.
 *
 * WHAT IT DOES:
 * - Creates "songs" table
 * - Creates "edges" table
 */
public class DBSetup {

    public static void createTables() throws Exception {

        try (Connection con = DBConnection.connect();
             Statement stmt = con.createStatement()) {
            stmt.execute("CREATE TABLE IF NOT EXISTS songs (" +
                    "id INTEGER PRIMARY KEY," +
                    "title TEXT NOT NULL," +
                    "artist TEXT NOT NULL," +
                    "streams INTEGER NOT NULL CHECK(streams >= 0)," +
                    "genre TEXT NOT NULL DEFAULT 'Unknown'" +
                    ")");

            stmt.execute("CREATE TABLE IF NOT EXISTS edges (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "from_song INTEGER NOT NULL," +
                    "to_song INTEGER NOT NULL," +
                    "UNIQUE(from_song, to_song)," +
                    "FOREIGN KEY(from_song) REFERENCES songs(id) ON DELETE CASCADE," +
                    "FOREIGN KEY(to_song) REFERENCES songs(id) ON DELETE CASCADE" +
                    ")");
            stmt.execute("CREATE INDEX IF NOT EXISTS idx_songs_artist ON songs(artist)");
        }
    }
}
