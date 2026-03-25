package com.fiap.techchallenge.application.usecases.restaurante;

import com.fiap.techchallenge.application.gateways.restaurant.RestaurantGateway;
import com.fiap.techchallenge.domain.entities.Restaurant;
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
class ListAllRestaurantsUseCaseTest {

    @Mock
    private RestaurantGateway restaurantGateway;

    @InjectMocks
    private ListAllRestaurantsUseCase listAllRestaurantsUseCase;

    @Test
    void shouldReturnEmptyListWhenNoRestaurantsExist() {
        when(restaurantGateway.findAll()).thenReturn(List.of());

        List<Restaurant> result = listAllRestaurantsUseCase.execute();

        assertThat(result).isEmpty();
        verify(restaurantGateway, times(1)).findAll();
    }

    @Test
    void shouldReturnListWithRestaurants() {
        Restaurant res1 = new Restaurant(1L, "Tech Burger", "Fast Food", "08:00-22:00", "Rua A", 123, "SP", "01000000", 1L);
        Restaurant res2 = new Restaurant(2L, "Sushi Bar", "Japanese", "18:00-23:00", "Rua B", 456, "RJ", "20000000", 2L);
        
        when(restaurantGateway.findAll()).thenReturn(Arrays.asList(res1, res2));

        List<Restaurant> result = listAllRestaurantsUseCase.execute();

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getName()).isEqualTo("Tech Burger");
        assertThat(result.get(1).getName()).isEqualTo("Sushi Bar");
        verify(restaurantGateway, times(1)).findAll();
    }
}
