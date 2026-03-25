package com.fiap.techchallenge.application.gateways.menuItem;

import com.fiap.techchallenge.domain.entities.MenuItem;
import java.util.List;
import java.util.Optional;

public interface MenuItemGateway {
    MenuItem save(MenuItem menuItem);
    Optional<MenuItem> findById(Long id);
    List<MenuItem> findAllByRestaurantId(Long restaurantId);
    boolean existsByNameAndRestaurantId(String name, Long restaurantId);
    void deleteById(Long id);
}
