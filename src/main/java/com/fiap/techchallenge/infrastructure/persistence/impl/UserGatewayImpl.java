package com.fiap.techchallenge.infrastructure.persistence.impl;

import com.fiap.techchallenge.application.gateways.user.UserGateway;
import com.fiap.techchallenge.domain.entities.User;
import com.fiap.techchallenge.domain.enums.TipoUsuario;
import com.fiap.techchallenge.infrastructure.persistence.entity.UserEntity;
import com.fiap.techchallenge.infrastructure.persistence.repository.UserRepositoryJPA;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class UserGatewayImpl implements UserGateway {

    private final UserRepositoryJPA repository;

    public UserGatewayImpl(UserRepositoryJPA repository) {
        this.repository = repository;
    }

    /**
     * Salva um novo usuário ou atualiza um existente.
     *
     * @param user Usuário a ser salvo
     * @return Usuário salvo com ID gerado (se novo)
     */
    @Override
    public User save(User user) {
        UserEntity entity = toEntity(user);

        UserEntity saveEntity = repository.save(entity);

        return toDomain(saveEntity);
    }

    /**
     * Busca um usuário pelo ID.
     *
     * @param id ID do usuário
     * @return Optional com o usuário se encontrado
     */
    @Override
    public Optional<User> findById(Long id) {
        return repository.findById(id).map(this::toDomain);
    }

    /**
     * Retorna todos os usuários.
     *
     * @return Lista de todos os usuários
     */
    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        for (UserEntity userEntity : repository.findAll()) {
            users.add(toDomain(userEntity));
        }
        return users;
    }

    /**
     * Busca um usuário pelo email.
     *
     * @param email Email do usuário
     * @return Optional com o usuário se encontrado
     */
    @Override
    public Optional<User> findByEmail(String email) {
        return repository.findByEmail(email).map(this::toDomain);
    }

    /**
     * Busca um usuário pelo login.
     *
     * @param login Login do usuário
     * @return Optional com o usuário se encontrado
     */
    @Override
    public Optional<User> findByLogin(String login) {
        return repository.findByLogin(login).map(this::toDomain);
    }

    /**
     * Busca usuários cujo nome contém a string especificada (case-insensitive).
     *
     * @param name Parte do nome do usuário
     * @return Lista de usuários encontrados
     */
    @Override
    public List<User> findByNameContainingIgnoreCase(String name) {
        List<User> users = new ArrayList<>();
        for (UserEntity userEntity : repository.findByNameContainingIgnoreCase(name)) {
            users.add(toDomain(userEntity));
        }
        return users;
    }

    /**
     * Busca um usuário pelo CPF.
     *
     * @param cpf CPF do usuário
     * @return Optional com o usuário se encontrado
     */
    @Override
    public Optional<User> findByCpf(String cpf) {
        return repository.findByCpf(cpf).map(this::toDomain);
    }

    /**
     * Busca usuários por tipo.
     *
     * @param userType Tipo do usuário
     * @return Lista de usuários do tipo especificado
     */
    @Override
    public List<User> findByUserType(TipoUsuario userType) {
        List<User> users = new ArrayList<>();
        for (UserEntity userEntity : repository.findByUserType(userType)) {
            users.add(toDomain(userEntity));
        }
        return users;
    }

    /**
     * Verifica se existe um usuário com o email especificado.
     *
     * @param email Email a verificar
     * @return true se existir, false caso contrário
     */
    @Override
    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }

    /**
     * Verifica se existe um usuário com o login especificado.
     *
     * @param login Login a verificar
     * @return true se existir, false caso contrário
     */
    @Override
    public boolean existsByLogin(String login) {
        return repository.existsByLogin(login);
    }

    /**
     * Verifica se existe um usuário com o CPF especificado.
     *
     * @param cpf CPF a verificar
     * @return true se existir, false caso contrário
     */
    @Override
    public boolean existsByCpf(String cpf) {
        return repository.existsByCpf(cpf);
    }

    /**
     * Verifica se existe um usuário com o ID especificado.
     *
     * @param id ID a verificar
     * @return true se existir, false caso contrário
     */
    @Override
    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

    /**
     * Exclui um usuário pelo ID.
     *
     * @param id ID do usuário a excluir
     */
    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    /**
     * @param user domain
     * @return UserEntity User
     */
    private UserEntity toEntity(User user) {
        UserEntity entity = new UserEntity();
        entity.setId(user.getId());
        entity.setName(user.getName());
        entity.setEmail(user.getEmail());
        entity.setLogin(user.getLogin());
        entity.setPassword(user.getPassword());
        entity.setCpf(user.getCpf());
        entity.setUserType(user.getUserType());
        entity.setStreetAddress(user.getStreetAddress());
        entity.setNumberAddress(user.getNumberAddress());
        entity.setCityAddress(user.getCityAddress());
        entity.setCepAddress(user.getCepAddress());
        return entity;
    }

    private User toDomain(UserEntity entity) {
        return new User(
                entity.getId(),
                entity.getName(),
                entity.getEmail(),
                entity.getLogin(),
                entity.getPassword(),
                entity.getCpf(),
                entity.getUserType(),
                entity.getStreetAddress(),
                entity.getNumberAddress(),
                entity.getCityAddress(),
                entity.getCepAddress(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
