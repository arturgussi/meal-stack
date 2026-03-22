package com.fiap.techchallenge.application.usecases.user;

import com.fiap.techchallenge.application.gateways.user.UserGateway;
import com.fiap.techchallenge.infrastructure.exception.RecursoNaoEncontradoException;

public class DeleteUserUseCase {

    private final UserGateway userGateway;


    public DeleteUserUseCase(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public void execute(Long id) {
        if(!userGateway.existsById(id)) {
            throw new RecursoNaoEncontradoException(
                    "Usuário não encontrado com ID: " + id);
        }

        userGateway.deleteById(id);
    }

}
