package com.fiap.techchallenge.application.usecases.userType;

import java.util.List;

import com.fiap.techchallenge.application.gateways.userType.UserTypeGateway;
import com.fiap.techchallenge.domain.entities.UserType;

public class FindUserTypeByNameContainingIgnoreCaseUseCase {

    private final UserTypeGateway userTypeGateway;

    public FindUserTypeByNameContainingIgnoreCaseUseCase(UserTypeGateway userTypeGateway) {
        this.userTypeGateway = userTypeGateway;
    }

    public List<UserType> execute(String name) {
        return userTypeGateway.findByNameContainingIgnoreCase(name);
    }

}
