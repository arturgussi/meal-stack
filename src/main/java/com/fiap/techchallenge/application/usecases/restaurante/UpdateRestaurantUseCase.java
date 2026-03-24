package com.fiap.techchallenge.application.usecases.restaurante;

import com.fiap.techchallenge.application.gateways.restaurant.RestaurantGateway;
import com.fiap.techchallenge.application.gateways.user.UserGateway;
import com.fiap.techchallenge.domain.entities.Restaurant;
import com.fiap.techchallenge.domain.entities.User;
import com.fiap.techchallenge.infrastructure.exception.BusinessRuleException;
import com.fiap.techchallenge.infrastructure.exception.ResourceNotFoundException;

public class UpdateRestaurantUseCase {

    private final UserGateway userGateway;
    private final RestaurantGateway restaurantGateway;

    public UpdateRestaurantUseCase(RestaurantGateway restaurantGateway, UserGateway userGateway) {
        this.userGateway = userGateway;
        this.restaurantGateway = restaurantGateway;
    }

    public Restaurant execute(Long id, Restaurant restaurantUpdates) {
        Restaurant existingRestaurant = restaurantGateway.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurante não encontrado com ID: " + id));

        User owner = userGateway.findById(restaurantUpdates.getOwnerId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Usuário (Dono) não encontrado com ID: " + restaurantUpdates.getOwnerId()));

        if (!owner.isRestaurantOwner()) {
            throw new BusinessRuleException("O usuário informado não tem permissão de Dono de Restaurante.");
        }

        if (!restaurantUpdates.getName().equals(existingRestaurant.getName()) &&
                restaurantGateway.existsByName(restaurantUpdates.getName())) {
            throw new BusinessRuleException(
                    "Já existe um restaurante cadastrado com o nome: " + restaurantUpdates.getName());
        }

        existingRestaurant.setName(restaurantUpdates.getName());
        existingRestaurant.setCuisineType(restaurantUpdates.getCuisineType());
        existingRestaurant.setOperatingHours(restaurantUpdates.getOperatingHours());
        existingRestaurant.setStreetAddress(restaurantUpdates.getStreetAddress());
        existingRestaurant.setNumberAddress(restaurantUpdates.getNumberAddress());
        existingRestaurant.setCityAddress(restaurantUpdates.getCityAddress());
        existingRestaurant.setCepAddress(restaurantUpdates.getCepAddress());
        existingRestaurant.setOwnerId(restaurantUpdates.getOwnerId());

        return restaurantGateway.save(existingRestaurant);
    }
}
