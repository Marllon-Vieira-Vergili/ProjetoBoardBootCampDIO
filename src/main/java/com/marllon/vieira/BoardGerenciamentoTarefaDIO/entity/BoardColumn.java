package com.marllon.vieira.BoardGerenciamentoTarefaDIO.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "board_column")
public class BoardColumn {

    @Id
    @Setter(AccessLevel.NONE) //não deixar fazer set na id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @NotNull(message = "Campo id da tabela BoardColumn não pode ser nulo!")
    private Long id;

    @Column(name = "name")
    @NotBlank(message = "Campo name da tabela board_column não pode ficar em branco")

    @NotNull(message = "Campo name da tabela board_column não pode ser nulo!!")
    private String name;

    @Column(name = "kind")
    @NotBlank(message = "Campo kind da tabela board_column não pode ficar em branco")
    @NotNull(message = "Campo kind da tabela board_column não pode ser nulo!")
    private String kind;

    @Column(name = "order_enum")
    @NotNull(message = "Campo order da tabela board_column não pode ser nulo!")
    @NotBlank(message = "Campo order da tabela board_column não pode ficar em branco")
    @Enumerated(EnumType.STRING)
    private BoardColumnKindEnum order;


    //Para relacionar
    @OneToOne
    private Board board;

    @OneToMany
    private Card card;
}
