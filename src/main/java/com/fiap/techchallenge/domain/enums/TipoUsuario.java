package com.fiap.techchallenge.domain.enums;

/**
 * Tipos de usuário suportados pelo sistema.
 */
public enum TipoUsuario {
    CLIENTE("Cliente"),
    DONO_RESTAURANTE("Dono de Restaurante");

    private final String descricao;

    TipoUsuario(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
