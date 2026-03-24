package com.fiap.techchallenge.infrastructure.persistence.repository;

import com.fiap.techchallenge.infrastructure.persistence.entity.UserTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserTypeRepositoryJPA extends JpaRepository<UserTypeEntity, Long> {

    Optional<UserTypeEntity> findByName(String name);

    boolean existsByName(String name);

    List<UserTypeEntity> findByNameContainingIgnoreCase(String name);

}
