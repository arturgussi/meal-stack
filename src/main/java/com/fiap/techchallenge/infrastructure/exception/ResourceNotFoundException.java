package com.fiap.techchallenge.infrastructure.exception;

/**
 * Exceção lançada quando um recurso não é encontrado.
 * Exemplo: Buscar usuário por ID inexistente.
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String mensagem) {
        super(mensagem);
    }

    public ResourceNotFoundException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
