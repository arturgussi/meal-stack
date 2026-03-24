package com.fiap.techchallenge.application.usecases.user;

import com.fiap.techchallenge.application.gateways.user.UserGateway;
import com.fiap.techchallenge.domain.entities.User;
import com.fiap.techchallenge.infrastructure.exception.InvalidPasswordException;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Change User Password Use Case Tests")
class ChangeUserPasswordUseCaseTest {

    @Mock
    private UserGateway userGateway;

    @InjectMocks
    private ChangeUserPasswordUseCase changeUserPasswordUseCase;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setPassword("old_password");
    }

    @Test
    @DisplayName("Should change password successfully when current password is correct")
    void shouldChangePasswordWithSuccess() {
        when(userGateway.findById(1L)).thenReturn(Optional.of(user));
        when(userGateway.save(any(User.class))).thenReturn(user);

        changeUserPasswordUseCase.execute(1L, "old_password", "new_password");

        assertThat(user.getPassword()).isEqualTo("new_password");
        verify(userGateway, times(1)).save(user);
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when user is not found")
    void shouldThrowExceptionWhenUserNotFound() {
        when(userGateway.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> changeUserPasswordUseCase.execute(1L, "any", "new"))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Usuário não encontrado com ID: 1");
    }

    @Test
    @DisplayName("Should throw InvalidPasswordException when current password is incorrect")
    void shouldThrowExceptionWhenCurrentPasswordIncorrect() {
        when(userGateway.findById(1L)).thenReturn(Optional.of(user));

        assertThatThrownBy(() -> changeUserPasswordUseCase.execute(1L, "wrong_password", "new"))
                .isInstanceOf(InvalidPasswordException.class)
                .hasMessage("Senha atual errada");

        verify(userGateway, never()).save(any());
    }
}
