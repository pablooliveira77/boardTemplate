package board.dio.persistence.entity;

import java.util.ArrayList;
import java.util.List;

public class BoardEntity {
    private Long id;
    private String name;

    public List<BoardColumn> getBoardColumn() {
        return boardColumn;
    }

    public void setBoardColumn(List<BoardColumn> boardColumn) {
        this.boardColumn = boardColumn;
    }

    private List<BoardColumn> boardColumn = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
