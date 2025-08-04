package board.dio.persistence.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
// import java.util.Optional;

import com.mysql.cj.jdbc.StatementImpl;

import board.dio.persistence.entity.BoardColumn;
// import board.dio.persistence.entity.BoardEntity;

import static board.dio.persistence.entity.BoardColumnKindEnum.findByName;

public class BoardColumnDAO {

    private Connection connection = null;

    public BoardColumnDAO(Connection connection) {
        this.connection = connection;
    }

    public BoardColumn insert(final BoardColumn entity) throws SQLException {
        var sql = "INSERT INTO boards (name, `order`, ind, border_id) VALUES (?, ?, ?, ?)";
        try (var statments = connection.prepareStatement(sql)) {
            var i = 1;
            statments.setString(i++, entity.getName());
            statments.setInt(i++, entity.getOrder());
            statments.setString(i++, entity.getKind().name());
            statments.setLong(i++, entity.getBoard().getId());
            statments.executeUpdate();
            if (statments instanceof StatementImpl impl) {
                entity.setId(impl.getLastInsertID());
            }
            return entity;
        }
    }

    public List<BoardColumn> findById(Long id) throws SQLException {
        List<BoardColumn> entities = new ArrayList<>();
        var sql = "SELECT id, name, `order`, kind FROM boards_columns WHERE ID = ? ODER BY `order`";
        try (var statments = connection.prepareStatement(sql)) {
            statments.setLong(1, id);
            statments.executeQuery();
            var resultSet = statments.getResultSet();
            while (resultSet.next()) {
                var entity = new BoardColumn();
                entity.setId(resultSet.getLong("id"));
                entity.setName(resultSet.getString("name"));
                entity.setOrder(resultSet.getInt("order"));
                entity.setKind(findByName(resultSet.getString("kind")));
                entities.add(entity);
            }
            return entities;
        }
        // return null;
    }
}
