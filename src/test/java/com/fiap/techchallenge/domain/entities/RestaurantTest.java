package com.fiap.techchallenge.domain.entities;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class RestaurantTest {

    @Test
    void shouldCreateRestaurantWithAllArgsConstructor() {
        LocalDateTime now = LocalDateTime.now();
        Restaurant restaurant = new Restaurant(
                1L, 
                "Tech Burger", 
                "Fast Food", 
                "08:00 - 22:00", 
                "Rua A", 
                123, 
                "São Paulo", 
                "01000000", 
                10L, 
                now, 
                now
        );

        assertThat(restaurant.getId()).isEqualTo(1L);
        assertThat(restaurant.getName()).isEqualTo("Tech Burger");
        assertThat(restaurant.getCuisineType()).isEqualTo("Fast Food");
        assertThat(restaurant.getOperatingHours()).isEqualTo("08:00 - 22:00");
        assertThat(restaurant.getStreetAddress()).isEqualTo("Rua A");
        assertThat(restaurant.getNumberAddress()).isEqualTo(123);
        assertThat(restaurant.getCityAddress()).isEqualTo("São Paulo");
        assertThat(restaurant.getCepAddress()).isEqualTo("01000000");
        assertThat(restaurant.getOwnerId()).isEqualTo(10L);
        assertThat(restaurant.getCreatedAt()).isEqualTo(now);
        assertThat(restaurant.getUpdatedAt()).isEqualTo(now);
    }

    @Test
    void shouldCreateRestaurantWithConstructorWithoutAuditDates() {
        Restaurant restaurant = new Restaurant(
                1L, 
                "Tech Burger", 
                "Fast Food", 
                "08:00 - 22:00", 
                "Rua A", 
                123, 
                "São Paulo", 
                "01000000", 
                10L
        );

        assertThat(restaurant.getId()).isEqualTo(1L);
        assertThat(restaurant.getName()).isEqualTo("Tech Burger");
        assertThat(restaurant.getCreatedAt()).isNull();
        assertThat(restaurant.getUpdatedAt()).isNull();
    }

    @Test
    void shouldTestGettersAndSetters() {
        Restaurant restaurant = new Restaurant();
        
        restaurant.setId(2L);
        restaurant.setName("Sushi Bar");
        restaurant.setCuisineType("Japanese");
        restaurant.setOperatingHours("18:00 - 23:00");
        restaurant.setStreetAddress("Rua B");
        restaurant.setNumberAddress(456);
        restaurant.setCityAddress("Rio de Janeiro");
        restaurant.setCepAddress("20000000");
        restaurant.setOwnerId(20L);

        assertThat(restaurant.getId()).isEqualTo(2L);
        assertThat(restaurant.getName()).isEqualTo("Sushi Bar");
        assertThat(restaurant.getCuisineType()).isEqualTo("Japanese");
        assertThat(restaurant.getOperatingHours()).isEqualTo("18:00 - 23:00");
        assertThat(restaurant.getStreetAddress()).isEqualTo("Rua B");
        assertThat(restaurant.getNumberAddress()).isEqualTo(456);
        assertThat(restaurant.getCityAddress()).isEqualTo("Rio de Janeiro");
        assertThat(restaurant.getCepAddress()).isEqualTo("20000000");
        assertThat(restaurant.getOwnerId()).isEqualTo(20L);
    }

    @Test
    void shouldTestEquality() {
        Restaurant r1 = new Restaurant(1L, "A", "C", "H", "S", 1, "C", "CEP", 1L);
        Restaurant r2 = new Restaurant(1L, "A", "C", "H", "S", 1, "C", "CEP", 1L);
        Restaurant r3 = new Restaurant(2L, "B", "C", "H", "S", 1, "C", "CEP", 1L);

        assertThat(r1).isEqualTo(r2);
        assertThat(r1).isNotEqualTo(r3);
        assertThat(r1).isNotEqualTo(null);
        assertThat(r1).isNotEqualTo("not a restaurant");
    }

    @Test
    void shouldTestHashCode() {
        Restaurant r1 = new Restaurant(1L, "A", "C", "H", "S", 1, "C", "CEP", 1L);
        Restaurant r2 = new Restaurant(1L, "A", "C", "H", "S", 1, "C", "CEP", 1L);

        assertThat(r1.hashCode()).isEqualTo(r2.hashCode());
    }

    @Test
    void shouldTestToString() {
        Restaurant r = new Restaurant(1L, "Tech Burger", "C", "H", "S", 1, "C", "CEP", 1L);
        assertThat(r.toString()).contains("Tech Burger").contains("1");
    }
}
