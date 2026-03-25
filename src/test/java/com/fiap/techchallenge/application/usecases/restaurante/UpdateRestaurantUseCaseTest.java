package com.fiap.techchallenge.application.usecases.restaurante;

import com.fiap.techchallenge.application.gateways.restaurant.RestaurantGateway;
import com.fiap.techchallenge.application.gateways.user.UserGateway;
import com.fiap.techchallenge.domain.entities.Restaurant;
import com.fiap.techchallenge.domain.entities.User;
import com.fiap.techchallenge.domain.entities.UserType;
import com.fiap.techchallenge.infrastructure.exception.BusinessRuleException;
import com.fiap.techchallenge.infrastructure.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateRestaurantUseCaseTest {

    @Mock
    private RestaurantGateway restaurantGateway;

    @Mock
    private UserGateway userGateway;

    @InjectMocks
    private UpdateRestaurantUseCase updateRestaurantUseCase;

    @Test
    void shouldUpdateRestaurantSuccessfully() {
        UserType userType = new UserType();
        userType.setId(2L);
        Long restaurantId = 10L;
        Long ownerId = 1L;

        Restaurant existingRestaurant = new Restaurant(restaurantId, "Old Name", "Fast Food", "08:00-22:00", "Rua A",
                123, "SP", "01000000", ownerId);
        Restaurant updates = new Restaurant(null, "New Name", "Gourmet", "09:00-23:00", "Rua B", 456, "RJ", "20000000",
                ownerId);

        User owner = new User();
        owner.setId(ownerId);
        owner.setUserType(userType);

        when(restaurantGateway.findById(restaurantId)).thenReturn(Optional.of(existingRestaurant));
        when(userGateway.findById(ownerId)).thenReturn(Optional.of(owner));
        when(restaurantGateway.existsByName("New Name")).thenReturn(false);
        when(restaurantGateway.save(existingRestaurant)).thenReturn(existingRestaurant);

        Restaurant result = updateRestaurantUseCase.execute(restaurantId, updates);

        assertThat(result.getName()).isEqualTo("New Name");
        assertThat(result.getCuisineType()).isEqualTo("Gourmet");
        assertThat(result.getOperatingHours()).isEqualTo("09:00-23:00");
        assertThat(result.getStreetAddress()).isEqualTo("Rua B");
        assertThat(result.getNumberAddress()).isEqualTo(456);
        assertThat(result.getCityAddress()).isEqualTo("RJ");
        assertThat(result.getCepAddress()).isEqualTo("20000000");

        verify(restaurantGateway, times(1)).findById(restaurantId);
        verify(userGateway, times(1)).findById(ownerId);
        verify(restaurantGateway, times(1)).existsByName("New Name");
        verify(restaurantGateway, times(1)).save(existingRestaurant);
    }

    @Test
    void shouldUpdateRestaurantSuccessfullyWhenNameRemainsSame() {
        UserType userType = new UserType();
        userType.setId(2L);
        Long restaurantId = 10L;
        Long ownerId = 1L;

        Restaurant existingRestaurant = new Restaurant(restaurantId, "Same Name", "Fast Food", "08:00-22:00", "Rua A",
                123, "SP", "01000000", ownerId);
        Restaurant updates = new Restaurant(null, "Same Name", "Gourmet", "09:00-23:00", "Rua B", 456, "RJ", "20000000",
                ownerId);

        User owner = new User();
        owner.setId(ownerId);
        owner.setUserType(userType);

        when(restaurantGateway.findById(restaurantId)).thenReturn(Optional.of(existingRestaurant));
        when(userGateway.findById(ownerId)).thenReturn(Optional.of(owner));
        when(restaurantGateway.save(existingRestaurant)).thenReturn(existingRestaurant);

        Restaurant result = updateRestaurantUseCase.execute(restaurantId, updates);

        assertThat(result.getName()).isEqualTo("Same Name");
        assertThat(result.getCuisineType()).isEqualTo("Gourmet");

        verify(restaurantGateway, times(1)).findById(restaurantId);
        verify(userGateway, times(1)).findById(ownerId);
        verify(restaurantGateway, never()).existsByName(anyString());
        verify(restaurantGateway, times(1)).save(existingRestaurant);
    }

    @Test
    void shouldThrowResourceNotFoundExceptionWhenRestaurantNotFound() {
        Long restaurantId = 10L;
        Long ownerId = 1L;
        Restaurant updates = new Restaurant(null, "New Name", "Gourmet", "09:00-23:00", "Rua B", 456, "RJ", "20000000",
                ownerId);

        when(restaurantGateway.findById(restaurantId)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> updateRestaurantUseCase.execute(restaurantId, updates));

        assertThat(exception.getMessage()).contains("Restaurante não encontrado com ID: " + restaurantId);

        verify(restaurantGateway, times(1)).findById(restaurantId);
        verify(userGateway, never()).findById(any());
        verify(restaurantGateway, never()).save(any());
    }

    @Test
    void shouldThrowResourceNotFoundExceptionWhenOwnerNotFound() {
        Long restaurantId = 10L;
        Long ownerId = 1L;
        Restaurant existingRestaurant = new Restaurant(restaurantId, "Old Name", "Fast Food", "08:00-22:00", "Rua A",
                123, "SP", "01000000", ownerId);
        Restaurant updates = new Restaurant(null, "New Name", "Gourmet", "09:00-23:00", "Rua B", 456, "RJ", "20000000",
                ownerId);

        when(restaurantGateway.findById(restaurantId)).thenReturn(Optional.of(existingRestaurant));
        when(userGateway.findById(ownerId)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> updateRestaurantUseCase.execute(restaurantId, updates));

        assertThat(exception.getMessage()).contains("Usuário (Dono) não encontrado com ID: " + ownerId);
    }

    @Test
    void shouldThrowBusinessRuleExceptionWhenNameAlreadyExists() {
        UserType userType = new UserType();
        userType.setId(2L);
        Long restaurantId = 10L;
        Long ownerId = 1L;

        Restaurant existingRestaurant = new Restaurant(restaurantId, "Old Name", "Fast Food", "08:00-22:00", "Rua A",
                123, "SP", "01000000", ownerId);
        Restaurant updates = new Restaurant(null, "Existing Name", "Gourmet", "09:00-23:00", "Rua B", 456, "RJ",
                "20000000", ownerId);

        User owner = new User();
        owner.setId(ownerId);
        owner.setUserType(userType);

        when(restaurantGateway.findById(restaurantId)).thenReturn(Optional.of(existingRestaurant));
        when(userGateway.findById(ownerId)).thenReturn(Optional.of(owner));
        when(restaurantGateway.existsByName("Existing Name")).thenReturn(true);

        BusinessRuleException exception = assertThrows(BusinessRuleException.class,
                () -> updateRestaurantUseCase.execute(restaurantId, updates));

        assertThat(exception.getMessage()).contains("Já existe um restaurante cadastrado com o nome: Existing Name");

        verify(restaurantGateway, never()).save(any());
    }
}
