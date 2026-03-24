package com.fiap.techchallenge.application.usecases.restaurante;

import com.fiap.techchallenge.application.gateways.restaurant.RestaurantGateway;
import com.fiap.techchallenge.application.gateways.user.UserGateway;
import com.fiap.techchallenge.domain.entities.Restaurant;
import com.fiap.techchallenge.domain.entities.User;
import com.fiap.techchallenge.domain.enums.UserType;
import com.fiap.techchallenge.infrastructure.exception.BusinessRuleException;
import com.fiap.techchallenge.infrastructure.exception.ResourceNotFoundException;

public class CreateRestaurantUseCase {

    private final RestaurantGateway restaurantGateway;
    private final UserGateway userGateway;

    public CreateRestaurantUseCase(RestaurantGateway restaurantGateway, UserGateway userGateway) {
        this.restaurantGateway = restaurantGateway;
        this.userGateway = userGateway;
    }

    public Restaurant execute(Restaurant restaurant) {
        User owner = userGateway.findById(restaurant.getOwnerId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário (Dono) não encontrado com ID: " + restaurant.getOwnerId()));

        if (!owner.getUserType().equals(UserType.DONO_RESTAURANTE)) {
            throw new BusinessRuleException("O usuário informado não tem permissão de Dono de Restaurante.");
        }

        if (restaurantGateway.existsByName(restaurant.getName())) {
            throw new BusinessRuleException("Já existe um restaurante cadastrado com o nome: " + restaurant.getName());
        }

        return restaurantGateway.save(restaurant);
    }
}
