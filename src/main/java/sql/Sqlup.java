package sql;
import java.sql.*;
public class Sqlup {
    static final String USER = "root";
    static final String PASS = "486999";
    private static final String DB_URL ="jdbc:mysql://localhost:3306/sid";
    Connection conn;

    public Sqlup() throws ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
    }
    void setup() throws SQLException {
        System.out.println("Connecting to database...");
        conn = DriverManager.getConnection(DB_URL, USER, PASS);
    }
}
