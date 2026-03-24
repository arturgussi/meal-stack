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

    public UserType execute(Long id, UserType userTypeUpdates) {
        UserType existingUserType = userTypeGateway.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                "Tipo de usuário não encontrado com ID: " + id));

        if (!userTypeUpdates.getName().equals(existingUserType.getName())
                && userTypeGateway.existsByName(userTypeUpdates.getName())) {
            throw new BusinessRuleException("O nome do tipo de usuário já está em uso: " + userTypeUpdates.getName());
        }

        existingUserType.setName(userTypeUpdates.getName());
        return userTypeGateway.save(existingUserType);
    }

}
