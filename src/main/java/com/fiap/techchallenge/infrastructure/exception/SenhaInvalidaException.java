package com.fiap.techchallenge.infrastructure.exception;

/**
 * Exceção lançada quando uma senha é inválida.
 * Usada no login ou na troca de senha.
 */
public class SenhaInvalidaException extends RuntimeException {

    public SenhaInvalidaException(String mensagem) {
        super(mensagem);
    }

    public SenhaInvalidaException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
