package com.marllon.vieira.BoardGerenciamentoTarefaDIO.repository;

import com.marllon.vieira.BoardGerenciamentoTarefaDIO.entity.BoardColumn;
import com.marllon.vieira.BoardGerenciamentoTarefaDIO.entity.BoardColumnKindEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/*
 * Interface CRUD JPA Repository da entidade BoardColumn
 * */
public interface BoardColumnRepository extends JpaRepository<BoardColumn,Long> {

    //Encontrar pelo id e seu tipo ENUM
    Optional<BoardColumn> findByBoardIdAndKind(Long boardId, BoardColumnKindEnum kind);

    //Encontrar na lista pela ordem da id do board
    List<BoardColumn> findByBoardIdOrder(Long boardId);
}
