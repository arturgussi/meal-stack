package com.fiap.techchallenge.infrastructure.exception;

/**
 * Exceção lançada quando uma senha é inválida.
 * Usada no login ou na troca de senha.
 */
public class InvalidPasswordException extends RuntimeException {

    public InvalidPasswordException(String mensagem) {
        super(mensagem);
    }

    public InvalidPasswordException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
