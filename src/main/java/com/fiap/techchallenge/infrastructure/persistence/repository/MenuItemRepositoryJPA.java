package com.fiap.techchallenge.infrastructure.persistence.repository;

import com.fiap.techchallenge.infrastructure.persistence.entity.MenuItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MenuItemRepositoryJPA extends JpaRepository<MenuItemEntity, Long> {
    List<MenuItemEntity> findAllByRestaurantId(Long restaurantId);
    boolean existsByNameAndRestaurantId(String name, Long restaurantId);
}
