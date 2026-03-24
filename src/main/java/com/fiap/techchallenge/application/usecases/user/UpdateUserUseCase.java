package com.fiap.techchallenge.application.usecases.user;

import com.fiap.techchallenge.application.gateways.user.UserGateway;
import com.fiap.techchallenge.domain.entities.User;
import com.fiap.techchallenge.infrastructure.exception.ResourceNotFoundException;
import com.fiap.techchallenge.infrastructure.exception.BusinessRuleException;

public class UpdateUserUseCase {

    final UserGateway userGateway;

    public UpdateUserUseCase(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public User execute(Long id, User userUpdates) {
        User existingUser = userGateway.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                "Usuário não encontrado com ID: " + id));

        if (!userUpdates.getEmail().equals(existingUser.getEmail()) &&
                userGateway.existsByEmail(userUpdates.getEmail())) {
            throw new BusinessRuleException("Email já cadastrado: " + userUpdates.getEmail());
        }

        existingUser.setName(userUpdates.getName());
        existingUser.setEmail(userUpdates.getEmail());
        existingUser.setUserType(userUpdates.getUserType());
        existingUser.setStreetAddress(userUpdates.getStreetAddress());
        existingUser.setNumberAddress(userUpdates.getNumberAddress());
        existingUser.setCityAddress(userUpdates.getCityAddress());
        existingUser.setCepAddress(userUpdates.getCepAddress());

        return userGateway.save(existingUser);

    }
}
