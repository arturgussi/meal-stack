package com.fiap.techchallenge.application.usecases.user;

import com.fiap.techchallenge.application.gateways.user.UserGateway;
import com.fiap.techchallenge.domain.entities.User;
import com.fiap.techchallenge.infrastructure.exception.InvalidPasswordException;
import org.junit.jupiter.api.BeforeEach;
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
@DisplayName("Authenticate User Use Case Tests")
class AuthenticateUserUseCaseTest {

    @Mock
    private UserGateway userGateway;

    @InjectMocks
    private AuthenticateUserUseCase authenticateUserUseCase;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setLogin("joao.login");
        user.setPassword("correct_password");
    }

    @Test
    @DisplayName("Should authenticate successfully with correct credentials")
    void shouldAuthenticateWithSuccess() {
        // Arrange
        when(userGateway.findByLogin("joao.login")).thenReturn(Optional.of(user));

        // Act
        User result = authenticateUserUseCase.execute("joao.login", "correct_password");

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getLogin()).isEqualTo("joao.login");
    }

    @Test
    @DisplayName("Should throw InvalidPasswordException when login is not found")
    void shouldThrowExceptionWhenLoginNotFound() {
        // Arrange
        when(userGateway.findByLogin("invalid")).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> authenticateUserUseCase.execute("invalid", "any"))
                .isInstanceOf(InvalidPasswordException.class)
                .hasMessage("Credenciais inválidas");
    }

    @Test
    @DisplayName("Should throw InvalidPasswordException when password is incorrect")
    void shouldThrowExceptionWhenPasswordIncorrect() {
        // Arrange
        when(userGateway.findByLogin("joao.login")).thenReturn(Optional.of(user));

        // Act & Assert
        assertThatThrownBy(() -> authenticateUserUseCase.execute("joao.login", "wrong_password"))
                .isInstanceOf(InvalidPasswordException.class)
                .hasMessage("Credenciais inválidas");
    }
}
