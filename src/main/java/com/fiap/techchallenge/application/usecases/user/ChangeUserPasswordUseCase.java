package com.fiap.techchallenge.application.usecases.user;

import com.fiap.techchallenge.application.gateways.user.UserGateway;
import com.fiap.techchallenge.domain.entities.User;
import com.fiap.techchallenge.infrastructure.exception.RecursoNaoEncontradoException;
import com.fiap.techchallenge.infrastructure.exception.SenhaInvalidaException;

public class ChangeUserPasswordUseCase {

    private final UserGateway userGateway;

    public ChangeUserPasswordUseCase(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public User execute(Long id, String oldPassword, String newPassword) {
        User existingUser = userGateway.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException(
                "Usuário não encontrado com ID: " + id));

        if (!existingUser.getPassword().equals(oldPassword)) {
            throw new SenhaInvalidaException("Senha atual errada");
        }

        existingUser.setPassword(newPassword);

        return existingUser;
    }

}
