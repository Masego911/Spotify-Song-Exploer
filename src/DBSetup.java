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

        Connection con = DBConnection.connect(); // connect to DB

        Statement stmt = con.createStatement(); // create SQL executor

        // create songs table
        String songsTable = "CREATE TABLE IF NOT EXISTS songs (" +
                "id INTEGER PRIMARY KEY," +
                "title TEXT," +
                "artist TEXT," +
                "streams INTEGER," +
                "genre TEXT" +
                ")";

        stmt.execute(songsTable); // execute SQL

        // create edges table
        String edgesTable = "CREATE TABLE IF NOT EXISTS edges (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "from_song INTEGER," +
                "to_song INTEGER," +
                "FOREIGN KEY(from_song) REFERENCES songs(id)," +
                "FOREIGN KEY(to_song) REFERENCES songs(id)" +
                ")";

        stmt.execute(edgesTable);

        con.close(); // close connection
    }
}