package com.marllon.vieira.BoardGerenciamentoTarefaDIO.services;

import com.marllon.vieira.BoardGerenciamentoTarefaDIO.entity.*;
import com.marllon.vieira.BoardGerenciamentoTarefaDIO.exceptions.BoardNotFoundException;
import com.marllon.vieira.BoardGerenciamentoTarefaDIO.exceptions.CardBlockedException;
import com.marllon.vieira.BoardGerenciamentoTarefaDIO.exceptions.CardErrorException;
import com.marllon.vieira.BoardGerenciamentoTarefaDIO.exceptions.CardNotFoundException;
import com.marllon.vieira.BoardGerenciamentoTarefaDIO.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional //Fazer rollback caso não der certo
public class BoardService implements BoardServiceImpl{

    /*
    * Injeção das dependências
    * */
    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private BoardColumnRepository boardColumnRepository;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private BlockRepository blockRepository;

    @Autowired
    private CardMovementRepository cardMovementRepository;



    @Override
    public Board createBoard(String name, List<String> columnNames) {
        //Instanciando novo board
        Board board = new Board();
        board.setName(name);
        //Persistindo o objeto no banco
        board = boardRepository.save(board);

        // Criar colunas obrigatórias
        createColumn(board, columnNames.get(0), BoardColumnKindEnum.INITIAL, 1);

        // Criar colunas pendentes
        for (int i = 1; i < columnNames.size() - 2; i++) {
            createColumn(board, columnNames.get(i), BoardColumnKindEnum.PENDING, i + 1);
        }

        // Criar coluna final e de cancelamento
        createColumn(board, columnNames.get(columnNames.size() - 2), BoardColumnKindEnum.FINAL, columnNames.size() - 1);
        createColumn(board, columnNames.get(columnNames.size() - 1), BoardColumnKindEnum.CANCEL, columnNames.size());

        return board;
    }


    @Override
    public void moveCardToNextColumn(Long cardId) {

        //Verificar se localiza o card pela id
        Optional<Card> cardLocalizado = cardRepository.findById(cardId);
        if (!cardLocalizado.isPresent()) {
            throw new CardNotFoundException("Card não encontrado");
        }
        //Instanciando o card e verificando es ele não está bloqueado
        Card card = cardLocalizado.get();
        if (card.isBlocked()) {
            throw new CardBlockedException("Card está bloqueado e não pode ser movido");
        }

        //Se não tiver, obter a coluna do board
        BoardColumn currentColumn = card.getBoardColumn();
        List<BoardColumn> columns = getBoardColumns(currentColumn.getBoard().getId());

        // Encontrar próxima coluna
        BoardColumn nextColumn = null;
        for (int i = 0; i < columns.size() - 1; i++) {
            if (columns.get(i).getId().equals(currentColumn.getId())) {
                if (i < columns.size() - 2) { // Não incluir coluna de cancelamento na sequência
                    nextColumn = columns.get(i + 1);
                }
                break;
            }
        }

        if (nextColumn != null) {
            // Registrar movimento
            CardMovement movement = new CardMovement();
            movement.setCard(card);
            movement.setFromColumn(currentColumn);
            movement.setToColumn(nextColumn);
            movement.setMovedAt(OffsetDateTime.now());
            cardMovementRepository.save(movement);

            // Mover card
            card.setBoardColumn(nextColumn);
            cardRepository.save(card);
        } else {
            throw new CardErrorException("Card já está na coluna final");
        }
    }

    @Override
    public void createColumn(Board board, String name, BoardColumnKindEnum kind, int order) {
        BoardColumn column = new BoardColumn();
        column.setName(name);
        column.setKind(kind);
        column.setOrder(order);
        column.setBoard(board);
        boardColumnRepository.save(column);
    }

    @Override
    public Card createCard(Long columnId, String title, String description) {
        //Verificar se já existe card pela id da coluna do board
        Optional<BoardColumn> column = boardColumnRepository.findById(columnId);
        if (column.isPresent() && column.get().getKind() == BoardColumnKindEnum.INITIAL) {
            Card card = new Card();
            card.setTitle(title);
            card.setDescription(description);
            card.setCreatedAt(OffsetDateTime.now());
            card.setBoardColumn(column.get());
            card = cardRepository.save(card);

            // Registrar movimento inicial se caso for criado
            CardMovement movement = new CardMovement();
            movement.setCard(card);
            movement.setToColumn(column.get());
            movement.setMovedAt(OffsetDateTime.now());
            cardMovementRepository.save(movement);

            return card;
        }
        throw new CardErrorException("Card só pode ser criado na coluna inicial");
    }



    @Override
    public List<Board> getAllBoards() {

        if (boardRepository.findAll().isEmpty()){
            throw new BoardNotFoundException("Nenhuma board foi localizada");
        }
        return boardRepository.findAll();
    }

    @Override
    public Optional<Board> getBoardById(Long id) {
        return Optional.ofNullable(boardRepository.findById(id).orElseThrow(()
                -> new BoardNotFoundException("Nenhum board foi localizado com essa id")));
    }

    @Override
    public List<BoardColumn> getBoardColumns(Long boardId) {

        if (boardColumnRepository.findByBoardIdOrder(boardId).isEmpty()){
            throw new BoardNotFoundException("Não foi encontrado nenhuma coluna com a id informada da board");
        }
        return boardColumnRepository.findByBoardIdOrder(boardId);
    }

    @Override
    public List<Card> getCardsByColumn(Long columnId) {

        if (cardRepository.findByBoardColumnId(columnId).isEmpty()){
            throw new CardNotFoundException("Não foi localizado nenhum card nessa coluna");
        }
        return cardRepository.findByBoardColumnId(columnId);

    }

    @Override
    public void cancelCard(Long cardId) {
        Optional<Card> cardEncontrado = cardRepository.findById(cardId);
        if (!cardEncontrado.isPresent()) {
            throw new CardNotFoundException("Card não encontrado");
        }
        //Se encontrar, obter o objeto Card
        Card card = cardEncontrado.get();
        //Obter a coluna que o card se localiza
        BoardColumn currentColumn = card.getBoardColumn();

        //Verificar se o card localizado está com o valor finalizado
        if (currentColumn.getKind() == BoardColumnKindEnum.FINAL) {
            throw new CardErrorException("Card finalizado não pode ser cancelado");
        }

        // Encontrar coluna de cancelamento, caso passado nas condições acima
        Optional<BoardColumn> cancelColumn = boardColumnRepository
                .findByBoardIdAndKind(currentColumn.getBoard().getId(), BoardColumnKindEnum.CANCEL);

        //Verificar se o card está na coluna de cancelamento do board
        if (cancelColumn.isPresent()) {
            // Registrar movimento
            CardMovement movement = new CardMovement();
            movement.setCard(card);
            movement.setFromColumn(currentColumn);
            movement.setToColumn(cancelColumn.get());
            movement.setMovedAt(OffsetDateTime.now());
            cardMovementRepository.save(movement);

            // Mover card para cancelamento
            card.setBoardColumn(cancelColumn.get());
            cardRepository.save(card);
        }
    }

    @Override
    public void blockCard(Long cardId, String reason) {
        Optional<Card> cardEncontrado = cardRepository.findById(cardId);
        if (!cardEncontrado.isPresent()) {
            throw new CardNotFoundException("Card não encontrado");
        }

        //Se localizado o card, obter o objeto card
        Card card = cardEncontrado.get();
        //Verificar se já não está bloqueado
        if (card.isBlocked()) {
            throw new CardErrorException("Card já está bloqueado");
        }

        //Se não tiver bloqueado, bloquear e persistir as alterações
        Block block = new Block();
        block.setCard(card);
        block.setBlockReason(reason);
        block.setBlockedAt(OffsetDateTime.now());
        blockRepository.save(block);

        card.setBlocked(true);
        cardRepository.save(card);
    }

    @Override
    public void unblockCard(Long cardId, String reason) {
        Optional<Card> cardEncontrado = cardRepository.findById(cardId);
            //Se não estiver presente, já lançar a exceção
        if (!cardEncontrado.isPresent()){
            throw new CardNotFoundException("Card não está presente");
        }
        //Senão, obter o card
        Card card = cardEncontrado.get();
        if (!card.isBlocked()){
            throw new CardErrorException(("Card não está bloqueado"));
        }
        //Encontrar o último bloqueio do card
        Optional<Block> blockIsActive = card.getBlock().stream().filter(block1 -> block1.getUnblockedAt() == null)
                .findFirst();

        //Se detectado que o bloqueio do card está ativo, alterar os valores
        //Para desbloquear, e salvar no respositório as alterações
        if (blockIsActive.isPresent()){
            Block block = blockIsActive.get();
            block.setBlockUnblockReason(reason);
            block.setUnblockedAt(OffsetDateTime.now());
            blockRepository.save(block);
        }

        //Alterar o valor booleano para falso
        card.setBlocked(false);
        //Persistir as alterações e atualizar na tabela card
        cardRepository.save(card);
    }

}
