package board.dio.ui;

import static board.dio.persistence.config.ConnectionConfig.getConnection;
import static board.dio.persistence.entity.BoardColumnKindEnum.CANCEL;
import static board.dio.persistence.entity.BoardColumnKindEnum.FINAL;
import static board.dio.persistence.entity.BoardColumnKindEnum.INITIAL;
import static board.dio.persistence.entity.BoardColumnKindEnum.PENDING;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import board.dio.persistence.entity.BoardColumn;
import board.dio.persistence.entity.BoardColumnKindEnum;
import board.dio.persistence.entity.BoardEntity;
import board.dio.service.BoardQueryService;
import board.dio.service.BoardService;

public class MainMenu {
    private final Scanner scanner = new Scanner(System.in);

    public void execute() throws SQLException {
        System.out.println("Bem vindo ao gerenciador de Boards, escolha a opção desejada!");
        var option = -1;
        while (true) {
            System.out.println("1. Criar um novo board!");
            System.out.println("2. Selecionar um novo board");
            System.out.println("3. Excluir um board");
            System.out.println("4. Sair");
            option = scanner.nextInt();
            switch (option) {
                case 1:
                    createBoard();
                    break;

                case 2:
                    selectBoard();
                    break;

                case 3:
                    deleteBoard();
                    break;

                case 4:
                    System.exit(0);
                    break;

                default:
                    System.out.println("Escolha uma opção valida");
                    break;
            }
        }
    }

    private void createBoard() throws SQLException {
        var entity = new BoardEntity();
        System.out.println("Informe o nome do seu Board");
        var nameBoard = scanner.next();
        entity.setName(nameBoard);

        System.out.println("Seu board terá colunas além das 3 padrões? se sim informe quantos, se não digite '0'");
        var additionalColumns = scanner.nextInt();

        List<BoardColumn> columns = new ArrayList<>();

        System.out.println("Informe o nome da coluna inicial do board");
        var initialColumnName = scanner.next();
        var initialColumn = createColumns(initialColumnName, INITIAL, 0);
        columns.add(initialColumn);

        for (int i = 0; i < additionalColumns; i++) {
            System.out.println("Informe o nome da coluna de tarefa pendente do board");
            var pendingColumnName = scanner.next();
            var pendingColumn = createColumns(pendingColumnName, PENDING, i + 1);
            columns.add(pendingColumn);
        }

        System.out.println("Informe o nome da coluna de final");
        var finalColumnName = scanner.next();
        var finalColumn = createColumns(finalColumnName, FINAL, additionalColumns + 1);
        columns.add(finalColumn);

        System.out.println("Informe o nome da coluna de cancelamento do board");
        var cancelColumnName = scanner.next();
        var cancelColumn = createColumns(cancelColumnName, CANCEL, additionalColumns + 2);
        columns.add(cancelColumn);

        entity.setBoardColumn(columns);

        try (var connection = getConnection()) {
            var service = new BoardService(connection);
            service.insert(entity);
        }

    }

    private void selectBoard() throws SQLException {
        System.out.println("Informe o ID do board que deseja selecionar");
        var id = scanner.nextLong();
        try (var connection = getConnection()) {
            var queryService = new BoardQueryService(connection);
            var optional = queryService.findById(id);
            if (optional.isPresent()) {
                var boardMenu = new BoardMenu(optional.get());
                boardMenu.execute();
            } else {
                System.out.println("Não foi encontrado um board com o id " + id);
            }

        }

    }

    private void deleteBoard() throws SQLException {
        System.out.println("Informe o ID do board");
        var id = scanner.nextLong();
        try (var connection = getConnection()) {
            var service = new BoardService(connection);
            if (service.delete(id)) {
                System.out.println("O board foi excluido");
            } else {
                System.out.println("Não foi possível excluir o board com id " + id);
            }
        }
    }

    private BoardColumn createColumns(final String name, final BoardColumnKindEnum kind, final int order) {
        var boardColumn = new BoardColumn();
        boardColumn.setName(name);
        boardColumn.setKind(kind);
        boardColumn.setOrder(order);
        return boardColumn;
    }
}
