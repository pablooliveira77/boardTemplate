package board.dio.service;

import java.sql.Connection;
import java.sql.SQLException;

import board.dio.persistence.dao.BoardColumnDAO;
import board.dio.persistence.dao.BoardDAO;
import board.dio.persistence.entity.BoardEntity;

public class BoardService {
    private Connection connection;

    public BoardService(Connection connection) {
        this.connection = connection;
    }

    public BoardEntity insert(final BoardEntity entity) throws SQLException {
        var dao = new BoardDAO(connection);
        var boardColumnDao = new BoardColumnDAO(connection);

        try {
            dao.insert(entity);
            var columns = entity.getBoardColumn().stream().map(c -> {
                c.setBoard(entity);
                return c;
            }).toList();
            for (var column : columns) {
                boardColumnDao.insert(column);
            }
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        }

        return entity;
    }

    public boolean delete(final Long id) throws SQLException {
        var dao = new BoardDAO(connection);

        try {
            if (!dao.exists(id)) {
                return false;

            }
            dao.delete(id);
            connection.commit();
            return true;
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }
}
