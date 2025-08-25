package com.marllon.vieira.BoardGerenciamentoTarefaDIO.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

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

    private Long id;

    @Column(name = "name")
    @NotBlank(message = "Campo name da tabela board_column não pode ficar em branco")

    @NotNull(message = "Campo name da tabela board_column não pode ser nulo!!")
    private String name;

    @Column(name = "kind")
    @Enumerated(EnumType.STRING)

    @NotNull(message = "Campo kind da tabela board_column não pode ser nulo!")
    private BoardColumnKindEnum kind;

    @Column(name = "order_enum")
    @NotNull(message = "Campo order da tabela board_column não pode ser nulo!")

    private Integer order;


    //Para relacionar
    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @OneToMany(mappedBy = "boardColumn", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Card> cards;
}
