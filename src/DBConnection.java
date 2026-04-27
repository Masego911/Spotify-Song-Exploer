import java.sql.Connection;
import java.sql.DriverManager;

/*
 * DBConnection
 *
 * PURPOSE:
 * Creates connection to SQLite database.
 */
public class DBConnection {

    public static Connection connect() throws Exception {

        Class.forName("org.sqlite.JDBC"); // load driver

        String url = "jdbc:sqlite:spotify.db"; // database file

        return DriverManager.getConnection(url); // return connection
    }
}