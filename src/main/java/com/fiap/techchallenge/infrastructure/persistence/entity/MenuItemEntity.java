package com.fiap.techchallenge.infrastructure.persistence.entity;

import com.fiap.techchallenge.domain.entities.MenuItem;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_item_cardapio")
public class MenuItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_item_cardapio")
    private Long id;

    @Column(name = "nm_item_cardapio", nullable = false, length = 100)
    private String name;

    @Column(name = "ds_item_cardapio", nullable = false, length = 255)
    private String description;

    @Column(name = "vl_preco", nullable = false, precision = 19, scale = 2)
    private BigDecimal price;

    @Column(name = "bl_apenas_no_restaurante", nullable = false)
    private boolean onlyOnSite;

    @Column(name = "ds_caminho_foto")
    private String photoPath;

    @Column(name = "id_restaurante", nullable = false)
    private Long restaurantId;

    @org.hibernate.annotations.Generated(event = org.hibernate.generator.EventType.INSERT)
    @Column(name = "dt_criacao", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @org.hibernate.annotations.Generated(event = {org.hibernate.generator.EventType.INSERT, org.hibernate.generator.EventType.UPDATE})
    @Column(name = "dt_atualizacao", insertable = false, updatable = false)
    private LocalDateTime updatedAt;

    public MenuItemEntity() {
    }

    public MenuItemEntity(Long id, String name, String description, BigDecimal price, boolean onlyOnSite, String photoPath, Long restaurantId, LocalDateTime createdAt, LocalDateTime updatedAt) {
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

    public static MenuItemEntity fromDomain(MenuItem domain) {
        return new MenuItemEntity(
                domain.getId(),
                domain.getName(),
                domain.getDescription(),
                domain.getPrice(),
                domain.isOnlyOnSite(),
                domain.getPhotoPath(),
                domain.getRestaurantId(),
                domain.getCreatedAt(),
                domain.getUpdatedAt()
        );
    }

    public MenuItem toDomain() {
        return new MenuItem(
                this.id,
                this.name,
                this.description,
                this.price,
                this.onlyOnSite,
                this.photoPath,
                this.restaurantId,
                this.createdAt,
                this.updatedAt
        );
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public boolean isOnlyOnSite() { return onlyOnSite; }
    public void setOnlyOnSite(boolean onlyOnSite) { this.onlyOnSite = onlyOnSite; }
    public String getPhotoPath() { return photoPath; }
    public void setPhotoPath(String photoPath) { this.photoPath = photoPath; }
    public Long getRestaurantId() { return restaurantId; }
    public void setRestaurantId(Long restaurantId) { this.restaurantId = restaurantId; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
