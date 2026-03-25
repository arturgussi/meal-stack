package com.fiap.techchallenge.infrastructure.persistence.impl;

import com.fiap.techchallenge.domain.entities.Restaurant;
import com.fiap.techchallenge.infrastructure.persistence.repository.RestaurantRepositoryJPA;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(RestaurantGatewayImpl.class)
@ActiveProfiles("test")
class RestaurantGatewayImplTest {

    @Autowired
    private RestaurantGatewayImpl restaurantGateway;

    @Autowired
    private RestaurantRepositoryJPA repository;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
    }

    @Test
    void shouldSaveRestaurantSuccessfully() {
        Restaurant restaurant = new Restaurant(null, "Tech Burger", "Fast Food", "08:00-22:00", "Rua A", 123, "SP",
                "01000000", 1L);

        Restaurant savedRestaurant = restaurantGateway.save(restaurant);

        assertThat(savedRestaurant).isNotNull();
        assertThat(savedRestaurant.getId()).isNotNull();
        assertThat(savedRestaurant.getName()).isEqualTo("Tech Burger");
    }

    @Test
    void shouldFindRestaurantById() {
        Restaurant restaurant = new Restaurant(null, "Tech Pizza", "Italian", "18:00-23:00", "Rua B", 456, "RJ",
                "20000000", 2L);
        Restaurant savedRestaurant = restaurantGateway.save(restaurant);

        Optional<Restaurant> foundRestaurantOptional = restaurantGateway.findById(savedRestaurant.getId());

        assertThat(foundRestaurantOptional).isPresent();
        assertThat(foundRestaurantOptional.get().getName()).isEqualTo("Tech Pizza");
    }

    @Test
    void shouldReturnEmptyWhenFindingNonExistentId() {
        Optional<Restaurant> foundRestaurantOptional = restaurantGateway.findById(999L);

        assertThat(foundRestaurantOptional).isEmpty();
    }

    @Test
    void shouldFindAllRestaurants() {
        Restaurant res1 = new Restaurant(null, "Tech Pizza", "Italian", "18:00-23:00", "Rua B", 456, "RJ", "20000000",
                1L);
        Restaurant res2 = new Restaurant(null, "Tech Burger", "Fast Food", "08:00-22:00", "Rua A", 123, "SP",
                "01000000", 2L);
        restaurantGateway.save(res1);
        restaurantGateway.save(res2);

        List<Restaurant> allRestaurants = restaurantGateway.findAll();

        assertThat(allRestaurants).hasSize(2);
    }

    @Test
    void shouldDeleteRestaurantById() {
        Restaurant restaurant = new Restaurant(null, "Tech Pizza", "Italian", "18:00-23:00", "Rua B", 456, "RJ",
                "20000000", 2L);
        Restaurant savedRestaurant = restaurantGateway.save(restaurant);
        Long id = savedRestaurant.getId();

        assertThat(restaurantGateway.existsById(id)).isTrue();

        restaurantGateway.deleteById(id);

        assertThat(restaurantGateway.existsById(id)).isFalse();
    }

    @Test
    void shouldReturnTrueIfRestaurantExistsByName() {
        Restaurant restaurant = new Restaurant(null, "Unique Name", "Italian", "18:00-23:00", "Rua B", 456, "RJ",
                "20000000", 2L);
        restaurantGateway.save(restaurant);

        boolean exists = restaurantGateway.existsByName("Unique Name");
        assertThat(exists).isTrue();
    }

    @Test
    void shouldReturnFalseIfRestaurantDoesNotExistByName() {
        boolean exists = restaurantGateway.existsByName("Non Existent Name");
        assertThat(exists).isFalse();
    }

    @Test
    void shouldReturnTrueIfRestaurantExistsById() {
        Restaurant restaurant = new Restaurant(null, "Tech Pizza", "Italian", "18:00-23:00", "Rua B", 456, "RJ",
                "20000000", 2L);
        Restaurant savedRestaurant = restaurantGateway.save(restaurant);

        boolean exists = restaurantGateway.existsById(savedRestaurant.getId());

        assertThat(exists).isTrue();
    }

    @Test
    void shouldReturnFalseIfRestaurantDoesNotExistById() {
        boolean exists = restaurantGateway.existsById(999L);
        assertThat(exists).isFalse();
    }
}
