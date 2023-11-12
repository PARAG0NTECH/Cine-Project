package repositories.mysql;

import entities.Computer;
import repositories.connections.ConnectionMySql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ComputerRepository {

    private ConnectionMySql connectionMySql;

    public ComputerRepository(ConnectionMySql connectionMySql){
        this.connectionMySql = connectionMySql;
    }

    public void save(Computer computer) {
        String command = """
            INSERT INTO tb_computers (hostname, maker, system_info, id_cpu, id_disk, tb_companies_id) VALUES (?, ?, ?, ?, ?, ?);
        """;

        Connection conn = connectionMySql.open();
        try (PreparedStatement st = conn.prepareStatement(command)) {
            st.setString(1, computer.getHostname());
            st.setString(2, computer.getMaker());
            st.setString(3, computer.getSystemInfo());
            st.setString(4, computer.getCpu().getId());
            st.setString(5, computer.getDisk().getId());
            st.setInt(6, computer.getCompany().getId());
            st.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            connectionMySql.close(conn);
        }
    }

    public Computer findByCpuId(String cpuId) {
        String command = "SELECT * FROM tb_computers WHERE id_cpu = ?";

        Connection conn = connectionMySql.open();

        try (PreparedStatement st = conn.prepareStatement(command)) {
            st.setString(1, cpuId);
            ResultSet resultSet = st.executeQuery();

            if (resultSet.next()) {
                return new Computer(resultSet.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            connectionMySql.close(conn);
        }

        return null;
    }

    public int countComputers(){
        String query = "SELECT COUNT(*) FROM tb_computers";

        Connection conn = connectionMySql.open();

        try (PreparedStatement st = conn.prepareStatement(query)) {

            ResultSet resultSet = st.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }


}
