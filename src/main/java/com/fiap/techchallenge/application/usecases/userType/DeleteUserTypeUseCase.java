package com.fiap.techchallenge.application.usecases.userType;

import com.fiap.techchallenge.application.gateways.userType.UserTypeGateway;
import com.fiap.techchallenge.domain.entities.UserType;
import com.fiap.techchallenge.infrastructure.exception.BusinessRuleException;
import com.fiap.techchallenge.infrastructure.exception.ResourceNotFoundException;

public class DeleteUserTypeUseCase {

    private final UserTypeGateway userTypeGateway;

    public DeleteUserTypeUseCase(UserTypeGateway userTypeGateway) {
        this.userTypeGateway = userTypeGateway;
    }

    public void execute(Long id) {
        UserType userType = userTypeGateway.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Tipo de usuário não encontrado com ID: " + id));

        if ("CLIENTE".equals(userType.getName()) || "DONO_RESTAURANTE".equals(userType.getName()) || "RESTAURANTE".equals(userType.getName())) {
            throw new BusinessRuleException(
                    "Não é permitido excluir os tipos de usuário 'CLIENTE' e 'DONO_RESTAURANTE'.");
        }

        userTypeGateway.deleteById(id);
    }

}
