package com.fiap.techchallenge.application.usecases.user;

import com.fiap.techchallenge.application.gateways.user.UserGateway;
import com.fiap.techchallenge.domain.entities.User;

import java.util.List;

public class FindUsersByNameUseCase {

    private final UserGateway userGateway;

    public FindUsersByNameUseCase(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public List<User> execute(String name) {
        return userGateway.findByNameContainingIgnoreCase(name);
    }
}
