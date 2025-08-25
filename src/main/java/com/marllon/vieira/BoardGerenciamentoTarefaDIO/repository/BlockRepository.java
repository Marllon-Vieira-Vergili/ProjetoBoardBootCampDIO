package com.marllon.vieira.BoardGerenciamentoTarefaDIO.repository;

import com.marllon.vieira.BoardGerenciamentoTarefaDIO.entity.Block;
import org.springframework.data.jpa.repository.JpaRepository;

/*
 * Interface CRUD JPA Repository da entidade Block
 * */
public interface BlockRepository extends JpaRepository<Block,Long> {
}
