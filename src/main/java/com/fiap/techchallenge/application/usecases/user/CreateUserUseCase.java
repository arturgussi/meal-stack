package com.fiap.techchallenge.application.usecases.user;

import com.fiap.techchallenge.application.gateways.user.UserGateway;
import com.fiap.techchallenge.domain.entities.User;
import com.fiap.techchallenge.infrastructure.exception.BusinessRuleException;

public class CreateUserUseCase {

    private final UserGateway userGateway;

    public CreateUserUseCase(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public User execute(User user) {

        if (userGateway.existsByEmail(user.getEmail())) {
            throw new BusinessRuleException("Email já cadastrado: " + user.getEmail());
        }

        if (userGateway.existsByLogin(user.getLogin())) {
            throw new BusinessRuleException("Login já cadastrado: " + user.getLogin());
        }

        if (userGateway.existsByCpf(user.getCpf())) {
            throw new BusinessRuleException("CPF já cadastrado: " + user.getCpf());
        }

        return userGateway.save(user);
    }
}
