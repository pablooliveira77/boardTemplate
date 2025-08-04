package board.dio.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

import board.dio.persistence.dao.BoardColumnDAO;
import board.dio.persistence.dao.BoardDAO;
import board.dio.persistence.entity.BoardEntity;

public class BoardQueryService {
    private Connection connection;

    public BoardQueryService(Connection connection) {
        this.connection = connection;
    }

    public Optional<BoardEntity> findById(final Long id) throws SQLException {
        var dao = new BoardDAO(connection);
        var boardColumnDao = new BoardColumnDAO(connection);
        var optional = dao.findById(id);
        if (optional.isPresent()) {
            var entity = optional.get();
            entity.setBoardColumn(boardColumnDao.findById(entity.getId()));
            return Optional.of(entity);
        }

        return Optional.empty();
    }
}
