package com.fiap.techchallenge.infrastructure.exception;

/**
 * Exceção lançada quando um recurso não é encontrado.
 * Exemplo: Buscar usuário por ID inexistente.
 */
public class RecursoNaoEncontradoException extends RuntimeException {

    public RecursoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public RecursoNaoEncontradoException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
