package com.fiap.techchallenge.application.usecases.menuItem;

import com.fiap.techchallenge.application.gateways.menuItem.MenuItemGateway;
import com.fiap.techchallenge.infrastructure.exception.ResourceNotFoundException;

public class DeleteMenuItemUseCase {

    private final MenuItemGateway menuItemGateway;

    public DeleteMenuItemUseCase(MenuItemGateway menuItemGateway) {
        this.menuItemGateway = menuItemGateway;
    }

    public void execute(Long id) {
        if (menuItemGateway.findById(id).isEmpty()) {
            throw new ResourceNotFoundException("Item do cardápio não encontrado com ID: " + id);
        }
        menuItemGateway.deleteById(id);
    }
}
