package com.fiap.techchallenge.application.usecases.menuItem;

import com.fiap.techchallenge.application.gateways.menuItem.MenuItemGateway;
import com.fiap.techchallenge.domain.entities.MenuItem;
import com.fiap.techchallenge.infrastructure.exception.ResourceNotFoundException;

public class FindMenuItemByIdUseCase {

    private final MenuItemGateway menuItemGateway;

    public FindMenuItemByIdUseCase(MenuItemGateway menuItemGateway) {
        this.menuItemGateway = menuItemGateway;
    }

    public MenuItem execute(Long id) {
        return menuItemGateway.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item do cardápio não encontrado com ID: " + id));
    }
}
