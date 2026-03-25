package com.fiap.techchallenge.application.dto.restaurant;

import java.time.LocalDateTime;

public class RestaurantResponseDTO {

    private Long id;
    private String name;
    private String cuisineType;
    private String operatingHours;
    private String streetAddress;
    private Integer numberAddress;
    private String cityAddress;
    private String cepAddress;
    private Long ownerId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public RestaurantResponseDTO() {
    }

    public RestaurantResponseDTO(Long id, String name, String cuisineType, String operatingHours, String streetAddress, Integer numberAddress, String cityAddress, String cepAddress, Long ownerId, LocalDateTime createdAt, LocalDateTime updatedAt) {
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

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
