package com.fiap.techchallenge.application.usecases.menuItem;

import com.fiap.techchallenge.application.gateways.menuItem.MenuItemGateway;
import com.fiap.techchallenge.domain.entities.MenuItem;
import com.fiap.techchallenge.infrastructure.exception.BusinessRuleException;
import com.fiap.techchallenge.infrastructure.exception.ResourceNotFoundException;

public class UpdateMenuItemUseCase {

    private final MenuItemGateway menuItemGateway;

    public UpdateMenuItemUseCase(MenuItemGateway menuItemGateway) {
        this.menuItemGateway = menuItemGateway;
    }

    public MenuItem execute(Long id, MenuItem updates) {
        MenuItem existingItem = menuItemGateway.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item do cardápio não encontrado com ID: " + id));

        if (!existingItem.getName().equals(updates.getName()) &&
                menuItemGateway.existsByNameAndRestaurantId(updates.getName(), existingItem.getRestaurantId())) {
            throw new BusinessRuleException("Já existe um item com este nome cadastrado para este restaurante.");
        }

        existingItem.setName(updates.getName());
        existingItem.setDescription(updates.getDescription());
        existingItem.setPrice(updates.getPrice());
        existingItem.setOnlyOnSite(updates.isOnlyOnSite());
        existingItem.setPhotoPath(updates.getPhotoPath());

        return menuItemGateway.save(existingItem);
    }
}
