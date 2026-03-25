package com.fiap.techchallenge.infrastructure.persistence.impl;

import com.fiap.techchallenge.domain.entities.MenuItem;
import com.fiap.techchallenge.infrastructure.persistence.entity.MenuItemEntity;
import com.fiap.techchallenge.infrastructure.persistence.entity.RestaurantEntity;
import com.fiap.techchallenge.infrastructure.persistence.repository.MenuItemRepositoryJPA;
import com.fiap.techchallenge.infrastructure.persistence.repository.RestaurantRepositoryJPA;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(MenuItemGatewayImpl.class)
@ActiveProfiles("test")
class MenuItemGatewayImplTest {

    @Autowired
    private MenuItemGatewayImpl menuItemGateway;

    @Autowired
    private MenuItemRepositoryJPA menuItemRepository;

    @Autowired
    private RestaurantRepositoryJPA restaurantRepository;

    private RestaurantEntity restaurant;

    @BeforeEach
    void setUp() {
        RestaurantEntity rest = new RestaurantEntity();
        rest.setName("Test Restaurant");
        rest.setCuisineType("Italian");
        rest.setOperatingHours("10:00-22:00");
        rest.setOwnerId(1L);
        restaurant = restaurantRepository.save(rest);
    }

    @Test
    @DisplayName("Deve salvar e buscar um item do cardápio")
    void shouldSaveAndFindMenuItem() {
        MenuItem item = new MenuItem(null, "Pizza", "Delicious", new BigDecimal("50.00"), true, "/images/pizza.jpg", restaurant.getId());

        MenuItem saved = menuItemGateway.save(item);

        assertThat(saved.getId()).isNotNull();
        Optional<MenuItem> found = menuItemGateway.findById(saved.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("Pizza");
    }

    @Test
    @DisplayName("Deve listar itens por restaurante")
    void shouldListItemsByRestaurant() {
        MenuItemEntity item1 = new MenuItemEntity(null, "Pizza", "Delicious", new BigDecimal("50.00"), true, "/images/pizza.jpg", restaurant.getId(), null, null);
        MenuItemEntity item2 = new MenuItemEntity(null, "Burger", "Tasty", new BigDecimal("30.00"), false, "/images/burger.jpg", restaurant.getId(), null, null);
        menuItemRepository.saveAll(List.of(item1, item2));

        List<MenuItem> items = menuItemGateway.findAllByRestaurantId(restaurant.getId());

        assertThat(items).hasSize(2);
        assertThat(items).extracting(MenuItem::getName).containsExactlyInAnyOrder("Pizza", "Burger");
    }

    @Test
    @DisplayName("Deve verificar se existe item por nome e restaurante")
    void shouldCheckExistenceByNameAndRestaurant() {
        MenuItemEntity item = new MenuItemEntity(null, "Pizza", "Delicious", new BigDecimal("50.00"), true, "/images/pizza.jpg", restaurant.getId(), null, null);
        menuItemRepository.save(item);

        boolean exists = menuItemGateway.existsByNameAndRestaurantId("Pizza", restaurant.getId());
        boolean notExists = menuItemGateway.existsByNameAndRestaurantId("Sushi", restaurant.getId());

        assertThat(exists).isTrue();
        assertThat(notExists).isFalse();
    }
}
