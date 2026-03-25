package com.fiap.techchallenge.application.usecases.user;

import com.fiap.techchallenge.application.gateways.user.UserGateway;
import com.fiap.techchallenge.domain.entities.User;
import com.fiap.techchallenge.infrastructure.exception.InvalidPasswordException;

public class AuthenticateUserUseCase {

    private final UserGateway userGateway;

    public AuthenticateUserUseCase(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public User execute(String login, String password) {
        User existingUser = userGateway.findByLogin(login).orElseThrow(() -> new InvalidPasswordException(
                "Credenciais inválidas"));

        if (!existingUser.getPassword().equals(password)) {
            throw new InvalidPasswordException("Credenciais inválidas");
        }

        return existingUser;
    }
}
