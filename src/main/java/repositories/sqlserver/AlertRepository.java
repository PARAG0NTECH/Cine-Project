package repositories.sqlserver;

import entities.Alert;
import entities.Company;
import repositories.connections.ConnectionMySql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AlertRepository {

    private ConnectionMySql connectionMySql;

    public AlertRepository(ConnectionMySql connectionMySql){
        this.connectionMySql = connectionMySql;
    }

    public Alert findByCompany(Company company) {
        String command = """
            SELECT * FROM tb_alerts WHERE tb_companies_id = ?
        """;

        Connection conn = connectionMySql.open();
        try (PreparedStatement st = conn.prepareStatement(command)) {
            st.setInt(1, company.getId().intValue());
            st.execute();
            ResultSet resultSet = st.getResultSet();
            if(resultSet.next()){
                int id = resultSet.getInt("id");
                int companyId = resultSet.getInt("tb_companies_id");
                double percentualCpu = resultSet.getDouble("percentual_cpu");
                double percentualDisk = resultSet.getDouble("percentual_disk");
                double percentualRam = resultSet.getDouble("percentual_ram");
                return new Alert(id, new Company(companyId), percentualCpu, percentualDisk, percentualRam);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            connectionMySql.close(conn);
        }
        return null;
    }
}
