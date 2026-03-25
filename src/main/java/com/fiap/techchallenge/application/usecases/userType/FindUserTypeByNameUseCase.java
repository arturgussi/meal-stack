package com.fiap.techchallenge.application.usecases.userType;

import com.fiap.techchallenge.application.gateways.userType.UserTypeGateway;
import com.fiap.techchallenge.domain.entities.UserType;
import com.fiap.techchallenge.infrastructure.exception.ResourceNotFoundException;

public class FindUserTypeByNameUseCase {

    private final UserTypeGateway userTypeGateway;

    public FindUserTypeByNameUseCase(UserTypeGateway userTypeGateway) {
        this.userTypeGateway = userTypeGateway;
    }

    public UserType execute(String name) {
        return userTypeGateway.findByName(name).orElseThrow(() -> new ResourceNotFoundException(
                "Tipo de usuário não encontrado com nome: " + name));
    }

}
