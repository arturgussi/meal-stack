package com.fiap.techchallenge.application.gateways.userType;

import java.util.List;
import java.util.Optional;

import com.fiap.techchallenge.domain.entities.UserType;

public interface UserTypeGateway {

    /**
     * Salva um novo tipo de usuário.
     *
     * @param userType Tipo de usuário a ser salvo
     * @return Tipo de usuário salvo com ID gerado (se novo)
     */
    UserType save(UserType userType);

    /**
     * Busca um tipo de usuário pelo ID.
     *
     * @param id ID do tipo de usuário
     * @return Optional com o tipo de usuário se encontrado
     */
    Optional<UserType> findById(Long id);

    /**
     * Retorna todos os tipos de usuários.
     *
     * @return Lista de todos os tipos de usuários
     */
    List<UserType> findAll();

    /**
     * Busca um tipo de usuário pelo nome.
     *
     * @param name Nome do tipo de usuário
     * @return Optional com o tipo de usuário se encontrado
     */
    Optional<UserType> findByName(String name);

    /**
     * Busca tipos de usuários cujo nome contém a string especificada
     * (case-insensitive).
     *
     * @param name Parte do nome do tipo de usuário
     * @return Lista de tipos de usuários encontrados
     */
    List<UserType> findByNameContainingIgnoreCase(String name);

    /**
     * Verifica se existe um tipo de usuário com o ID especificado.
     * 
     * @param id ID a verificar
     * @return true se existir, false caso contrário
     */
    boolean existsById(Long id);

    /**
     * Verifica se existe um tipo de usuário com o nome especificado.
     * 
     * @param name Nome do tipo de usuário
     * @return true se existir, false caso contrário
     */
    boolean existsByName(String name);

    /**
     * Exclui um tipo de usuário pelo ID.
     *
     * @param id ID do tipo de usuário a excluir
     */
    void deleteById(Long id);

}
