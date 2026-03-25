package com.fiap.techchallenge.application.usecases.user;

import com.fiap.techchallenge.application.gateways.user.UserGateway;
import com.fiap.techchallenge.domain.entities.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("List All Users Use Case Tests")
class ListAllUsersUseCaseTest {

    @Mock
    private UserGateway userGateway;

    @InjectMocks
    private ListAllUsersUseCase listAllUsersUseCase;

    @Test
    @DisplayName("Should return all users")
    void shouldReturnAllUsers() {
        User user1 = new User();
        User user2 = new User();
        when(userGateway.findAll()).thenReturn(List.of(user1, user2));

        List<User> result = listAllUsersUseCase.execute();

        assertThat(result).hasSize(2);
    }
}
