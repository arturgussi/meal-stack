package com.fiap.techchallenge.infrastructure.persistence.impl;

import com.fiap.techchallenge.application.gateways.userType.UserTypeGateway;
import com.fiap.techchallenge.domain.entities.UserType;
import com.fiap.techchallenge.infrastructure.persistence.entity.UserTypeEntity;
import com.fiap.techchallenge.infrastructure.persistence.repository.UserTypeRepositoryJPA;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class UserTypeGatewayImpl implements UserTypeGateway {

    private final UserTypeRepositoryJPA repository;

    public UserTypeGatewayImpl(UserTypeRepositoryJPA repository) {
        this.repository = repository;
    }

    /**
     * Salva um novo tipo de usuário.
     *
     * @param userType Tipo de usuário a ser salvo
     * @return Tipo de usuário salvo com ID gerado (se novo)
     */
    @Override
    public UserType save(UserType userType) {
        UserTypeEntity entity = toEntity(userType);
        UserTypeEntity saved = repository.save(entity);
        return toDomain(saved);
    }

    /**
     * Busca um tipo de usuário pelo ID.
     *
     * @param id ID do tipo de usuário
     * @return Optional com o tipo de usuário se encontrado
     */
    @Override
    public Optional<UserType> findById(Long id) {
        return repository.findById(id).map(this::toDomain);
    }

    /**
     * Retorna todos os tipos de usuários.
     *
     * @return Lista de todos os tipos de usuários
     */
    @Override
    public List<UserType> findAll() {
        return repository.findAll().stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    /**
     * Busca um tipo de usuário pelo nome.
     *
     * @param name Nome do tipo de usuário
     * @return Optional com o tipo de usuário se encontrado
     */
    @Override
    public Optional<UserType> findByName(String name) {
        return repository.findByName(name).map(this::toDomain);
    }

    /**
     * Busca tipos de usuários cujo nome contém a string especificada
     * (case-insensitive).
     *
     * @param name Parte do nome do tipo de usuário
     * @return Lista de tipos de usuários encontrados
     */
    @Override
    public List<UserType> findByNameContainingIgnoreCase(String name) {
        return repository.findByNameContainingIgnoreCase(name).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    /**
     * Verifica se existe um tipo de usuário com o ID especificado.
     * 
     * @param id ID a verificar
     * @return true se existir, false caso contrário
     */
    @Override
    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

    /**
     * Verifica se existe um tipo de usuário com o nome especificado.
     * 
     * @param name Nome do tipo de usuário
     * @return true se existir, false caso contrário
     */
    @Override
    public boolean existsByName(String name) {
        return repository.existsByName(name);
    }

    /**
     * Exclui um tipo de usuário pelo ID.
     *
     * @param id ID do tipo de usuário a excluir
     */
    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    private UserTypeEntity toEntity(UserType domain) {
        if (domain == null) {
            return null;
        }
        return new UserTypeEntity(domain.getId(), domain.getName(), domain.getCreatedAt(), domain.getUpdatedAt());
    }

    private UserType toDomain(UserTypeEntity entity) {
        if (entity == null) {
            return null;
        }
        return new UserType(entity.getId(), entity.getName(), entity.getCreatedAt(), entity.getUpdatedAt());
    }

}
