package com.marllon.vieira.BoardGerenciamentoTarefaDIO.repository;

import com.marllon.vieira.BoardGerenciamentoTarefaDIO.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

/*
 * Interface CRUD JPA Repository da entidade Card
 * */
public interface CardRepository extends JpaRepository<Card,Long> {
}
