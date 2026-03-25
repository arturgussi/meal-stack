package com.fiap.techchallenge.application.usecases.userTypes;

import com.fiap.techchallenge.application.gateways.userType.UserTypeGateway;
import com.fiap.techchallenge.application.usecases.userType.FindUserTypeByNameUseCase;
import com.fiap.techchallenge.domain.entities.UserType;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("FindUserTypeByNameUseCase Tests")
class FindUserTypeByNameUseCaseTest {

    @Mock
    private UserTypeGateway userTypeGateway;

    @InjectMocks
    private FindUserTypeByNameUseCase findUserTypeByNameUseCase;

    private UserType userType;

    @BeforeEach
    void setup() {
        userType = new UserType();
        userType.setId(1L);
        userType.setName("CLIENTE");
    }

    @Test
    @DisplayName("Should find user type by name when it exists")
    void shouldFindUserTypeByName() {
        // Arrange
        when(userTypeGateway.findByName(anyString())).thenReturn(Optional.of(userType));

        // Act
        UserType result = findUserTypeByNameUseCase.execute("CLIENTE");

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("CLIENTE");
        verify(userTypeGateway, times(1)).findByName("CLIENTE");
    }

    @Test
    @DisplayName("Should throw exception when user type is not found")
    void shouldThrowExceptionWhenUserTypeNotFound() {
        // Arrange
        when(userTypeGateway.findByName(anyString())).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> findUserTypeByNameUseCase.execute("INVALIDO"))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Tipo de usuário não encontrado com nome: INVALIDO");

        verify(userTypeGateway, times(1)).findByName("INVALIDO");
    }
}
