package com.fiap.techchallenge.application.usecases.menuItem;

import com.fiap.techchallenge.application.gateways.menuItem.MenuItemGateway;
import com.fiap.techchallenge.application.gateways.restaurant.RestaurantGateway;
import com.fiap.techchallenge.domain.entities.MenuItem;
import com.fiap.techchallenge.infrastructure.exception.BusinessRuleException;
import com.fiap.techchallenge.infrastructure.exception.ResourceNotFoundException;

public class CreateMenuItemUseCase {

    private final MenuItemGateway menuItemGateway;
    private final RestaurantGateway restaurantGateway;

    public CreateMenuItemUseCase(MenuItemGateway menuItemGateway, RestaurantGateway restaurantGateway) {
        this.menuItemGateway = menuItemGateway;
        this.restaurantGateway = restaurantGateway;
    }

    public MenuItem execute(MenuItem menuItem) {
        restaurantGateway.findById(menuItem.getRestaurantId())
                .orElseThrow(() -> new ResourceNotFoundException("Restaurante não encontrado com ID: " + menuItem.getRestaurantId()));

        if (menuItemGateway.existsByNameAndRestaurantId(menuItem.getName(), menuItem.getRestaurantId())) {
            throw new BusinessRuleException("Já existe um item com este nome cadastrado para este restaurante.");
        }

        return menuItemGateway.save(menuItem);
    }
}
