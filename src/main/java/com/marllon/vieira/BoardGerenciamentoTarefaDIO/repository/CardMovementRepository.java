package com.marllon.vieira.BoardGerenciamentoTarefaDIO.repository;

import com.marllon.vieira.BoardGerenciamentoTarefaDIO.entity.CardMovement;
import org.springframework.data.jpa.repository.JpaRepository;

/*
 * Interface CRUD JPA Repository da entidade CardMovement
 * */
public interface CardMovementRepository extends JpaRepository<CardMovement,Long> {
}
