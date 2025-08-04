package board.dio.ui;

import java.util.Scanner;

import board.dio.persistence.entity.BoardEntity;

public class BoardMenu {
    private BoardEntity entity;
    private final Scanner scanner = new Scanner(System.in);

    public BoardMenu(BoardEntity entity) {
        this.entity = entity;
    }

    public void execute() {
        System.out.printf("Bem vindo ao board %s, selecione a operação desejada", entity.getId());
        var option = -1;
        while (option != 9) {
            System.out.println("1. Criar um card");
            System.out.println("2. Mover um card");
            System.out.println("3. Bloquear um card");
            System.out.println("4. Desbloquear um card");
            System.out.println("5. Cancelar um card");
            System.out.println("6. Ver Board");
            System.out.println("7. ver coluna com card");
            System.out.println("8. Ver Card");
            System.out.println("9. Voltar para o menu anterior");
            System.out.println("10. Sair");
            option = scanner.nextInt();
            switch (option) {
                case 1 -> createCard();
                case 2 -> moveCardToNextColumn();
                case 3 -> blockCard();
                case 4 -> unblockCard();
                case 5 -> cancelCards();
                case 6 -> showBoard();
                case 7 -> showColumn();
                case 8 -> showCard();
                case 9 -> System.out.println("Voltando para menu anterior");
                case 10 -> System.exit(0);
                default -> System.out.println("Escolha uma opção valida");
            }
        }
    }

    private Object createCard() {
        return entity;
    }

    private Object moveCardToNextColumn() {
        return entity;
    }

    private Object blockCard() {
        return entity;
    }

    private Object unblockCard() {
        return entity;
    }

    private Object cancelCards() {
        return entity;
    }

    private Object showBoard() {
        return entity;
    }

    private Object showColumn() {
        return entity;
    }

    private Object showCard() {
        return entity;
    }
}

