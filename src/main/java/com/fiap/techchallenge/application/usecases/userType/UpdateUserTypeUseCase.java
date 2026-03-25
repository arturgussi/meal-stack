package com.fiap.techchallenge.application.usecases.userType;

import com.fiap.techchallenge.application.gateways.userType.UserTypeGateway;
import com.fiap.techchallenge.domain.entities.UserType;
import com.fiap.techchallenge.infrastructure.exception.BusinessRuleException;
import com.fiap.techchallenge.infrastructure.exception.ResourceNotFoundException;

public class UpdateUserTypeUseCase {

    private final UserTypeGateway userTypeGateway;

    public UpdateUserTypeUseCase(UserTypeGateway userTypeGateway) {
        this.userTypeGateway = userTypeGateway;
    }

    public UserType execute(Long id, String name) {
        UserType existingUserType = userTypeGateway.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                "Tipo de usuário não encontrado com ID: " + id));

        if (!name.equals(existingUserType.getName())
                && userTypeGateway.existsByName(name)) {
            throw new BusinessRuleException("O nome do tipo de usuário já está em uso: " + name);
        }

        existingUserType.setName(name);
        return userTypeGateway.save(existingUserType);
    }

}
