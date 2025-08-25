package com.marllon.vieira.BoardGerenciamentoTarefaDIO.exceptions;

//Exception se não achar o card na base de dados

public class CardNotFoundException extends RuntimeException {
    public CardNotFoundException(String message) {
        super(message);
    }
}
