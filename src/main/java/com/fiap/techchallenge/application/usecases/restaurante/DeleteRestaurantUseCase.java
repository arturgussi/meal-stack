package com.fiap.techchallenge.application.usecases.restaurante;

import com.fiap.techchallenge.application.gateways.restaurant.RestaurantGateway;
import com.fiap.techchallenge.infrastructure.exception.ResourceNotFoundException;

public class DeleteRestaurantUseCase {

    private final RestaurantGateway restaurantGateway;

    public DeleteRestaurantUseCase(RestaurantGateway restaurantGateway) {
        this.restaurantGateway = restaurantGateway;
    }

    public void execute(Long id) {
        if(!restaurantGateway.existsById(id)) {
            throw new ResourceNotFoundException(
                    "Restaurante não encontrado com ID: " + id);
        }

        restaurantGateway.deleteById(id);
    }
}
