package com.fiap.techchallenge.application.usecases.user;

import com.fiap.techchallenge.application.gateways.user.UserGateway;
import com.fiap.techchallenge.infrastructure.exception.ResourceNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Delete User Use Case Tests")
class DeleteUserUseCaseTest {

    @Mock
    private UserGateway userGateway;

    @InjectMocks
    private DeleteUserUseCase deleteUserUseCase;

    @Test
    @DisplayName("Should delete user successfully when ID exists")
    void shouldDeleteUserWithSuccess() {
        when(userGateway.existsById(1L)).thenReturn(true);

        deleteUserUseCase.execute(1L);

        verify(userGateway, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when user ID does not exist")
    void shouldThrowExceptionWhenUserNotFound() {
        when(userGateway.existsById(1L)).thenReturn(false);

        assertThatThrownBy(() -> deleteUserUseCase.execute(1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Usuário não encontrado com ID: 1");

        verify(userGateway, never()).deleteById(anyLong());
    }
}
