package com.fiap.techchallenge.infrastructure.persistence.repository;

import com.fiap.techchallenge.domain.entities.UserType;
import com.fiap.techchallenge.infrastructure.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepositoryJPA extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByLogin(String login);

    Optional<UserEntity> findByCpf(String cpf);

    List<UserEntity> findByNameContainingIgnoreCase(String nome);

    @Query("SELECT u FROM UserEntity u WHERE u.userType.id = :#{#userType.id}")
    List<UserEntity> findByUserType(@Param("userType") UserType userType);

    boolean existsByEmail(String email);

    boolean existsByLogin(String login);

    boolean existsByCpf(String cpf);
}
