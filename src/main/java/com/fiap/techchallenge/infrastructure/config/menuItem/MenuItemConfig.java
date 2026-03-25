package com.fiap.techchallenge.infrastructure.config.menuItem;

import com.fiap.techchallenge.application.gateways.menuItem.MenuItemGateway;
import com.fiap.techchallenge.application.gateways.restaurant.RestaurantGateway;
import com.fiap.techchallenge.application.usecases.menuItem.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MenuItemConfig {

    @Bean
    public CreateMenuItemUseCase createMenuItemUseCase(MenuItemGateway menuItemGateway, RestaurantGateway restaurantGateway) {
        return new CreateMenuItemUseCase(menuItemGateway, restaurantGateway);
    }

    @Bean
    public FindMenuItemByIdUseCase findMenuItemByIdUseCase(MenuItemGateway menuItemGateway) {
        return new FindMenuItemByIdUseCase(menuItemGateway);
    }

    @Bean
    public ListAllMenuItemsUseCase listAllMenuItemsUseCase(MenuItemGateway menuItemGateway) {
        return new ListAllMenuItemsUseCase(menuItemGateway);
    }

    @Bean
    public UpdateMenuItemUseCase updateMenuItemUseCase(MenuItemGateway menuItemGateway) {
        return new UpdateMenuItemUseCase(menuItemGateway);
    }

    @Bean
    public DeleteMenuItemUseCase deleteMenuItemUseCase(MenuItemGateway menuItemGateway) {
        return new DeleteMenuItemUseCase(menuItemGateway);
    }
}
