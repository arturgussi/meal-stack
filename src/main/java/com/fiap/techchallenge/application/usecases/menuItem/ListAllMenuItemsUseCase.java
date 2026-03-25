package com.fiap.techchallenge.application.usecases.menuItem;

import com.fiap.techchallenge.application.gateways.menuItem.MenuItemGateway;
import com.fiap.techchallenge.domain.entities.MenuItem;
import java.util.List;

public class ListAllMenuItemsUseCase {

    private final MenuItemGateway menuItemGateway;

    public ListAllMenuItemsUseCase(MenuItemGateway menuItemGateway) {
        this.menuItemGateway = menuItemGateway;
    }

    public List<MenuItem> execute(Long restaurantId) {
        return menuItemGateway.findAllByRestaurantId(restaurantId);
    }
}
