package repositories.connections;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionMySql implements ConnectionDatabase {

    private static final String URL = "jdbc:mysql://localhost:3306/cineguardian";
    private static final String user = "root";
    private static final String password = "my-secret";

    @Override
    public Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, user, password);
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void closeConnection(Connection conn) {
        if(conn != null){
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println("Erro ao fechar a conexão");
                e.printStackTrace();
            }
        }
    }
}
