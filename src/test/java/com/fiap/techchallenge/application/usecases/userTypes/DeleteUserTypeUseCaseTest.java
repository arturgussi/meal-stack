package com.fiap.techchallenge.application.usecases.userTypes;

import com.fiap.techchallenge.application.gateways.userType.UserTypeGateway;
import com.fiap.techchallenge.application.usecases.userType.DeleteUserTypeUseCase;
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

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("DeleteUserTypeUseCase Tests")
class DeleteUserTypeUseCaseTest {

    @Mock
    private UserTypeGateway userTypeGateway;

    @InjectMocks
    private DeleteUserTypeUseCase deleteUserTypeUseCase;

    private UserType clientUserType;
    private UserType restaurantUserType;
    private UserType adminUserType;

    @BeforeEach
    void setup() {
        clientUserType = new UserType();
        clientUserType.setId(1L);
        clientUserType.setName("CLIENTE");

        restaurantUserType = new UserType();
        restaurantUserType.setId(2L);
        restaurantUserType.setName("RESTAURANTE");

        adminUserType = new UserType();
        adminUserType.setId(3L);
        adminUserType.setName("ADMINISTRADOR");
    }

    @Test
    @DisplayName("Should delete user type when it is not protected")
    void shouldDeleteUserType() {
        // Arrange
        when(userTypeGateway.findById(3L)).thenReturn(Optional.of(adminUserType));

        // Act
        deleteUserTypeUseCase.execute(3L);

        // Assert
        verify(userTypeGateway, times(1)).findById(3L);
        verify(userTypeGateway, times(1)).deleteById(3L);
    }

    @Test
    @DisplayName("Should throw exception when user type is not found")
    void shouldThrowExceptionWhenUserTypeNotFound() {
        // Arrange
        when(userTypeGateway.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> deleteUserTypeUseCase.execute(99L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Tipo de usuário não encontrado com ID: 99");

        verify(userTypeGateway, times(1)).findById(99L);
        verify(userTypeGateway, never()).deleteById(anyLong());
    }

    @Test
    @DisplayName("Should throw exception when trying to delete CLIENTE")
    void shouldThrowExceptionWhenDeletingClient() {
        // Arrange
        when(userTypeGateway.findById(1L)).thenReturn(Optional.of(clientUserType));

        // Act & Assert
        assertThatThrownBy(() -> deleteUserTypeUseCase.execute(1L))
                .isInstanceOf(BusinessRuleException.class)
                .hasMessage("Não é permitido excluir os tipos de usuário 'CLIENTE' e 'DONO_RESTAURANTE'.");

        verify(userTypeGateway, times(1)).findById(1L);
        verify(userTypeGateway, never()).deleteById(anyLong());
    }

    @Test
    @DisplayName("Should throw exception when trying to delete RESTAURANTE")
    void shouldThrowExceptionWhenDeletingRestaurant() {
        // Arrange
        when(userTypeGateway.findById(2L)).thenReturn(Optional.of(restaurantUserType));

        // Act & Assert
        assertThatThrownBy(() -> deleteUserTypeUseCase.execute(2L))
                .isInstanceOf(BusinessRuleException.class)
                .hasMessage("Não é permitido excluir os tipos de usuário 'CLIENTE' e 'DONO_RESTAURANTE'.");

        verify(userTypeGateway, times(1)).findById(2L);
        verify(userTypeGateway, never()).deleteById(anyLong());
    }
}
