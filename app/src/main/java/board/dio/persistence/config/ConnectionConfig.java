package board.dio.persistence.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class ConnectionConfig {

    // Construtor privado para impedir instância
    private ConnectionConfig() {
        // Nada aqui
    }

    public static Connection getConnection() throws SQLException {
        var url = "jdbc:mysql://localhost/meubanco";
        var user = "user";
        var password = "123456";

        var connection = DriverManager.getConnection(url, user, password);
        connection.setAutoCommit(false);
        return connection;
    }
}
