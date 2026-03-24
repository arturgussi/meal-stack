package com.fiap.techchallenge.application.usecases.userType;

import com.fiap.techchallenge.application.gateways.userType.UserTypeGateway;
import com.fiap.techchallenge.domain.entities.UserType;
import com.fiap.techchallenge.infrastructure.exception.BusinessRuleException;

public class CreateUserTypeUseCase {

    private final UserTypeGateway userTypeGateway;

    public CreateUserTypeUseCase(UserTypeGateway userTypeGateway) {
        this.userTypeGateway = userTypeGateway;
    }

    public UserType execute(UserType userType) {

        if (userTypeGateway.existsByName(userType.getName())) {
            throw new BusinessRuleException("Tipo de usuário já cadastrado: " + userType.getName());
        }

        return userTypeGateway.save(userType);
    }
}
