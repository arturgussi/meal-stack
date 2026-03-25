package com.fiap.techchallenge.application.usecases.userTypes;

import com.fiap.techchallenge.application.gateways.userType.UserTypeGateway;
import com.fiap.techchallenge.application.usecases.userType.FindUserTypeByIdUseCase;
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
@DisplayName("FindUserTypeByIdUseCase Tests")
class FindUserTypeByIdUseCaseTest {

    @Mock
    private UserTypeGateway userTypeGateway;

    @InjectMocks
    private FindUserTypeByIdUseCase findUserTypeByIdUseCase;

    private UserType userType;

    @BeforeEach
    void setup() {
        userType = new UserType();
        userType.setId(1L);
        userType.setName("CLIENTE");
    }

    @Test
    @DisplayName("Should find user type by ID when it exists")
    void shouldFindUserTypeById() {
        // Arrange
        when(userTypeGateway.findById(1L)).thenReturn(Optional.of(userType));

        // Act
        UserType result = findUserTypeByIdUseCase.execute(1L);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("CLIENTE");
        verify(userTypeGateway, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Should throw exception when user type is not found")
    void shouldThrowExceptionWhenUserTypeNotFound() {
        // Arrange
        when(userTypeGateway.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> findUserTypeByIdUseCase.execute(99L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Tipo de usuário não encontrado com ID: 99");

        verify(userTypeGateway, times(1)).findById(99L);
    }
}
