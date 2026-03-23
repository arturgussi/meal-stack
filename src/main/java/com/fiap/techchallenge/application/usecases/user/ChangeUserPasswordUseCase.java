package com.fiap.techchallenge.application.usecases.user;

import com.fiap.techchallenge.application.gateways.user.UserGateway;
import com.fiap.techchallenge.domain.entities.User;
import com.fiap.techchallenge.infrastructure.exception.ResourceNotFoundException;
import com.fiap.techchallenge.infrastructure.exception.InvalidPasswordException;

public class ChangeUserPasswordUseCase {

    private final UserGateway userGateway;

    public ChangeUserPasswordUseCase(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public User execute(Long id, String oldPassword, String newPassword) {
        User existingUser = userGateway.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                "Usuário não encontrado com ID: " + id));

        if (!existingUser.getPassword().equals(oldPassword)) {
            throw new InvalidPasswordException("Senha atual errada");
        }

        existingUser.setPassword(newPassword);

        return existingUser;
    }

}
