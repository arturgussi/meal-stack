package com.fiap.techchallenge.application.gateways.restaurant;

import com.fiap.techchallenge.domain.entities.Restaurant;

import java.util.List;
import java.util.Optional;

public interface RestaurantGateway {
    /**
     * Salva um novo restaurante ou atualiza um existente.
     *
     * @param restaurant Restaurant a ser salvo
     * @return Restaurant salvo com ID gerado (se novo)
     */
    Restaurant save(Restaurant restaurant);

    /**
     * Busca um restaurante pelo ID.
     *
     * @param id ID do restaurante
     * @return Optional com o restaurante se encontrado
     */
    Optional<Restaurant> findById(Long id);

    /**
     * Retorna todos os restaurantes.
     *
     * @return Lista de todos os restaurantes
     */
    List<Restaurant> findAll();

    /**
     * Exclui um restaurante pelo ID.
     *
     * @param id ID do restaurante a excluir
     */
    void deleteById(Long id);

    /**
     * Verifica se existe um restaurante com o nome especificado.
     * Evita ciar restaurantes com o mesmo nome.
     *
     * @param name Nome a verificar
     * @return true se existir, false caso contrário
     */
    boolean existsByName(String name);
}
