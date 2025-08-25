package com.marllon.vieira.BoardGerenciamentoTarefaDIO.controller;

import com.marllon.vieira.BoardGerenciamentoTarefaDIO.entity.Board;
import com.marllon.vieira.BoardGerenciamentoTarefaDIO.entity.BoardColumn;
import com.marllon.vieira.BoardGerenciamentoTarefaDIO.entity.BoardColumnKindEnum;
import com.marllon.vieira.BoardGerenciamentoTarefaDIO.entity.Card;
import com.marllon.vieira.BoardGerenciamentoTarefaDIO.services.BoardServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;


/**
 * Classe principal para chamar todos os métodos do services no static void main
 * principal do projeto. Aqui, é um componente usando o commandLineRunner
 * para executar o menu no console de uma forma mais interativa.
 *
 * Injetando a interface do boardService
 * e instanciando um scanner para entrada de dados.
 *
 *
 * Todos os métodos retornam void, e chamam os métodos da classe service correspondente
 * */
@Component
public class BoardController implements CommandLineRunner {


    @Autowired
    private BoardServiceImpl boardService;

    private Scanner sc = new Scanner(System.in);


    /**
     * Método principal, exibirá em loop até o usuário escolher sair
     * */
    @Override
    public void run(String... args) {
        while (true) {
            //Primeiro método a ser chamado.
            showMenu();
            int option = getIntInput();

            switch (option) {
                case 1:
                    createNewBoard();
                    break;
                case 2:
                    selectBoard();
                    break;
                case 3:
                    deleteBoards();
                    break;
                case 4:
                    System.out.println("Saindo");
                    return;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    //Primeiro método a ser chamado. Exibindo o menu
    private void showMenu() {
        System.out.println("GERENCIAMENTO DE TAREFAS");
        System.out.println("1. Criar nova tarefa");
        System.out.println("2. Selecionar tarefa criada");
        System.out.println("3. Excluir tarefa existente");
        System.out.println("4. Sair");
        System.out.print("Escolha uma opção: ");
    }


    /**
     * Método será executado de criar nova tarefa quando for chamado, com suas colunas
     * */
    private void createNewBoard() {
        System.out.print("Nome da tarefa: ");
        String boardName = sc.nextLine();

        System.out.print("Quantas colunas terá? (além das obrigatórias): ");
        int pendingColumns = getIntInput();
        //sc.nextLine();

        List<String> columnNames = new ArrayList<>();

        System.out.print("Nome da coluna inicial: ");
        columnNames.add(sc.nextLine());

        for (int i = 0; i < pendingColumns; i++) {
            System.out.print("Nome da coluna pendente " + (i + 1) + ": ");
            columnNames.add(sc.nextLine());
        }

        System.out.print("Nome da coluna final: ");
        columnNames.add(sc.nextLine());

        System.out.print("Nome da coluna de cancelamento: ");
        columnNames.add(sc.nextLine());

        try {
            Board board = boardService.createBoard(boardName, columnNames);
            System.out.println("Tarefa criada com sucesso! ID: " + board.getId());
        } catch (Exception e) {
            System.out.println("Erro ao criar tarefa: " + e.getMessage());
        }
    }

    /**
     * Método que irá selecionar uma tarefa já criada quando for chamado
     * e pedindo para passar a ID para localizar o método correto
     * */
    private void selectBoard() {
        List<Board> boards = boardService.getAllBoards();
        if (boards.isEmpty()) {
            System.out.println("Nenhuma tarefa encontrada.");
            return;
        }

        System.out.println("TAREFAS DISPONÍVEIS");
        for (Board board : boards) {
            System.out.println(board.getId() + ". " + board.getName());
        }

        System.out.print("Selecione uma tarefa (ID): ");
        Long boardId = (long) getIntInput();

        Optional<Board> board = boardService.getBoardById(boardId);
        if (board.isPresent()) {
            manageBoardMenu(board.get());
        } else {
            System.out.println("Tarefa não encontrada.");
        }
    }

    /**
     * Método que chamará o submenu, para ações de uma determinada tarefa específica.
     *
     * Ou seja, ele chamará os submétodos para gerenciamento do board, conforme for selecionado em cada caso separado
     * */
    private void manageBoardMenu(Board board) {
        while (true) {
            displayBoard(board);
            showBoardMenu();
            int option = getIntInput();

            switch (option) {
                case 1:
                    createCard(board);
                    break;
                case 2:
                    moveCard(board);
                    break;
                case 3:
                    cancelCard(board);
                    break;
                case 4:
                    blockCard(board);
                    break;
                case 5:
                    unblockCard(board);
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    //Exibirá a tarefa ao usuário, os cards inclusas nela, e as colunas
    private void displayBoard(Board board) {
        System.out.println("Tarefa: " + board.getName());
        List<BoardColumn> columns = boardService.getBoardColumns(board.getId());

        for (BoardColumn column : columns) {
            System.out.println( column.getName() + " - " + column.getKind() + "]");
            List<Card> cards = boardService.getCardsByColumn(column.getId());

            if (cards.isEmpty()) {
                System.out.println("  (vazio)");
            } else {
                for (Card card : cards) {
                    String status = "";
                    if (card.isBlocked()) {
                        status = " [BLOQUEADO]";
                    }
                    System.out.println("  " + card.getId() + ". " + card.getTitle() + status);
                    System.out.println("     " + card.getDescription());
                }
            }
        }
    }

    /**
     * Método para mostrar o submenu das boards(tarefas).
     * para gerenciar as opções nos cards dentro das boards

     * */
    private void showBoardMenu() {
        System.out.println("AÇÕES DA TAREFA");
        System.out.println("1. Criar card");
        System.out.println("2. Mover card para próxima coluna");
        System.out.println("3. Cancelar card");
        System.out.println("4. Bloquear card");
        System.out.println("5. Desbloquear card");
        System.out.println("6. Voltar");
        System.out.print("Escolha uma opção: ");
    }

    //Criar um novo card dentro da board, na primeira coluna
    private void createCard(Board board) {
        System.out.print("Título do card: ");
        sc.nextLine(); // Consumir quebra de linha
        String title = sc.nextLine();

        System.out.print("Descrição do card: ");
        String description = sc.nextLine();

        try {
            List<BoardColumn> columns = boardService.getBoardColumns(board.getId());
            BoardColumn initialColumn = columns.stream()
                    .filter(c -> c.getKind() == BoardColumnKindEnum.INITIAL)
                    .findFirst()
                    .orElseThrow();

            Card card = boardService.createCard(initialColumn.getId(), title, description);
            System.out.println("Card criado com sucesso! ID: " + card.getId());
        } catch (Exception e) {
            System.out.println("Erro ao criar card: " + e.getMessage());
        }
    }

    //Método par mover um card para outra coluna da tarefa
    private void moveCard(Board board) {
        System.out.print("ID do card para mover: ");
        Long cardId = (long) getIntInput();

        try {
            boardService.moveCardToNextColumn(cardId);
            System.out.println("Card movido com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao mover card: " + e.getMessage());
        }
    }

    //Método para cancelar um card, e mover para a coluna de cards cancelados
    private void cancelCard(Board board) {
        System.out.print("ID do card para cancelar: ");
        Long cardId = (long) getIntInput();

        try {
            boardService.cancelCard(cardId);
            System.out.println("Card cancelado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao cancelar card: " + e.getMessage());
        }
    }

    //Método para bloquear um card e registrar o motivo do bloqueio
    private void blockCard(Board board) {
        System.out.print("ID do card para bloquear: ");
        Long cardId = (long) getIntInput();
        sc.nextLine();

        System.out.print("Motivo do bloqueio: ");
        String reason = sc.nextLine();

        try {
            boardService.blockCard(cardId, reason);
            System.out.println("Card bloqueado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao bloquear card: " + e.getMessage());
        }
    }

    //Método para desbloquear um card e registrar o motivo do bloqueio
    private void unblockCard(Board board) {
        System.out.print("ID do card para desbloquear: ");
        Long cardId = (long) getIntInput();
        sc.nextLine(); // Consumir quebra de linha

        System.out.print("Motivo do desbloqueio: ");
        String reason = sc.nextLine();

        try {
            boardService.unblockCard(cardId, reason);
            System.out.println("Card desbloqueado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao desbloquear card: " + e.getMessage());
        }
    }

    /*
    * Chamando o método de deletar Tarefa pela id
    * */
    private void deleteBoards() {
        List<Board> boards = boardService.getAllBoards();
        if (boards.isEmpty()) {
            System.out.println("Nenhuma tarefa encontrada.");
            return;
        }

        System.out.println("TAREFAS PARA EXCLUIR");
        for (Board board : boards) {
            System.out.println(board.getId() + ". " + board.getName());
        }

        System.out.print("ID do board para excluir: ");
        Long boardId = (long) getIntInput();

        try {
            boardService.deleteBoard(boardId);
            System.out.println("Tarefa excluída com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao excluir tarefa: " + e.getMessage());
        }
    }

    /**
     * Método para ler o valor input de entrada dos dados no método RUn do CommandLineRunner
     */

    private int getIntInput() {
        while (true) {
            String input = sc.nextLine(); // lê a linha inteira
            try {
                return Integer.parseInt(input.trim()); // converte para int
            } catch (NumberFormatException e) {
                System.out.print("Por favor, digite um número válido: ");
            }
        }
    }
}
