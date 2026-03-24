package com.fiap.techchallenge.application.dto.restaurant;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class RestaurantRequestDTO {

    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 3, max = 100, message = "Nome deve ter entre 3 e 100 caracteres")
    private String name;

    @NotBlank(message = "Tipo de restaurante é obrigatório")
    @Size(min = 3, max = 255, message = "Tipo de restaurante deve ter entre 3 e 255 caracteres")
    private String cuisineType;

    @NotBlank(message = "O horário de funcionamento é obrigatório")
    private String operatingHours;

    private String streetAddress;
    private Integer numberAddress;
    @Size(max = 100, message = "Cidade deve ter no máximo 100 caracteres")
    private String cityAddress;
    @Size(min = 8, max = 8, message = "CEP deve ter 8 dígitos")
    private String cepAddress;

    @NotNull(message = "O ID do dono é obrigatório")
    private Long ownerId;

    public RestaurantRequestDTO() {
    }

    public RestaurantRequestDTO(String name, String cousineType, String operatingHours, String streetAddress, Integer numberAddress, String cityAddress, String cepAddress, Long ownerId) {
        this.name = name;
        this.cuisineType = cousineType;
        this.operatingHours = operatingHours;
        this.streetAddress = streetAddress;
        this.numberAddress = numberAddress;
        this.cityAddress = cityAddress;
        this.cepAddress = cepAddress;
        this.ownerId = ownerId;
    }

    public @NotBlank(message = "Nome é obrigatório") @Size(min = 3, max = 100, message = "Nome deve ter entre 3 e 100 caracteres") String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "Nome é obrigatório") @Size(min = 3, max = 100, message = "Nome deve ter entre 3 e 100 caracteres") String name) {
        this.name = name;
    }

    public @NotBlank(message = "Tipo de restaurante é obrigatório") @Size(min = 3, max = 255, message = "Tipo de restaurante deve ter entre 3 e 255 caracteres") String getCuisineType() {
        return cuisineType;
    }

    public void setCuisineType(@NotBlank(message = "Tipo de restaurante é obrigatório") @Size(min = 3, max = 255, message = "Tipo de restaurante deve ter entre 3 e 255 caracteres") String cuisineType) {
        this.cuisineType = cuisineType;
    }

    public @NotBlank(message = "O horário de funcionamento é obrigatório") String getOperatingHours() {
        return operatingHours;
    }

    public void setOperatingHours(@NotBlank(message = "O horário de funcionamento é obrigatório") String operatingHours) {
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

    public @Size(max = 100, message = "Cidade deve ter no máximo 100 caracteres") String getCityAddress() {
        return cityAddress;
    }

    public void setCityAddress(@Size(max = 100, message = "Cidade deve ter no máximo 100 caracteres") String cityAddress) {
        this.cityAddress = cityAddress;
    }

    public @Size(min = 8, max = 8, message = "CEP deve ter 8 dígitos") String getCepAddress() {
        return cepAddress;
    }

    public void setCepAddress(@Size(min = 8, max = 8, message = "CEP deve ter 8 dígitos") String cepAddress) {
        this.cepAddress = cepAddress;
    }

    public @NotNull(message = "O ID do dono é obrigatório") Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(@NotNull(message = "O ID do dono é obrigatório") Long ownerId) {
        this.ownerId = ownerId;
    }
}
