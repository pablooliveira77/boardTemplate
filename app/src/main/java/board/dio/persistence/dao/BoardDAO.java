package board.dio.persistence.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

import com.mysql.cj.jdbc.StatementImpl;

import board.dio.persistence.entity.BoardEntity;

public class BoardDAO {

    private Connection connection = null;
    
    public BoardDAO(Connection connection) {
        this.connection = connection;
    }

    public BoardEntity insert(final BoardEntity entity) throws SQLException {
        var sql = "INSERT INTO boards (name) VALUES (?)";
        try (var statments = connection.prepareStatement(sql)) {
            statments.setString(1, entity.getName());
            statments.executeUpdate();
            if (statments instanceof StatementImpl impl) {
                entity.setId(impl.getLastInsertID());
            }
            return entity;
        }
    }

    public void delete(final Long id) throws SQLException {
        var sql = "DELETE FROM boards WHERE ID = ?";
        try (var statments = connection.prepareStatement(sql)) {
            statments.setLong(1, id);
            statments.executeUpdate();
        }
    }

    public Optional<BoardEntity> findById(final Long id) throws SQLException {
        var sql = "SELECT id, name FROM boards WHERE ID = ?";
        try (var statments = connection.prepareStatement(sql)) {
            statments.setLong(1, id);
            statments.executeQuery();
            var resultSet = statments.getResultSet();
            if (resultSet.next()) {
                var entity = new BoardEntity();
                entity.setId(resultSet.getLong("id"));
                entity.setName(resultSet.getString("name"));
                return Optional.of(entity);
            }

            return Optional.empty();
        }
    }

    public boolean exists(final Long id) throws SQLException {
        var sql = "SELECT 1 FROM boards WHERE ID = ?";
        try (var statments = connection.prepareStatement(sql)) {
            statments.setLong(1, id);
            statments.executeQuery();
            return statments.getResultSet().next();
        }
    }
}
