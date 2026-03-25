package com.fiap.techchallenge.application.usecases.restaurante;

import com.fiap.techchallenge.application.gateways.restaurant.RestaurantGateway;
import com.fiap.techchallenge.domain.entities.Restaurant;

import java.util.List;

public class ListAllRestaurantsUseCase {

    private final RestaurantGateway restaurantGateway;

    public ListAllRestaurantsUseCase(RestaurantGateway restaurantGateway) {
        this.restaurantGateway = restaurantGateway;
    }

    public List<Restaurant> execute() {
        return restaurantGateway.findAll();
    }
}
