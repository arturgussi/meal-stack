package com.fiap.techchallenge.application.usecases.user;

import com.fiap.techchallenge.application.gateways.user.UserGateway;
import com.fiap.techchallenge.domain.entities.User;
import com.fiap.techchallenge.infrastructure.exception.ResourceNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Find User By ID Use Case Tests")
class FindUserByIdUseCaseTest {

    @Mock
    private UserGateway userGateway;

    @InjectMocks
    private FindUserByIdUseCase findUserByIdUseCase;

    @Test
    @DisplayName("Should return user when ID exists")
    void shouldReturnUserWhenIdExists() {
        // Arrange
        User user = new User();
        user.setId(1L);
        when(userGateway.findById(1L)).thenReturn(Optional.of(user));

        // Act
        User result = findUserByIdUseCase.execute(1L);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when ID does not exist")
    void shouldThrowExceptionWhenIdDoesNotExist() {
        // Arrange
        when(userGateway.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> findUserByIdUseCase.execute(1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Usuário não encontrado com ID: 1");
    }
}
