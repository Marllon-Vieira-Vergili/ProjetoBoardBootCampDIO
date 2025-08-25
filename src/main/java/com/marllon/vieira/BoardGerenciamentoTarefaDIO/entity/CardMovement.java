package com.marllon.vieira.BoardGerenciamentoTarefaDIO.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.OffsetDateTime;


/***
 *Entidade para detecção dos movimentos dos cards
 *
 * Usando Lombok para gerar os construtores com e sem argumentos.
 *
 * Relacionamentos:
 *
 * @ManyToOne com a boardColumn
 * Muitos movimentos cards para um coluna do board
 * relacionamento da coluna para outra coluna
 *
 */




@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "card_movements")
public class CardMovement {

    @Id
    @NotNull(message = "Campo id da tabela Block não pode ser nulo")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "moved_at")
    private OffsetDateTime movedAt;


    @ManyToOne
    @JoinColumn(name = "card_id")
    private Card card;

    @ManyToOne
    @JoinColumn(name = "from_column_id")
    private BoardColumn fromColumn;

    @ManyToOne
    @JoinColumn(name = "to_column_id")
    private BoardColumn toColumn;

}
