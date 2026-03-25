package com.fiap.techchallenge.infrastructure.persistence.impl;

import com.fiap.techchallenge.application.gateways.menuItem.MenuItemGateway;
import com.fiap.techchallenge.domain.entities.MenuItem;
import com.fiap.techchallenge.infrastructure.persistence.entity.MenuItemEntity;
import com.fiap.techchallenge.infrastructure.persistence.repository.MenuItemRepositoryJPA;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class MenuItemGatewayImpl implements MenuItemGateway {

    private final MenuItemRepositoryJPA menuItemRepositoryJPA;

    public MenuItemGatewayImpl(MenuItemRepositoryJPA menuItemRepositoryJPA) {
        this.menuItemRepositoryJPA = menuItemRepositoryJPA;
    }

    @Override
    public MenuItem save(MenuItem menuItem) {
        MenuItemEntity entity = MenuItemEntity.fromDomain(menuItem);
        MenuItemEntity savedEntity = menuItemRepositoryJPA.save(entity);
        return savedEntity.toDomain();
    }

    @Override
    public Optional<MenuItem> findById(Long id) {
        return menuItemRepositoryJPA.findById(id).map(MenuItemEntity::toDomain);
    }

    @Override
    public List<MenuItem> findAllByRestaurantId(Long restaurantId) {
        return menuItemRepositoryJPA.findAllByRestaurantId(restaurantId).stream()
                .map(MenuItemEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsByNameAndRestaurantId(String name, Long restaurantId) {
        return menuItemRepositoryJPA.existsByNameAndRestaurantId(name, restaurantId);
    }

    @Override
    public void deleteById(Long id) {
        menuItemRepositoryJPA.deleteById(id);
    }
}
