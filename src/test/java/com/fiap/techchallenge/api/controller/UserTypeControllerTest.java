package com.fiap.techchallenge.api.controller;

import com.fiap.techchallenge.application.dto.userType.UserTypeResponseDTO;
import com.fiap.techchallenge.application.usecases.userType.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import com.fiap.techchallenge.domain.entities.UserType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserTypeController.class)
@ActiveProfiles("test")
@DisplayName("UserType Controller Tests")
class UserTypeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CreateUserTypeUseCase createUserTypeUseCase;

    @MockitoBean
    private FindUserTypeByIdUseCase findUserTypeByIdUseCase;

    @MockitoBean
    private FindUserTypeByNameContainingIgnoreCaseUseCase findUserTypeByNameContainingIgnoreCaseUseCase;

    @MockitoBean
    private ListAllUserTypesUseCase listAllUserTypesUseCase;

    @MockitoBean
    private UpdateUserTypeUseCase updateUserTypeUseCase;

    @MockitoBean
    private DeleteUserTypeUseCase deleteUserTypeUseCase;

    private List<UserType> userTypes;

    @BeforeEach
    void setup() {
        UserType client = new UserType(1L, "CLIENTE");
        UserType restaurant = new UserType(2L, "RESTAURANTE");
        userTypes = Arrays.asList(client, restaurant);
    }

    @Test
    @DisplayName("Should return all user types")
    void shouldReturnAllUserTypes() throws Exception {
        when(listAllUserTypesUseCase.execute()).thenReturn(userTypes);

        mockMvc.perform(get("/v2/user-types")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("CLIENTE"))
                .andExpect(jsonPath("$[1].name").value("RESTAURANTE"));
    }
}