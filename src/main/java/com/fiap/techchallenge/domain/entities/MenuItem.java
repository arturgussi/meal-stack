package com.fiap.techchallenge.domain.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class MenuItem {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private boolean onlyOnSite;
    private String photoPath;
    private Long restaurantId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public MenuItem() {
    }

    public MenuItem(Long id, String name, String description, BigDecimal price, boolean onlyOnSite, String photoPath, Long restaurantId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.onlyOnSite = onlyOnSite;
        this.photoPath = photoPath;
        this.restaurantId = restaurantId;
    }

    public MenuItem(Long id, String name, String description, BigDecimal price, boolean onlyOnSite, String photoPath, Long restaurantId, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.onlyOnSite = onlyOnSite;
        this.photoPath = photoPath;
        this.restaurantId = restaurantId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public boolean isOnlyOnSite() {
        return onlyOnSite;
    }

    public void setOnlyOnSite(boolean onlyOnSite) {
        this.onlyOnSite = onlyOnSite;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MenuItem other)) return false;
        return Objects.equals(id, other.id) &&
               Objects.equals(name, other.name) &&
               Objects.equals(restaurantId, other.restaurantId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, restaurantId);
    }

    @Override
    public String toString() {
        return "MenuItem{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", onlyOnSite=" + onlyOnSite +
                ", restaurantId=" + restaurantId +
                '}';
    }
}
