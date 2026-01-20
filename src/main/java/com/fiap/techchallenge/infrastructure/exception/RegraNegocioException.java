package com.fiap.techchallenge.infrastructure.exception;

/**
 * Exceção lançada quando uma regra de negócio é violada.
 * Exemplos: Email duplicado, CPF já cadastrado, etc.
 */
public class RegraNegocioException extends RuntimeException {

    public RegraNegocioException(String mensagem) {
        super(mensagem);
    }

    public RegraNegocioException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
