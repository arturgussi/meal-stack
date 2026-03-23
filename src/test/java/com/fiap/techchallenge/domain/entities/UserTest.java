package com.fiap.techchallenge.domain.entities;

import com.fiap.techchallenge.domain.enums.UserType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("User Domain Entity Tests")
class UserTest {

    @Test
    @DisplayName("Should instantiate a user using the reduced constructor (no dates)")
    void shouldCreateUserWithReducedConstructor() {
        User user = new User(1L, "João Silva", "joao@email.com",
                "joaologin", "senha123", "12345678909", UserType.CLIENTE,
                "Rua A", 123, "São Paulo", "01000000");

        assertNotNull(user);
        assertEquals(1L, user.getId());
        assertEquals("João Silva", user.getName());
        assertEquals("joao@email.com", user.getEmail());
        assertEquals("joaologin", user.getLogin());
        assertEquals("senha123", user.getPassword());
        assertEquals("12345678909", user.getCpf());
        assertEquals(UserType.CLIENTE, user.getUserType());
        assertEquals("Rua A", user.getStreetAddress());
        assertEquals(123, user.getNumberAddress());
        assertEquals("São Paulo", user.getCityAddress());
        assertEquals("01000000", user.getCepAddress());
        assertNull(user.getCreatedAt());
        assertNull(user.getUpdatedAt());
    }

    @Test
    @DisplayName("Should instantiate a user using the full constructor (with dates)")
    void shouldCreateUserWithFullConstructor() {
        LocalDateTime now = LocalDateTime.now();
        User user = new User(1L, "João Silva", "joao@email.com",
                "joaologin", "senha123", "12345678909", UserType.CLIENTE,
                "Rua A", 123, "São Paulo", "01000000", now, now.plusHours(1));

        assertNotNull(user);
        assertEquals(now, user.getCreatedAt());
        assertEquals(now.plusHours(1), user.getUpdatedAt());
    }

    @Test
    @DisplayName("Should validate all getters and setters")
    void shouldValidateGettersAndSetters() {
        User user = new User();
        LocalDateTime now = LocalDateTime.now();

        user.setId(2L);
        user.setName("Maria");
        user.setEmail("maria@email.com");
        user.setLogin("marialogin");
        user.setPassword("secret");
        user.setCpf("98765432100");
        user.setUserType(UserType.DONO_RESTAURANTE);
        user.setStreetAddress("Rua B");
        user.setNumberAddress(456);
        user.setCityAddress("Rio de Janeiro");
        user.setCepAddress("20000000");
        user.setCreatedAt(now);
        user.setUpdatedAt(now);

        assertEquals(2L, user.getId());
        assertEquals("Maria", user.getName());
        assertEquals("maria@email.com", user.getEmail());
        assertEquals("marialogin", user.getLogin());
        assertEquals("secret", user.getPassword());
        assertEquals("98765432100", user.getCpf());
        assertEquals(UserType.DONO_RESTAURANTE, user.getUserType());
        assertEquals("Rua B", user.getStreetAddress());
        assertEquals(456, user.getNumberAddress());
        assertEquals("Rio de Janeiro", user.getCityAddress());
        assertEquals("20000000", user.getCepAddress());
        assertEquals(now, user.getCreatedAt());
        assertEquals(now, user.getUpdatedAt());
    }

    @Test
    @DisplayName("Should validate toString method")
    void shouldValidateToString() {
        User user = new User();
        user.setId(1L);
        user.setName("João");
        user.setEmail("joao@email.com");
        user.setLogin("joao123");
        user.setCpf("123");
        user.setUserType(UserType.CLIENTE);

        String toString = user.toString();

        assertNotNull(toString);
        assertTrue(toString.contains("id=1"));
        assertTrue(toString.contains("name='João'"));
        assertTrue(toString.contains("email='joao@email.com'"));
        assertTrue(toString.contains("login='joao123'"));
        assertTrue(toString.contains("cpf='123'"));
        assertTrue(toString.contains("userType=CLIENTE"));
    }
}
