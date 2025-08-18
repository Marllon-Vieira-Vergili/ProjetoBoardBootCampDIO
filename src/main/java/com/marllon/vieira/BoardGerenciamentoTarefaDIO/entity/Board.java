package com.marllon.vieira.BoardGerenciamentoTarefaDIO.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "board")
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(message = "Campo id do Board não pode ser nulo")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    @NotNull(message = "Campo name do Board não pode ser nulo")
    private String name;


    //Para Relacionar
    private BoardColumn boardColumn;
}
