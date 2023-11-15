package repositories.connections;

import java.sql.Connection;

public interface ConnectionDatabase {
    Connection getConnection();

    void closeConnection(Connection conn);
}
