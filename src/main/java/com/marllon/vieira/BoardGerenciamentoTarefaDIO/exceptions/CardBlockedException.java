package com.marllon.vieira.BoardGerenciamentoTarefaDIO.exceptions;


//Exception se o card estiver sido bloqueado

public class CardBlockedException extends RuntimeException {
    public CardBlockedException(String message) {
        super(message);
    }
}
