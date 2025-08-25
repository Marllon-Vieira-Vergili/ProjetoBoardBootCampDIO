package com.marllon.vieira.BoardGerenciamentoTarefaDIO.repository;

import com.marllon.vieira.BoardGerenciamentoTarefaDIO.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/*
 * Interface CRUD JPA Repository da entidade Card
 * */
public interface CardRepository extends JpaRepository<Card,Long> {

    //Encontrar a lista da entidade Card pela id da coluna board
    List<Card> findByBoardColumnId(Long columnId);
}
