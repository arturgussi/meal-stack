package com.fiap.techchallenge.application.usecases.userTypes;

import com.fiap.techchallenge.application.gateways.userType.UserTypeGateway;
import com.fiap.techchallenge.application.usecases.userType.ListAllUserTypesUseCase;
import com.fiap.techchallenge.domain.entities.UserType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("ListAllUserTypesUseCase Tests")
class ListAllUserTypesUseCaseTest {

    @Mock
    private UserTypeGateway userTypeGateway;

    @InjectMocks
    private ListAllUserTypesUseCase listAllUserTypesUseCase;

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
    @DisplayName("Should list all user types")
    void shouldListAllUserTypes() {
        // Arrange
        List<UserType> allUserTypes = Arrays.asList(clientUserType, restaurantUserType, adminUserType);
        when(userTypeGateway.findAll()).thenReturn(allUserTypes);

        // Act
        List<UserType> result = listAllUserTypesUseCase.execute();

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(3);
        assertThat(result).containsExactlyInAnyOrder(clientUserType, restaurantUserType, adminUserType);
        verify(userTypeGateway, times(1)).findAll();
    }

    @Test
    @DisplayName("Should return empty list when no user types found")
    void shouldReturnEmptyListWhenNoUserTypesFound() {
        // Arrange
        when(userTypeGateway.findAll()).thenReturn(Arrays.asList());

        // Act
        List<UserType> result = listAllUserTypesUseCase.execute();

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).isEmpty();
        verify(userTypeGateway, times(1)).findAll();
    }
}
