package com.fiap.techchallenge.infrastructure.persistence.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_restaurante")
public class RestaurantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_restaurante")
    private Long id;

    @Column(name = "nm_restaurante")
    private String name;
    @Column(name = "ds_tipo_cozinha")
    private String cuisineType;
    @Column(name = "ds_horario_funcionamento")
    private String operatingHours;
    @Column(name = "ds_endereco_rua")
    private String streetAddress;
    @Column(name = "nr_endereco_numero")
    private Integer numberAddress;
    @Column(name = "ds_endereco_cidade")
    private String cityAddress;
    @Column(name = "nr_endereco_cep")
    private String cepAddress;
    @Column(name = "id_dono")
    private Long ownerId;

    @Column(name = "dt_criacao", insertable = false, updatable = false)
    private LocalDateTime createdAt;
    @Column(name = "dt_atualizacao", insertable = false, updatable = false)
    private LocalDateTime updatedAt;

    public RestaurantEntity() {
    }

    public RestaurantEntity(Long id, String name, String cuisineType, String operatingHours, String streetAddress, Integer numberAddress, String cityAddress, String cepAddress, Long ownerId, LocalDateTime createdAt, LocalDateTime updatedAt) {
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

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
