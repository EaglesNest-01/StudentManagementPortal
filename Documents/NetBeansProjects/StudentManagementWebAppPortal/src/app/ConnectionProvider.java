package app;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionProvider {
    private static Connection conn;

    public static Connection getConnection() throws Exception {
        if (conn == null || conn.isClosed()) {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/StudentInfo",
                "postgres",
                "UwU123"
            );
        }
        return conn;
    }
}
