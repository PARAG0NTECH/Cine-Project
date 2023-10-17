package repositories;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionMySql {

    private static final String URL = "jdbc:mysql://localhost:3306/cineguardian";

    // Mude essas variáveis para seu usuário do MYSQL
    private static final String USER = "aluno";

    // Mude essas variáveis para sua senha do MYSQL
    private static final String PASSWORD = "aluno";

    public Connection open() {
        Connection connection = null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.out.println("Driver JDBC não encontrado");
            e.printStackTrace();
        } catch (SQLException e){
            System.out.println("Erro ao conectar ao banco de dados");
            e.printStackTrace();
        }
        return connection;
    }

    public void close(Connection connection){
        if(connection != null){
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println("Erro ao fechar a conexão");
                e.printStackTrace();
            }
        }
    }

}
