package com.fiap.techchallenge.infrastructure.persistence.impl;

import com.fiap.techchallenge.application.gateways.restaurant.RestaurantGateway;
import com.fiap.techchallenge.domain.entities.Restaurant;
import com.fiap.techchallenge.infrastructure.persistence.entity.RestaurantEntity;
import com.fiap.techchallenge.infrastructure.persistence.repository.RestaurantRepositoryJPA;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class RestaurantGatewayImpl implements RestaurantGateway {

    private final RestaurantRepositoryJPA repository;

    public RestaurantGatewayImpl(RestaurantRepositoryJPA repository) {
        this.repository = repository;
    }

    /**
     * Salva um novo restaurante ou atualiza um existente.
     *
     * @param restaurant Restaurant a ser salvo
     * @return Restaurant salvo com ID gerado (se novo)
     */
    @Override
    public Restaurant save(Restaurant restaurant) {
        RestaurantEntity entity = toEntity(restaurant);

        RestaurantEntity saveEntity = repository.save(entity);

        return toDomain(saveEntity);
    }

    /**
     * Busca um restaurante pelo ID.
     *
     * @param id ID do restaurante
     * @return Optional com o restaurante se encontrado
     */
    @Override
    public Optional<Restaurant> findById(Long id) {
        return repository.findById(id).map(this::toDomain);
    }

    /**
     * Retorna todos os restaurantes.
     *
     * @return Lista de todos os restaurantes
     */
    @Override
    public List<Restaurant> findAll() {
        List<Restaurant> restaurants = new ArrayList<>();
        for (RestaurantEntity restaurantEntity : repository.findAll()) {
            restaurants.add(toDomain(restaurantEntity));
        }
        return restaurants;
    }

    /**
     * Exclui um restaurante pelo ID.
     *
     * @param id ID do restaurante a excluir
     */
    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    /**
     * Verifica se existe um restaurante com o nome especificado.
     * Evita ciar restaurantes com o mesmo nome.
     *
     * @param name Nome a verificar
     * @return true se existir, false caso contrário
     */
    @Override
    public boolean existsByName(String name) {
        return repository.existsByName(name);
    }

    /**
     * Verifica se existe um restaurante com o ID especificado.
     *
     * @param id ID a verificar
     * @return true se existir, false caso contrário
     */
    @Override
    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

    private RestaurantEntity toEntity(Restaurant restaurant) {
        RestaurantEntity entity = new RestaurantEntity();
        entity.setId(restaurant.getId());
        entity.setName(restaurant.getName());
        entity.setCuisineType(restaurant.getCuisineType());
        entity.setOperatingHours(restaurant.getOperatingHours());
        entity.setStreetAddress(restaurant.getStreetAddress());
        entity.setNumberAddress(restaurant.getNumberAddress());
        entity.setCityAddress(restaurant.getCityAddress());
        entity.setCepAddress(restaurant.getCepAddress());
        entity.setOwnerId(restaurant.getOwnerId());
        return entity;
    }

    private Restaurant toDomain(RestaurantEntity entity) {
        return new Restaurant(
                entity.getId(),
                entity.getName(),
                entity.getCuisineType(),
                entity.getOperatingHours(),
                entity.getStreetAddress(),
                entity.getNumberAddress(),
                entity.getCityAddress(),
                entity.getCepAddress(),
                entity.getOwnerId(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
