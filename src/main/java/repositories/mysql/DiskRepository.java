package repositories.mysql;

import entities.Disk;
import repositories.connections.ConnectionDatabase;
import repositories.connections.ConnectionMySql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DiskRepository {

    public void save(Disk disk, ConnectionDatabase database) {
        String command = "INSERT INTO tb_disk (id, model) VALUES (?, ?);";

        Connection conn = database.getConnection();
        try (PreparedStatement st = conn.prepareStatement(command)) {
            st.setString(1, disk.getId());
            st.setString(2, disk.getModel());
            st.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            database.closeConnection(conn);
        }
    }
}
