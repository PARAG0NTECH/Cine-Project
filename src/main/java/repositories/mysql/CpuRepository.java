package repositories.mysql;

import entities.Cpu;
import repositories.connections.ConnectionDatabase;
import repositories.connections.ConnectionMySql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CpuRepository {

    public void save(Cpu cpu, ConnectionDatabase database) {
        String command = "INSERT INTO tb_cpu (id, name) VALUES (?, ?);";

        Connection conn = database.getConnection();
        try (PreparedStatement st = conn.prepareStatement(command)) {
            st.setString(1, cpu.getId());
            st.setString(2, cpu.getName());
            st.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            database.closeConnection(conn);
        }
    }
}
