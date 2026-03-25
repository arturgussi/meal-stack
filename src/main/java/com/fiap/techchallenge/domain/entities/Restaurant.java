package com.fiap.techchallenge.domain.entities;

import java.time.LocalDateTime;

/**
 * Entidade que representa um restaurante no sistema.
 */
public class Restaurant {

    private Long id;
    private String name;
    private String cuisineType;
    private String operatingHours;
    private String streetAddress;
    private Integer numberAddress;
    private String cityAddress;
    private String cepAddress;
    /** ID do usuário que é dono deste restaurante */
    private Long ownerId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Restaurant() {
    }

    public Restaurant(Long id, String name, String cuisineType, String operatingHours, String streetAddress, Integer numberAddress, String cityAddress, String cepAddress, Long ownerId, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.cuisineType = cuisineType;
        this.operatingHours = operatingHours;
        this.streetAddress = streetAddress;
        this.numberAddress = numberAddress;
        this.cityAddress = cityAddress;
        this.cepAddress = cepAddress;
        this.ownerId = ownerId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Restaurant(Long id, String name, String cuisineType, String operatingHours, String streetAddress, Integer numberAddress, String cityAddress, String cepAddress, Long ownerId) {
        this.id = id;
        this.name = name;
        this.cuisineType = cuisineType;
        this.operatingHours = operatingHours;
        this.streetAddress = streetAddress;
        this.numberAddress = numberAddress;
        this.cityAddress = cityAddress;
        this.cepAddress = cepAddress;
        this.ownerId = ownerId;
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

    public String getCuisineType() {
        return cuisineType;
    }

    public void setCuisineType(String cuisineType) {
        this.cuisineType = cuisineType;
    }

    public String getOperatingHours() {
        return operatingHours;
    }

    public void setOperatingHours(String operatingHours) {
        this.operatingHours = operatingHours;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public Integer getNumberAddress() {
        return numberAddress;
    }

    public void setNumberAddress(Integer numberAddress) {
        this.numberAddress = numberAddress;
    }

    public String getCityAddress() {
        return cityAddress;
    }

    public void setCityAddress(String cityAddress) {
        this.cityAddress = cityAddress;
    }

    public String getCepAddress() {
        return cepAddress;
    }

    public void setCepAddress(String cepAddress) {
        this.cepAddress = cepAddress;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Restaurant other)) return false;
        return java.util.Objects.equals(id, other.id) &&
               java.util.Objects.equals(name, other.name);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cuisineType='" + cuisineType + '\'' +
                ", operatingHours='" + operatingHours + '\'' +
                ", cityAddress='" + cityAddress + '\'' +
                ", ownerId=" + ownerId +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
