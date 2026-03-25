package com.fiap.techchallenge.application.usecases.restaurante;

import com.fiap.techchallenge.application.gateways.restaurant.RestaurantGateway;
import com.fiap.techchallenge.infrastructure.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteRestaurantUseCaseTest {

    @Mock
    private RestaurantGateway restaurantGateway;

    @InjectMocks
    private DeleteRestaurantUseCase deleteRestaurantUseCase;

    @Test
    void shouldDeleteRestaurantSuccessfully() {
        Long restaurantId = 1L;
        when(restaurantGateway.existsById(restaurantId)).thenReturn(true);
        doNothing().when(restaurantGateway).deleteById(restaurantId);
        deleteRestaurantUseCase.execute(restaurantId);
        verify(restaurantGateway, times(1)).existsById(restaurantId);
        verify(restaurantGateway, times(1)).deleteById(restaurantId);
    }

    @Test
    void shouldThrowResourceNotFoundExceptionWhenRestaurantDoesNotExist() {
        Long restaurantId = 99L;
        when(restaurantGateway.existsById(restaurantId)).thenReturn(false);
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, 
                () -> deleteRestaurantUseCase.execute(restaurantId));

        assertThat(exception.getMessage()).contains("Restaurante não encontrado com ID: " + restaurantId);
        verify(restaurantGateway, times(1)).existsById(restaurantId);
        verify(restaurantGateway, never()).deleteById(any());
    }
}
