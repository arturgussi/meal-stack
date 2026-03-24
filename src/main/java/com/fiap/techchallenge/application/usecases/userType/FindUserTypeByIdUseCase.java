package com.fiap.techchallenge.application.usecases.userType;

import com.fiap.techchallenge.application.gateways.userType.UserTypeGateway;
import com.fiap.techchallenge.domain.entities.UserType;
import com.fiap.techchallenge.infrastructure.exception.ResourceNotFoundException;

public class FindUserTypeByIdUseCase {

    private final UserTypeGateway userTypeGateway;

    public FindUserTypeByIdUseCase(UserTypeGateway userTypeGateway) {
        this.userTypeGateway = userTypeGateway;
    }

    public UserType execute(Long id) {
        return userTypeGateway.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                "Tipo de usuário não encontrado com ID: " + id));
    }

}