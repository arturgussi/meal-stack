package com.fiap.techchallenge.infrastructure.exception;

/**
 * Exceção lançada quando uma regra de negócio é violada.
 * Exemplos: Email duplicado, CPF já cadastrado, etc.
 */
public class BusinessRuleException extends RuntimeException {

    public BusinessRuleException(String mensagem) {
        super(mensagem);
    }

    public BusinessRuleException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
