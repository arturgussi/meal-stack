package com.fiap.techchallenge.infrastructure.config.userType;

import com.fiap.techchallenge.application.gateways.userType.UserTypeGateway;
import com.fiap.techchallenge.application.usecases.userType.CreateUserTypeUseCase;
import com.fiap.techchallenge.application.usecases.userType.DeleteUserTypeUseCase;
import com.fiap.techchallenge.application.usecases.userType.FindUserTypeByNameContainingIgnoreCaseUseCase;
import com.fiap.techchallenge.application.usecases.userType.FindUserTypeByIdUseCase;
import com.fiap.techchallenge.application.usecases.userType.FindUserTypeByNameUseCase;
import com.fiap.techchallenge.application.usecases.userType.ListAllUserTypesUseCase;
import com.fiap.techchallenge.application.usecases.userType.UpdateUserTypeUseCase;
import com.fiap.techchallenge.infrastructure.persistence.impl.UserTypeGatewayImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserTypeConfig {

    @Bean
    public UserTypeGateway userTypeGateway(UserTypeGatewayImpl repository) {
        return repository;
    }

    @Bean
    public CreateUserTypeUseCase createUserTypeUseCase(UserTypeGateway userTypeGateway) {
        return new CreateUserTypeUseCase(userTypeGateway);
    }

    @Bean
    public FindUserTypeByIdUseCase findByIdUserTypeUseCase(UserTypeGateway userTypeGateway) {
        return new FindUserTypeByIdUseCase(userTypeGateway);
    }

    @Bean
    public FindUserTypeByNameUseCase findByNameUserTypeUseCase(UserTypeGateway userTypeGateway) {
        return new FindUserTypeByNameUseCase(userTypeGateway);
    }

    @Bean
    public FindUserTypeByNameContainingIgnoreCaseUseCase findByNameContainingIgnoreCaseUseCase(
            UserTypeGateway userTypeGateway) {
        return new FindUserTypeByNameContainingIgnoreCaseUseCase(userTypeGateway);
    }

    @Bean
    public ListAllUserTypesUseCase listAllUserTypesUseCase(UserTypeGateway userTypeGateway) {
        return new ListAllUserTypesUseCase(userTypeGateway);
    }

    @Bean
    public UpdateUserTypeUseCase updateUserTypeUseCase(UserTypeGateway userTypeGateway) {
        return new UpdateUserTypeUseCase(userTypeGateway);
    }

    @Bean
    public DeleteUserTypeUseCase deleteUserTypeUseCase(UserTypeGateway userTypeGateway) {
        return new DeleteUserTypeUseCase(userTypeGateway);
    }

}
