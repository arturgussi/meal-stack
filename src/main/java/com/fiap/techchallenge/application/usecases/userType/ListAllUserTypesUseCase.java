package com.fiap.techchallenge.application.usecases.userType;

import java.util.List;

import com.fiap.techchallenge.application.gateways.userType.UserTypeGateway;
import com.fiap.techchallenge.domain.entities.UserType;

public class ListAllUserTypesUseCase {

    private final UserTypeGateway userTypeGateway;

    public ListAllUserTypesUseCase(UserTypeGateway userTypeGateway) {
        this.userTypeGateway = userTypeGateway;
    }

    public List<UserType> execute() {
        return userTypeGateway.findAll();
    }

}
