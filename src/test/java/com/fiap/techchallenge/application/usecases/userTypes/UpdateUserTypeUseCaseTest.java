package com.fiap.techchallenge.application.usecases.userTypes;

import com.fiap.techchallenge.application.gateways.userType.UserTypeGateway;
import com.fiap.techchallenge.application.usecases.userType.UpdateUserTypeUseCase;
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
@DisplayName("UpdateUserTypeUseCase Tests")
class UpdateUserTypeUseCaseTest {

    @Mock
    private UserTypeGateway userTypeGateway;

    @InjectMocks
    private UpdateUserTypeUseCase updateUserTypeUseCase;

    private UserType existingUserType;

    @BeforeEach
    void setup() {
        existingUserType = new UserType();
        existingUserType.setId(1L);
        existingUserType.setName("CLIENTE");
    }

    @Test
    @DisplayName("Should update user type when it exists")
    void shouldUpdateUserType() {
        // Arrange
        String newName = "CLIENTE_VIP";
        when(userTypeGateway.findById(1L)).thenReturn(Optional.of(existingUserType));
        when(userTypeGateway.save(any(UserType.class))).thenReturn(existingUserType);

        // Act
        UserType updatedUserType = updateUserTypeUseCase.execute(1L, newName);

        // Assert
        assertThat(updatedUserType).isNotNull();
        assertThat(updatedUserType.getId()).isEqualTo(1L);
        assertThat(updatedUserType.getName()).isEqualTo(newName);
        verify(userTypeGateway, times(1)).findById(1L);
        verify(userTypeGateway, times(1)).save(any(UserType.class));
    }

    @Test
    @DisplayName("Should throw exception when user type is not found")
    void shouldThrowExceptionWhenUserTypeNotFound() {
        // Arrange
        when(userTypeGateway.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> updateUserTypeUseCase.execute(999L, "INVALIDO"))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Tipo de usuário não encontrado com ID: 999");

        verify(userTypeGateway, times(1)).findById(999L);
        verify(userTypeGateway, never()).save(any(UserType.class));
    }
}
