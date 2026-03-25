package com.fiap.techchallenge.application.dto.menuItem;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

public class MenuItemRequestDTO {

    @NotBlank(message = "O nome do item é obrigatório")
    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres")
    private String name;

    @NotBlank(message = "A descrição é obrigatória")
    @Size(max = 255, message = "A descrição deve ter no máximo 255 caracteres")
    private String description;

    @NotNull(message = "O preço é obrigatório")
    @Positive(message = "O preço deve ser positivo")
    private BigDecimal price;

    private boolean onlyOnSite;

    private String photoPath;

    @NotNull(message = "O ID do restaurante é obrigatório")
    private Long restaurantId;

    public MenuItemRequestDTO() {
    }

    public MenuItemRequestDTO(String name, String description, BigDecimal price, boolean onlyOnSite, String photoPath, Long restaurantId) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.onlyOnSite = onlyOnSite;
        this.photoPath = photoPath;
        this.restaurantId = restaurantId;
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
}
