package com.marllon.vieira.BoardGerenciamentoTarefaDIO.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "card")
public class Card {

    @Id
    @Setter(AccessLevel.NONE) //Não deixar fazer set na id (lombok)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @NotNull(message = "O campo id da tabela card não pode ser nulo")
    private Long id;

    @Column(name = "title")
    @NotNull(message = "O campo title da tabela card não pode ser nulo")
    @NotBlank
    private String title;

    @Column(name = "description")
    @NotNull(message = "O campo description da tabela card não pode ser nulo")
    @NotBlank(message = "O campo description da tabela card não pode ficar em branco")
    private String description;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @Column(name = "is_blocked")
    private boolean blocked = false;

    //Muitas cartas para uma coluna do board
    @ManyToOne
    @JoinColumn(name = "board_column_id")
    private BoardColumn boardColumn;

    //Mapeamento um card para muitos blocks
    @OneToMany(mappedBy = "card", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Block> block;

    //Mapeamento um card para muitos movimentos de cards
    @OneToMany(mappedBy = "card", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CardMovement> cardMovements;


}
