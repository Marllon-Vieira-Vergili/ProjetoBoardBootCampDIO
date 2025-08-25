package com.marllon.vieira.BoardGerenciamentoTarefaDIO.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "board")
public class Board {

    @Id
    @Setter(AccessLevel.NONE) //Não fazer setter no campo ID

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(message = "Campo id do Board não pode ser nulo")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    @NotNull(message = "Campo name do Board não pode ser nulo")
    private String name;

    /**
     * Relacionando uma Board, com uma lista
     * de BoardColumn
     *
     * Cascade com todos os tipos de requisições, chamada com fetch atrasada(preguiçosa)
     * */
    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<BoardColumn> boardColumn;
}
