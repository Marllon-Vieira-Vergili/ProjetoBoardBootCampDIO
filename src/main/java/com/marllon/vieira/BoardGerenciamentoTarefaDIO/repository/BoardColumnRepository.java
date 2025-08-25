package com.marllon.vieira.BoardGerenciamentoTarefaDIO.repository;

import com.marllon.vieira.BoardGerenciamentoTarefaDIO.entity.BoardColumn;
import org.springframework.data.jpa.repository.JpaRepository;

/*
 * Interface CRUD JPA Repository da entidade BoardColumn
 * */
public interface BoardColumnRepository extends JpaRepository<BoardColumn,Long> {

}
