package com.fiap.techchallenge.application.usecases.user;

import com.fiap.techchallenge.application.gateways.user.UserGateway;
import com.fiap.techchallenge.domain.entities.User;
import com.fiap.techchallenge.domain.enums.UserType;
import com.fiap.techchallenge.infrastructure.exception.BusinessRuleException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Create User Use Case Tests")
class CreateUserUseCaseTest {

    @Mock
    private UserGateway userGateway;

    @InjectMocks
    private CreateUserUseCase createUserUseCase;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User(null, "João", "joao@email.com", "joao.login", "senha123", "12345678901", 
                UserType.CLIENTE, "Rua A", 1, "SP", "01000000");
    }

    @Test
    @DisplayName("Should create a user successfully")
    void shouldCreateUserWithSuccess() {
        // Arrange
        when(userGateway.existsByEmail(user.getEmail())).thenReturn(false);
        when(userGateway.existsByLogin(user.getLogin())).thenReturn(false);
        when(userGateway.existsByCpf(user.getCpf())).thenReturn(false);
        when(userGateway.save(any(User.class))).thenReturn(user);

        // Act
        User result = createUserUseCase.execute(user);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getEmail()).isEqualTo(user.getEmail());
        verify(userGateway, times(1)).save(user);
    }

    @Test
    @DisplayName("Should throw BusinessRuleException when email already exists")
    void shouldThrowExceptionWhenEmailExists() {
        // Arrange
        when(userGateway.existsByEmail(user.getEmail())).thenReturn(true);

        // Act & Assert
        assertThatThrownBy(() -> createUserUseCase.execute(user))
                .isInstanceOf(BusinessRuleException.class)
                .hasMessage("Email já cadastrado: joao@email.com");
        
        verify(userGateway, never()).save(any());
    }

    @Test
    @DisplayName("Should throw BusinessRuleException when login already exists")
    void shouldThrowExceptionWhenLoginExists() {
        // Arrange
        when(userGateway.existsByEmail(user.getEmail())).thenReturn(false);
        when(userGateway.existsByLogin(user.getLogin())).thenReturn(true);

        // Act & Assert
        assertThatThrownBy(() -> createUserUseCase.execute(user))
                .isInstanceOf(BusinessRuleException.class)
                .hasMessage("Login já cadastrado: joao.login");
        
        verify(userGateway, never()).save(any());
    }

    @Test
    @DisplayName("Should throw BusinessRuleException when CPF already exists")
    void shouldThrowExceptionWhenCpfExists() {
        // Arrange
        when(userGateway.existsByEmail(user.getEmail())).thenReturn(false);
        when(userGateway.existsByLogin(user.getLogin())).thenReturn(false);
        when(userGateway.existsByCpf(user.getCpf())).thenReturn(true);

        // Act & Assert
        assertThatThrownBy(() -> createUserUseCase.execute(user))
                .isInstanceOf(BusinessRuleException.class)
                .hasMessage("CPF já cadastrado: 12345678901");
        
        verify(userGateway, never()).save(any());
    }
}