package repositories.connections;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionSqlServer {

    private static final String URL = "jdbc:sqlserver://localhost:1433;database=cineguardian";

    private static String USER = "root";
    private static String PASSWORD = "password";

    public ConnectionSqlServer(){}

    public ConnectionSqlServer(String user, String password){
        USER = user;
        PASSWORD = password;
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
