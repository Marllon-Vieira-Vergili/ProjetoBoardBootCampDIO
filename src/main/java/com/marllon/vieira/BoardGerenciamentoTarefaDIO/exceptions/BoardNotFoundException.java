package com.marllon.vieira.BoardGerenciamentoTarefaDIO.exceptions;

//Exception board não for localizado

public class BoardNotFoundException extends RuntimeException {
    public BoardNotFoundException(String message) {
        super(message);
    }
}
