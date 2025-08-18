package com.marllon.vieira.BoardGerenciamentoTarefaDIO.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "block")
public class Block {

    @Id
    @NotNull(message = "Campo id da tabela Block não pode ser nulo")
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "blocked_at")
    private OffsetDateTime blockedAt;

    @NotNull
    @Column(name = "block_reason")
    private String blockReason;

    @NotNull
    @Column(name = "unblocked_at")
    private OffsetDateTime unblockedAt;

    @NotNull
    @Column(name = "block_unblock_reason")
    private String blockUnblockReason;



    //para relacionar
    private Card card;
}
