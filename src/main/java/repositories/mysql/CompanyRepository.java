package repositories.mysql;

import entities.Company;
import repositories.connections.ConnectionMySql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CompanyRepository {

    private ConnectionMySql connectionMySql;

    public CompanyRepository(ConnectionMySql connectionMySql){
        this.connectionMySql = connectionMySql;
    }

    public Company findById(Integer id) {
        String command = """
            SELECT * FROM tb_company WHERE id = ?
        """;

        Connection conn = connectionMySql.open();
        try (PreparedStatement st = conn.prepareStatement(command)) {
            st.setInt(1, id.intValue());
            ResultSet resultSet = st.getResultSet();
            if(resultSet.first()){
                return new Company(id);
            }
            st.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            connectionMySql.close(conn);
        }
        return null;
    }
}
