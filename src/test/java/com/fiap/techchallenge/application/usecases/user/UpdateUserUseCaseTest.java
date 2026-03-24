package com.fiap.techchallenge.application.usecases.user;

import com.fiap.techchallenge.application.gateways.user.UserGateway;
import com.fiap.techchallenge.domain.entities.User;
import com.fiap.techchallenge.domain.entities.UserType;
import com.fiap.techchallenge.infrastructure.exception.BusinessRuleException;
import com.fiap.techchallenge.infrastructure.exception.ResourceNotFoundException;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Update User Use Case Tests")
class UpdateUserUseCaseTest {

    @Mock
    private UserGateway userGateway;

    @InjectMocks
    private UpdateUserUseCase updateUserUseCase;

    private User existingUser;
    private User userUpdates;

    @BeforeEach
    void setUp() {
        UserType userType = new UserType();
        userType.setId(1L);
        existingUser = new User(1L, "João Old", "old@email.com", "joao.login", "senha123", "12345678901",
                userType, "Rua A", 1, "SP", "01000000");

        userUpdates = new User(null, "João New", "new@email.com", "joao.login", "senha123", "12345678901",
                userType, "Rua B", 2, "RJ", "20000000");
    }

    @Test
    @DisplayName("Should update user successfully")
    void shouldUpdateUserWithSuccess() {
        // Arrange
        when(userGateway.findById(1L)).thenReturn(Optional.of(existingUser));
        when(userGateway.existsByEmail("new@email.com")).thenReturn(false);
        when(userGateway.save(any(User.class))).thenReturn(existingUser);

        // Act
        User result = updateUserUseCase.execute(1L, userUpdates);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("João New");
        assertThat(result.getEmail()).isEqualTo("new@email.com");
        verify(userGateway, times(1)).save(existingUser);
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when user does not exist")
    void shouldThrowExceptionWhenUserNotFound() {
        // Arrange
        when(userGateway.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> updateUserUseCase.execute(1L, userUpdates))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Usuário não encontrado com ID: 1");

        verify(userGateway, never()).save(any());
    }

    @Test
    @DisplayName("Should throw BusinessRuleException when new email is already taken by another user")
    void shouldThrowExceptionWhenEmailAlreadyTaken() {
        // Arrange
        when(userGateway.findById(1L)).thenReturn(Optional.of(existingUser));
        when(userGateway.existsByEmail("new@email.com")).thenReturn(true);

        // Act & Assert
        assertThatThrownBy(() -> updateUserUseCase.execute(1L, userUpdates))
                .isInstanceOf(BusinessRuleException.class)
                .hasMessage("Email já cadastrado: new@email.com");

        verify(userGateway, never()).save(any());
    }

    @Test
    @DisplayName("Should update user successfully when email remains the same")
    void shouldUpdateUserWhenEmailUnchanged() {
        // Arrange
        userUpdates.setEmail("old@email.com");
        when(userGateway.findById(1L)).thenReturn(Optional.of(existingUser));
        when(userGateway.save(any(User.class))).thenReturn(existingUser);

        // Act
        User result = updateUserUseCase.execute(1L, userUpdates);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getEmail()).isEqualTo("old@email.com");
        verify(userGateway, never()).existsByEmail(anyString());
        verify(userGateway, times(1)).save(existingUser);
    }
}
