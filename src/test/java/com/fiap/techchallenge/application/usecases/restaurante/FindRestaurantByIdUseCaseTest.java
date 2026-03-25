package com.fiap.techchallenge.application.usecases.restaurante;

import com.fiap.techchallenge.application.gateways.restaurant.RestaurantGateway;
import com.fiap.techchallenge.domain.entities.Restaurant;
import com.fiap.techchallenge.infrastructure.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FindRestaurantByIdUseCaseTest {

    @Mock
    private RestaurantGateway restaurantGateway;

    @InjectMocks
    private FindRestaurantByIdUseCase findRestaurantByIdUseCase;

    @Test
    void shouldFindRestaurantSuccessfully() {
        Long restaurantId = 1L;
        Restaurant restaurant = new Restaurant(restaurantId, "Tech Burger", "Fast Food", "08:00-22:00", "Rua A", 123, "SP", "01000000", 1L);

        when(restaurantGateway.findById(restaurantId)).thenReturn(Optional.of(restaurant));
        Restaurant result = findRestaurantByIdUseCase.execute(restaurantId);
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(restaurantId);
        verify(restaurantGateway, times(1)).findById(restaurantId);
    }

    @Test
    void shouldThrowResourceNotFoundExceptionWhenRestaurantDoesNotExist() {
        Long restaurantId = 99L;
        when(restaurantGateway.findById(restaurantId)).thenReturn(Optional.empty());
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, 
                () -> findRestaurantByIdUseCase.execute(restaurantId));

        assertThat(exception.getMessage()).contains("Restaurante não encontrado com ID: " + restaurantId);
        verify(restaurantGateway, times(1)).findById(restaurantId);
    }
}
