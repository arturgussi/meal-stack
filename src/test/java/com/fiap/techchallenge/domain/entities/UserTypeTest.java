package com.fiap.techchallenge.domain.entities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserTypeTest {

    @Test
    @DisplayName("Should create UserType with valid values")
    void shouldCreateUserTypeWithValidValues() {
        Long id = 1L;
        String name = "CLIENTE";

        UserType userType = new UserType(id, name);
        assertThat(userType).isNotNull();
        assertThat(userType.getId()).isEqualTo(id);
        assertThat(userType.getName()).isEqualTo(name);
    }

    @Test
    @DisplayName("Should create UserType with default constructor")
    void shouldCreateUserTypeWithDefaultConstructor() {
        UserType userType = new UserType();
        assertThat(userType).isNotNull();
        assertThat(userType.getId()).isNull();
        assertThat(userType.getName()).isNull();
    }

    @Test
    @DisplayName("Should set and get name correctly")
    void shouldSetAndGetNameCorrectly() {
        UserType userType = new UserType();
        String name = "ADMIN";
        userType.setName(name);
        assertThat(userType.getName()).isEqualTo(name);
    }

    @Test
    @DisplayName("Should set and get ID correctly")
    void shouldSetAndGetIdCorrectly() {
        UserType userType = new UserType();
        Long id = 2L;
        userType.setId(id);
        assertThat(userType.getId()).isEqualTo(id);
    }

    @Test
    @DisplayName("Should have proper equals and hashCode")
    void shouldHaveProperEqualsAndHashCode() {
        UserType userType1 = new UserType(1L, "CLIENTE");
        UserType userType2 = new UserType(1L, "CLIENTE");
        UserType userType3 = new UserType(2L, "ADMIN");
        assertThat(userType1).isEqualTo(userType2);
        assertThat(userType1).hasSameHashCodeAs(userType2);
        assertThat(userType1).isNotEqualTo(userType3);
    }

    @Test
    @DisplayName("Should have proper toString")
    void shouldHaveProperToString() {
        UserType userType = new UserType(1L, "CLIENTE");
        String toString = userType.toString();
        assertThat(toString).contains("UserType");
        assertThat(toString).contains("id=1");
        assertThat(toString).contains("name=CLIENTE");
    }
}
