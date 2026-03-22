package com.fiap.techchallenge.application.usecases.user;

import com.fiap.techchallenge.application.gateways.user.UserGateway;
import com.fiap.techchallenge.domain.entities.User;
import com.fiap.techchallenge.infrastructure.exception.RecursoNaoEncontradoException;

public class FindUserByIdUseCase {

    private final UserGateway userGateway;

    public FindUserByIdUseCase(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public User execute(Long id) {
        return userGateway.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException(
                "Usuário não encontrado com ID: " + id));
    }
}
