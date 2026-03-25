package com.fiap.techchallenge.domain.entities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("MenuItem Domain Entity Tests")
class MenuItemTest {

    @Test
    @DisplayName("Should create MenuItem with all fields")
    void shouldCreateMenuItemWithAllFields() {
        LocalDateTime now = LocalDateTime.now();
        MenuItem item = new MenuItem(
                1L,
                "Burger",
                "Delicious burger",
                new BigDecimal("25.00"),
                true,
                "/path/to/photo.jpg",
                10L,
                now,
                now
        );

        assertEquals(1L, item.getId());
        assertEquals("Burger", item.getName());
        assertEquals("Delicious burger", item.getDescription());
        assertEquals(new BigDecimal("25.00"), item.getPrice());
        assertTrue(item.isOnlyOnSite());
        assertEquals("/path/to/photo.jpg", item.getPhotoPath());
        assertEquals(10L, item.getRestaurantId());
        assertEquals(now, item.getCreatedAt());
        assertEquals(now, item.getUpdatedAt());
    }

    @Test
    @DisplayName("Should verify equality")
    void shouldVerifyEquality() {
        MenuItem item1 = new MenuItem(1L, "A", "D", BigDecimal.TEN, true, "P", 1L);
        MenuItem item2 = new MenuItem(1L, "A", "D", BigDecimal.TEN, true, "P", 1L);
        MenuItem item3 = new MenuItem(2L, "B", "D", BigDecimal.TEN, true, "P", 1L);

        assertEquals(item1, item2);
        assertNotEquals(item1, item3);
        assertNotEquals(item1, null);
        assertNotEquals(item1, "not a menu item");
    }

    @Test
    @DisplayName("Should verify hashCode")
    void shouldVerifyHashCode() {
        MenuItem item1 = new MenuItem(1L, "A", "D", BigDecimal.TEN, true, "P", 1L);
        MenuItem item2 = new MenuItem(1L, "A", "D", BigDecimal.TEN, true, "P", 1L);

        assertEquals(item1.hashCode(), item2.hashCode());
    }

    @Test
    @DisplayName("Should verify toString")
    void shouldVerifyToString() {
        MenuItem item = new MenuItem(1L, "Burger", "Desc", BigDecimal.TEN, true, "P", 1L);
        String toString = item.toString();
        
        assertTrue(toString.contains("Burger"));
        assertTrue(toString.contains("1"));
    }
}
