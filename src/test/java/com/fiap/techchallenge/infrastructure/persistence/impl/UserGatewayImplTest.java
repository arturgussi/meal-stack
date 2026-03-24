package com.fiap.techchallenge.infrastructure.persistence.impl;

import com.fiap.techchallenge.domain.entities.User;
import com.fiap.techchallenge.domain.entities.UserType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(UserGatewayImpl.class)
@ActiveProfiles("test")
@DisplayName("User Gateway Implementation Tests")
class UserGatewayImplTest {

    @Autowired
    private UserGatewayImpl userGateway;

    @Test
    @DisplayName("Should save a domain user and retrieve it correctly (Mapping & Persistence)")
    void shouldSaveAndRetrieveUser() {
        UserType userType = new UserType();
        userType.setId(1L);
        User user = new User(null, "João", "joao@email.com", "joao.login", "senha123", "12345678901",
                userType, "Rua A", 1, "SP", "01000000");

        User savedUser = userGateway.save(user);

        assertThat(savedUser.getId()).isNotNull();
        assertThat(savedUser.getName()).isEqualTo("João");

        Optional<User> foundUser = userGateway.findById(savedUser.getId());
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getEmail()).isEqualTo("joao@email.com");
    }

    @Test
    @DisplayName("Should find user by email")
    void shouldFindUserByEmail() {
        UserType userType = new UserType();
        userType.setId(1L);
        User user = new User(null, "João", "joao@email.com", "joao.login", "senha123", "12345678901",
                userType, "Rua A", 1, "SP", "01000000");
        userGateway.save(user);

        Optional<User> foundUser = userGateway.findByEmail("joao@email.com");

        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getName()).isEqualTo("João");
    }

    @Test
    @DisplayName("Should check existence by CPF")
    void shouldCheckExistsByCpf() {
        UserType userType = new UserType();
        userType.setId(1L);
        User user = new User(null, "João", "joao@email.com", "joao.login", "12345678901", "12345678901",
                userType, "Rua A", 1, "SP", "01000000");
        userGateway.save(user);

        assertThat(userGateway.existsByCpf("12345678901")).isTrue();
        assertThat(userGateway.existsByCpf("00000000000")).isFalse();
    }

    @Test
    @DisplayName("Should find users by name containing ignore case")
    void shouldFindByNameContaining() {
        UserType userType = new UserType();
        userType.setId(1L);
        User user1 = new User(null, "João Silva", "joao@email.com", "joao", "123", "1", userType, "A", 1, "SP",
                "1");
        User user2 = new User(null, "Maria Silva", "maria@email.com", "maria", "123", "2", userType, "A", 2,
                "SP", "1");
        userGateway.save(user1);
        userGateway.save(user2);

        List<User> results = userGateway.findByNameContainingIgnoreCase("silva");

        assertThat(results).hasSize(2);
        assertThat(results).extracting(User::getName).containsExactlyInAnyOrder("João Silva", "Maria Silva");
    }

    @Test
    @DisplayName("Should find user by login")
    void shouldFindUserByLogin() {
        UserType userType = new UserType();
        userType.setId(1L);
        User user = new User(null, "João", "joao@email.com", "joao.login", "senha123", "12345678901",
                userType, "Rua A", 1, "SP", "01000000");
        userGateway.save(user);

        Optional<User> foundUser = userGateway.findByLogin("joao.login");

        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getLogin()).isEqualTo("joao.login");
    }

    @Test
    @DisplayName("Should delete user by ID")
    void shouldDeleteById() {
        UserType userType = new UserType();
        userType.setId(1L);
        User user = new User(null, "João", "joao@email.com", "joao.login", "senha123", "12345678901",
                userType, "Rua A", 1, "SP", "01000000");
        User savedUser = userGateway.save(user);

        userGateway.deleteById(savedUser.getId());

        assertThat(userGateway.findById(savedUser.getId())).isEmpty();
    }
}
