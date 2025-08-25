package com.marllon.vieira.BoardGerenciamentoTarefaDIO.services;

import com.marllon.vieira.BoardGerenciamentoTarefaDIO.entity.Board;
import com.marllon.vieira.BoardGerenciamentoTarefaDIO.entity.BoardColumn;
import com.marllon.vieira.BoardGerenciamentoTarefaDIO.entity.BoardColumnKindEnum;
import com.marllon.vieira.BoardGerenciamentoTarefaDIO.entity.Card;
import com.marllon.vieira.BoardGerenciamentoTarefaDIO.exceptions.BoardNotFoundException;
import com.marllon.vieira.BoardGerenciamentoTarefaDIO.exceptions.CardBlockedException;
import com.marllon.vieira.BoardGerenciamentoTarefaDIO.exceptions.CardErrorException;
import com.marllon.vieira.BoardGerenciamentoTarefaDIO.exceptions.CardNotFoundException;

import java.util.List;
import java.util.Optional;

/***
 * Interfface dos métodos CRUD do boardService
 *
 */

public interface BoardServiceImpl {




    /**
     * Método retornará um objeto da entidade Board criado, passando como parâmetro
     * @param name, columnNames
     *
     *
     * */
    public Board createBoard(String name, List<String> columnNames);


    /**
     * Método para criar uma coluna, esperando como argumento passado o board, nome, tipo de coluna e a ordem dela
     * @param board, name, kind, order
     * */
    public void createColumn(Board board, String name, BoardColumnKindEnum kind, int order);



    /**
     * Método para criar um card, esperando como argumento passado o a id da coluna a ser criado ele, seu título e sua descrição
     *
     * @param columnId, title, description
     *
     * @throw CardException se der erro ao criar o card no início da coluna.
     * */
    public Card createCard(Long columnId, String title, String description);




    /**
     * Método só executará, sem retornar nada, movendo o card para próxima coluna, esperando como.
     * @param cardId
     *
     * @throws CardNotFoundException se o card não for localizado pela ID
     * @throws CardBlockedException se o card estiver sido bloqueado e não puder ser movido.
     * @throws CardErrorException se o card ja estiver sido movido para a coluna final
     * */
    public void moveCardToNextColumn(Long cardId);


    /**
     * Método para retornar a lista de Boards(quadros) encontrados.
     * @throws BoardNotFoundException se nenhuma board for encontrada.
     * */
    public List<Board> getAllBoards();

    //Obtendo boards pela Id
    //Jogar exceção se não localizada pela id
    public Optional<Board> getBoardById(Long id);


    /**
     * Método que retorna uma lista de colunas da entidade BoardColumn retornando todos os as colunas encontradas no board pela
     * @param boardId
     *
     *
     * @throws BoardNotFoundException se não for localizado nenhuma coluna na board.
     * */
    public List<BoardColumn> getBoardColumns(Long boardId);


    /**
     * Método que retorna uma lista de Objetos da entidade Card retornando todos os cards pela id da coluna informada
     * @param columnId
     *
     * @throws CardNotFoundException se não for localizado nenhum card nessa coluna
     * */
    public List<Card> getCardsByColumn(Long columnId);


    /**
     * Método para cancelar um card, só executando, sem retorno , passando como parâmetro para cancelar, a id
     * @param cardId
     *
     * @throws CardNotFoundException se card não for localizado pela id
     * @throws CardErrorException se for detectado que o card foi atribuído o valor no enum FINAL, não pode ser cancelado
     * */
    public void cancelCard(Long cardId);



    /**
     * Método para bloquear um card, só executando, sem retorno , passando como parâmetro para cancelar, a id do
     * card e a razão do bloqueio
     * @param cardId, reason
     *
     * @throws CardNotFoundException se card não for localizado pela id
     * @throws CardErrorException se for detectado que o card ja foi detectado que está bloqueado
     * */
    public void blockCard(Long cardId, String reason);


    /**
     * Método para desbloquear um card, só executando, sem retorno , passando como parâmetro para cancelar, a id e a
     * razão do desbloqueio
     * @param cardId, reason
     *
     * @throws CardNotFoundException se card não for localizado pela id
     * @throws CardErrorException se for detectado que o card ja está desbloqueado e não precisa do desbloqueio
     * */
    public void unblockCard(Long cardId, String reason);

}
