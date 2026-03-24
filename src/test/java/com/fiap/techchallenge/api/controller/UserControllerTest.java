package com.fiap.techchallenge.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fiap.techchallenge.application.dto.AlterarSenhaDTO;
import com.fiap.techchallenge.application.dto.LoginDTO;
import com.fiap.techchallenge.application.dto.UserRequestDTO;
import com.fiap.techchallenge.application.dto.UserResponseDTO;
import com.fiap.techchallenge.application.usecases.user.*;
import com.fiap.techchallenge.domain.entities.User;
import com.fiap.techchallenge.domain.enums.UserType;
import com.fiap.techchallenge.infrastructure.exception.BusinessRuleException;
import com.fiap.techchallenge.infrastructure.exception.InvalidPasswordException;
import com.fiap.techchallenge.infrastructure.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@DisplayName("User Controller Tests")
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockitoBean
    private CreateUserUseCase createUserUseCase;
    @MockitoBean
    private FindUserByIdUseCase findUserByIdUseCase;
    @MockitoBean
    private UpdateUserUseCase updateUserUseCase;
    @MockitoBean
    private DeleteUserUseCase deleteUserUseCase;
    @MockitoBean
    private AuthenticateUserUseCase authenticateUserUseCase;
    @MockitoBean
    private ChangeUserPasswordUseCase changeUserPasswordUseCase;
    @MockitoBean
    private FindUsersByNameUseCase findUsersByNameUseCase;
    @MockitoBean
    private ListAllUsersUseCase listAllUsersUseCase;

    private UserRequestDTO userRequestDTO;
    private User user;

    @BeforeEach
    void setUp() {
        objectMapper.registerModule(new JavaTimeModule());
        
        userRequestDTO = new UserRequestDTO(
                "João Silva", "joao@email.com", "joao.silva", "senha123", "12345678901",
                UserType.CLIENTE, "Rua A", 100, "São Paulo", "01234567");

        user = new User(1L, "João Silva", "joao@email.com", "joao.silva", "senha123", "12345678901",
                UserType.CLIENTE, "Rua A", 100, "São Paulo", "01234567", LocalDateTime.now(), LocalDateTime.now());
    }

    @Test
    @DisplayName("POST /v1/users - Should return 201 Created on success")
    void shouldCreateUser() throws Exception {
        when(createUserUseCase.execute(any(User.class))).thenReturn(user);

        mockMvc.perform(post("/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRequestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("João Silva"));
    }

    @Test
    @DisplayName("POST /v1/users - Should return 400 Bad Request on validation error")
    void shouldReturn400OnValidationError() throws Exception {
        userRequestDTO.setName(""); // Invalid name

        mockMvc.perform(post("/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRequestDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title").value("Dados inválidos"))
                .andExpect(jsonPath("$.errors.name").exists());
    }

    @Test
    @DisplayName("POST /v1/users - Should return 422 Unprocessable Content on business rule violation")
    void shouldReturn422OnBusinessRuleViolation() throws Exception {
        when(createUserUseCase.execute(any(User.class)))
                .thenThrow(new BusinessRuleException("Email já cadastrado"));

        mockMvc.perform(post("/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRequestDTO)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.detail").value("Email já cadastrado"));
    }

    @Test
    @DisplayName("GET /v1/users/{id} - Should return 200 OK")
    void shouldFindById() throws Exception {
        when(findUserByIdUseCase.execute(1L)).thenReturn(user);

        mockMvc.perform(get("/v1/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @DisplayName("GET /v1/users/{id} - Should return 404 Not Found")
    void shouldReturn404OnNotFound() throws Exception {
        when(findUserByIdUseCase.execute(99L)).thenThrow(new ResourceNotFoundException("Not found"));

        mockMvc.perform(get("/v1/users/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.title").value("Recurso não encontrado"));
    }

    @Test
    @DisplayName("GET /v1/users/name/{name} - Should return 200 OK with list")
    void shouldFindByName() throws Exception {
        when(findUsersByNameUseCase.execute("João")).thenReturn(List.of(user));

        mockMvc.perform(get("/v1/users/name/João"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("João Silva"));
    }

    @Test
    @DisplayName("GET /v1/users - Should return 200 OK with all users")
    void shouldFindAll() throws Exception {
        when(listAllUsersUseCase.execute()).thenReturn(List.of(user));

        mockMvc.perform(get("/v1/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    @DisplayName("PUT /v1/users/{id} - Should return 200 OK on success")
    void shouldUpdateUser() throws Exception {
        when(updateUserUseCase.execute(eq(1L), any(User.class))).thenReturn(user);

        mockMvc.perform(put("/v1/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @DisplayName("PATCH /v1/users/{id}/password - Should return 204 No Content")
    void shouldUpdatePassword() throws Exception {
        AlterarSenhaDTO dto = new AlterarSenhaDTO("old", "newPassword");

        mockMvc.perform(patch("/v1/users/1/password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNoContent());

        verify(changeUserPasswordUseCase).execute(eq(1L), eq("old"), eq("newPassword"));
    }

    @Test
    @DisplayName("PATCH /v1/users/{id}/password - Should return 401 Unauthorized on wrong password")
    void shouldReturn401OnWrongPassword() throws Exception {
        AlterarSenhaDTO dto = new AlterarSenhaDTO("wrong", "newPassword");
        doThrow(new InvalidPasswordException("Senha atual incorreta"))
                .when(changeUserPasswordUseCase).execute(anyLong(), anyString(), anyString());

        mockMvc.perform(patch("/v1/users/1/password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.title").value("Credenciais inválidas"));
    }

    @Test
    @DisplayName("POST /v1/users/login - Should return 200 OK on success")
    void shouldLogin() throws Exception {
        LoginDTO dto = new LoginDTO("joao", "senha");
        when(authenticateUserUseCase.execute("joao", "senha")).thenReturn(user);

        mockMvc.perform(post("/v1/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.login").value("joao.silva"));
    }

    @Test
    @DisplayName("DELETE /v1/users/{id} - Should return 204 No Content")
    void shouldDeleteUser() throws Exception {
        mockMvc.perform(delete("/v1/users/1"))
                .andExpect(status().isNoContent());

        verify(deleteUserUseCase).execute(1L);
    }
}
