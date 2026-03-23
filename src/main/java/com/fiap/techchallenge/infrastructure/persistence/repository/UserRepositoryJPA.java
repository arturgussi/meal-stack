package com.fiap.techchallenge.infrastructure.persistence.repository;

import com.fiap.techchallenge.domain.enums.UserType;
import com.fiap.techchallenge.infrastructure.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepositoryJPA extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByLogin(String login);

    Optional<UserEntity> findByCpf(String cpf);

    List<UserEntity> findByNameContainingIgnoreCase(String nome);

    List<UserEntity> findByUserType(UserType userType);

    boolean existsByEmail(String email);

    boolean existsByLogin(String login);

    boolean existsByCpf(String cpf);
}
