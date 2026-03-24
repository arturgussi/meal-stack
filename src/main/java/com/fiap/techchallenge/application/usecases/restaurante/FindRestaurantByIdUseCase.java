package com.fiap.techchallenge.application.usecases.restaurante;

import com.fiap.techchallenge.application.gateways.restaurant.RestaurantGateway;
import com.fiap.techchallenge.domain.entities.Restaurant;
import com.fiap.techchallenge.infrastructure.exception.ResourceNotFoundException;

public class FindRestaurantByIdUseCase {

    private final RestaurantGateway restaurantGateway;

    public FindRestaurantByIdUseCase(RestaurantGateway restaurantGateway) {
        this.restaurantGateway = restaurantGateway;
    }

    public Restaurant execute(Long id) {
        return restaurantGateway.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException( "Restaurante não encontrado com ID: " + id));
    }
}
