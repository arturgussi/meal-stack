package com.fiap.techchallenge.infrastructure.config.user;

import com.fiap.techchallenge.application.gateways.user.UserGateway;
import com.fiap.techchallenge.application.usecases.user.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;

@Configuration
public class UserConfig {

    @Bean
    public AuthenticateUserUseCase authenticateUserUseCase(UserGateway userGateway) {
        return new AuthenticateUserUseCase(userGateway);
    }

    @Bean
    public ChangeUserPasswordUseCase changeUserPasswordUseCase(UserGateway userGateway) {
        return new ChangeUserPasswordUseCase(userGateway);
    }

    @Bean
    public CreateUserUseCase createUserUseCase(UserGateway userGateway) {
        return new CreateUserUseCase(userGateway);
    }

    @Bean
    public DeleteUserUseCase deleteUserUseCase(UserGateway userGateway) {
        return new DeleteUserUseCase(userGateway);
    }

    @Bean
    public FindUserByIdUseCase findUserByIdUseCase(UserGateway userGateway) {
        return new FindUserByIdUseCase(userGateway);
    }

    @Bean
    public FindUsersByNameUseCase findUsersByNameUseCase(UserGateway userGateway) {
        return new FindUsersByNameUseCase(userGateway);
    }

    @Bean
    public ListAllUsersUseCase listAllUsersUseCase(UserGateway userGateway) {
        return new ListAllUsersUseCase(userGateway);
    }

    @Bean
    public UpdateUserUseCase updateUserUseCase(UserGateway userGateway) {
        return new UpdateUserUseCase(userGateway);
    }
}
