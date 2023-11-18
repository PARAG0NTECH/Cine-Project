package repositories.mysql;

import entities.Network;
import repositories.connections.ConnectionDatabase;
import repositories.connections.ConnectionMySql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class NetworkRepository {

    public void save(Network network, ConnectionDatabase database) {
        String command = "INSERT INTO tb_network (id_computer, name, mac_address, packages_received, packages_sent) VALUES (?, ?, ?, ?, ?);";

        Connection conn = database.getConnection();
        try (PreparedStatement st = conn.prepareStatement(command)) {
            st.setInt(1, network.getComputer().getId());
            st.setString(2, network.getName());
            st.setString(3, network.getMacAddress());
            st.setInt(4, network.getPackagesReceived());
            st.setDouble(5, network.getPackagesSent());
            st.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            database.closeConnection(conn);
        }
    }
}
