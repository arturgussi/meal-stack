package com.fiap.techchallenge.domain.enums;

/**
 * Tipos de usuário suportados pelo sistema.
 */
public enum UserType {
    CLIENTE("Cliente"),
    DONO_RESTAURANTE("Dono de Restaurante");

    private final String descricao;

    UserType(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
