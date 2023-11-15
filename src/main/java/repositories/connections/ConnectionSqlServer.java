package repositories.connections;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionSqlServer implements ConnectionDatabase {

    private static final String URL = "jdbc:sqlserver://ec2-44-194-47-186.compute-1.amazonaws.com:1433;database=cineguardian;encrypt=false;trustServerCertificate=true;";

    private static final String USER = "sa";
    private static final String PASSWORD = "Cine@2023!";


    @Override
    public Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
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
