import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
 * DBConnection
 *
 * PURPOSE:
 * Creates connection to SQLite database.
 */
public class DBConnection {

    public static Connection connect() throws SQLException {
        String databasePath = System.getProperty("spotify.db", "spotify.db");
        Connection connection = DriverManager.getConnection("jdbc:sqlite:" + databasePath);

        try (java.sql.Statement statement = connection.createStatement()) {
            statement.execute("PRAGMA foreign_keys = ON");
        }

        return connection;
    }
}
