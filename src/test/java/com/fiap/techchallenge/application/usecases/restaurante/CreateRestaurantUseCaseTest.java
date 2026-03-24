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

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateRestaurantUseCaseTest {

    @Mock
    private RestaurantGateway restaurantGateway;

    @Mock
    private UserGateway userGateway;

    @InjectMocks
    private CreateRestaurantUseCase createRestaurantUseCase;

    @Test
    void shouldCreateRestaurantSuccessfully() {
        UserType userType = new UserType();
        userType.setId(2L);
        Long ownerId = 1L;
        Restaurant restaurantToCreate = new Restaurant(null, "Tech Burger", "Fast Food", "08:00-22:00", "Rua A", 123,
                "SP", "01000000", ownerId);

        User owner = new User();
        owner.setId(ownerId);
        owner.setUserType(userType);

        Restaurant createdRestaurant = new Restaurant(10L, "Tech Burger", "Fast Food", "08:00-22:00", "Rua A", 123,
                "SP", "01000000", ownerId, LocalDateTime.now(), LocalDateTime.now());

        when(userGateway.findById(ownerId)).thenReturn(Optional.of(owner));
        when(restaurantGateway.existsByName(restaurantToCreate.getName())).thenReturn(false);
        when(restaurantGateway.save(restaurantToCreate)).thenReturn(createdRestaurant);
        Restaurant result = createRestaurantUseCase.execute(restaurantToCreate);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(10L);
        assertThat(result.getName()).isEqualTo("Tech Burger");

        verify(userGateway, times(1)).findById(ownerId);
        verify(restaurantGateway, times(1)).existsByName(restaurantToCreate.getName());
        verify(restaurantGateway, times(1)).save(restaurantToCreate);
    }

    @Test
    void shouldThrowResourceNotFoundExceptionWhenOwnerNotFound() {
        Long ownerId = 1L;
        Restaurant restaurantToCreate = new Restaurant(null, "Tech Burger", "Fast Food", "08:00-22:00", "Rua A", 123,
                "SP", "01000000", ownerId);

        when(userGateway.findById(ownerId)).thenReturn(Optional.empty());
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> createRestaurantUseCase.execute(restaurantToCreate));

        assertThat(exception.getMessage()).contains("Usuário (Dono) não encontrado com ID: " + ownerId);

        verify(userGateway, times(1)).findById(ownerId);
        verify(restaurantGateway, never()).existsByName(any());
        verify(restaurantGateway, never()).save(any());
    }

    @Test
    void shouldThrowBusinessRuleExceptionWhenRestaurantNameAlreadyExists() {
        Long ownerId = 1L;
        UserType userType = new UserType();
        userType.setId(2L);
        Restaurant restaurantToCreate = new Restaurant(null, "Tech Burger", "Fast Food", "08:00-22:00", "Rua A", 123,
                "SP", "01000000", ownerId);

        User owner = new User();
        owner.setId(ownerId);
        owner.setUserType(userType);

        when(userGateway.findById(ownerId)).thenReturn(Optional.of(owner));
        when(restaurantGateway.existsByName(restaurantToCreate.getName())).thenReturn(true);

        BusinessRuleException exception = assertThrows(BusinessRuleException.class,
                () -> createRestaurantUseCase.execute(restaurantToCreate));

        assertThat(exception.getMessage())
                .contains("Já existe um restaurante cadastrado com o nome: " + restaurantToCreate.getName());

        verify(userGateway, times(1)).findById(ownerId);
        verify(restaurantGateway, times(1)).existsByName(restaurantToCreate.getName());
        verify(restaurantGateway, never()).save(any());
    }
}
