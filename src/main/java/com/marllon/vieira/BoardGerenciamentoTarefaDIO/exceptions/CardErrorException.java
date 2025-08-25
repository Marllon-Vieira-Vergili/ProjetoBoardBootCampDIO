package com.marllon.vieira.BoardGerenciamentoTarefaDIO.exceptions;


//Exceção de alteração de valores do card

public class CardErrorException extends RuntimeException {
    public CardErrorException(String message) {
        super(message);
    }
}
