package com.fiap.techchallenge.infrastructure.persistence.impl;

import com.fiap.techchallenge.domain.entities.UserType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(UserTypeGatewayImpl.class)
@ActiveProfiles("test")
@DisplayName("UserTypeGatewayImpl Tests")
class UserTypeGatewayImplTest {

    @Autowired
    private UserTypeGatewayImpl userTypeGateway;

    @Test
    @DisplayName("Should save a user type and retrieve it")
    void shouldSaveAndRetrieveUserType() {
        UserType userType = new UserType();
        userType.setName("CLIENTE");

        UserType savedUserType = userTypeGateway.save(userType);

        assertThat(savedUserType.getId()).isNotNull();
        assertThat(savedUserType.getName()).isEqualTo("CLIENTE");

        Optional<UserType> foundUserType = userTypeGateway.findById(savedUserType.getId());
        assertThat(foundUserType).isPresent();
        assertThat(foundUserType.get().getName()).isEqualTo("CLIENTE");
    }

    @Test
    @DisplayName("Should find user type by name")
    void shouldFindUserTypeByName() {
        UserType userType = new UserType();
        userType.setName("ADMIN");
        userTypeGateway.save(userType);

        Optional<UserType> foundUserType = userTypeGateway.findByName("ADMIN");

        assertThat(foundUserType).isPresent();
        assertThat(foundUserType.get().getName()).isEqualTo("ADMIN");
    }

    @Test
    @DisplayName("Should check existence by name")
    void shouldCheckExistsByName() {
        UserType userType = new UserType();
        userType.setName("VISITANTE");
        userTypeGateway.save(userType);

        boolean exists = userTypeGateway.existsByName("VISITANTE");

        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("Should find all user types")
    void shouldFindAllUserTypes() {
        UserType userType1 = new UserType();
        userType1.setName("CLIENTE");
        userTypeGateway.save(userType1);

        UserType userType2 = new UserType();
        userType2.setName("ADMIN");
        userTypeGateway.save(userType2);

        var userTypes = userTypeGateway.findAll();

        assertThat(userTypes).hasSizeGreaterThanOrEqualTo(2);
    }

    @Test
    @DisplayName("Should delete user type by ID")
    void shouldDeleteById() {
        UserType userType = new UserType();
        userType.setName("TEMPORARIO");
        UserType savedUserType = userTypeGateway.save(userType);

        userTypeGateway.deleteById(savedUserType.getId());

        assertThat(userTypeGateway.findById(savedUserType.getId())).isEmpty();
    }
}
