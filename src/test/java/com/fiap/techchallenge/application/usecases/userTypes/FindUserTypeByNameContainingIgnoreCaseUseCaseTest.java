package com.fiap.techchallenge.application.usecases.userTypes;

import com.fiap.techchallenge.application.gateways.userType.UserTypeGateway;
import com.fiap.techchallenge.application.usecases.userType.FindUserTypeByNameContainingIgnoreCaseUseCase;
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
@DisplayName("FindUserTypeByNameContainingIgnoreCaseUseCase Tests")
class FindUserTypeByNameContainingIgnoreCaseUseCaseTest {

    @Mock
    private UserTypeGateway userTypeGateway;

    @InjectMocks
    private FindUserTypeByNameContainingIgnoreCaseUseCase findUserTypeByNameContainingIgnoreCaseUseCase;

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
    @DisplayName("Should find user types by name containingIgnoreCase")
    void shouldFindUserTypesByNameContainingIgnoreCase() {
        // Arrange
        String searchTerm = "cli";
        List<UserType> expectedList = Arrays.asList(clientUserType);
        when(userTypeGateway.findByNameContainingIgnoreCase(searchTerm))
                .thenReturn(expectedList);

        // Act
        List<UserType> result = findUserTypeByNameContainingIgnoreCaseUseCase.execute(searchTerm);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("CLIENTE");
        verify(userTypeGateway, times(1)).findByNameContainingIgnoreCase(searchTerm);
    }

    @Test
    @DisplayName("Should return empty list when no user types found")
    void shouldReturnEmptyListWhenNoUserTypesFound() {
        // Arrange
        String searchTerm = "XYZ";
        when(userTypeGateway.findByNameContainingIgnoreCase(searchTerm))
                .thenReturn(Arrays.asList());

        // Act
        List<UserType> result = findUserTypeByNameContainingIgnoreCaseUseCase.execute(searchTerm);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).isEmpty();
        verify(userTypeGateway, times(1)).findByNameContainingIgnoreCase(searchTerm);
    }

    @Test
    @DisplayName("Should find all user types when search term is empty")
    void shouldFindAllUserTypesWhenSearchTermIsEmpty() {
        // Arrange
        String searchTerm = "";
        List<UserType> allUserTypes = Arrays.asList(clientUserType, restaurantUserType, adminUserType);
        when(userTypeGateway.findByNameContainingIgnoreCase(searchTerm))
                .thenReturn(allUserTypes);

        // Act
        List<UserType> result = findUserTypeByNameContainingIgnoreCaseUseCase.execute(searchTerm);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(3);
        verify(userTypeGateway, times(1)).findByNameContainingIgnoreCase(searchTerm);
    }

    @Test
    @DisplayName("Should handle case insensitive search correctly")
    void shouldHandleCaseInsensitiveSearch() {
        // Arrange
        String searchTerm = "cliente";
        List<UserType> expectedList = Arrays.asList(clientUserType);
        when(userTypeGateway.findByNameContainingIgnoreCase(searchTerm))
                .thenReturn(expectedList);

        // Act
        List<UserType> result = findUserTypeByNameContainingIgnoreCaseUseCase.execute(searchTerm);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.get(0).getName()).isEqualTo("CLIENTE");
        verify(userTypeGateway, times(1)).findByNameContainingIgnoreCase(searchTerm);
    }
}
