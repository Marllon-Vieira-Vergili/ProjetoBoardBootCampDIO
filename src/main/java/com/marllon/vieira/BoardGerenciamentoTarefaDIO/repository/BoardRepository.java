package com.marllon.vieira.BoardGerenciamentoTarefaDIO.repository;

import com.marllon.vieira.BoardGerenciamentoTarefaDIO.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;


/*
 * Interface CRUD JPA Repository da entidade Board
 * */
public interface BoardRepository extends JpaRepository<Board,Long> {
}
