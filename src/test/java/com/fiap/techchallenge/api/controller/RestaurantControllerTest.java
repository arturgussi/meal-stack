package com.fiap.techchallenge.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.techchallenge.application.dto.restaurant.RestaurantRequestDTO;
import com.fiap.techchallenge.application.usecases.restaurante.*;
import com.fiap.techchallenge.domain.entities.Restaurant;
import com.fiap.techchallenge.infrastructure.exception.BusinessRuleException;
import com.fiap.techchallenge.infrastructure.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RestaurantController.class)
class RestaurantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());

    @MockitoBean
    private CreateRestaurantUseCase createRestaurantUseCase;

    @MockitoBean
    private DeleteRestaurantUseCase deleteRestaurantUseCase;

    @MockitoBean
    private FindRestaurantByIdUseCase findRestaurantByIdUseCase;

    @MockitoBean
    private ListAllRestaurantsUseCase listAllRestaurantsUseCase;

    @MockitoBean
    private UpdateRestaurantUseCase updateRestaurantUseCase;

    private RestaurantRequestDTO validRequestDTO;
    private Restaurant mockedRestaurant;

    @BeforeEach
    void setUp() {
        validRequestDTO = new RestaurantRequestDTO("Tech Burger", "Fast Food", "08:00-22:00", "Rua A", 123, "SP", "01000000", 1L);
        mockedRestaurant = new Restaurant(1L, "Tech Burger", "Fast Food", "08:00-22:00", "Rua A", 123, "SP", "01000000", 1L, LocalDateTime.now(), LocalDateTime.now());
    }

    @Test
    void shouldCreateRestaurantAndReturn201() throws Exception {
        when(createRestaurantUseCase.execute(any(Restaurant.class))).thenReturn(mockedRestaurant);

        mockMvc.perform(post("/v1/restaurants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(mockedRestaurant.getId()))
                .andExpect(jsonPath("$.name").value(mockedRestaurant.getName()));

        verify(createRestaurantUseCase, times(1)).execute(any(Restaurant.class));
    }

    @Test
    void shouldReturn400WhenCreateRestaurantWithInvalidData() throws Exception {
        RestaurantRequestDTO invalidDTO = new RestaurantRequestDTO("", "Fast Food", "08:00-22:00", "Rua A", 123, "SP", "invalid_cep", 1L);

        mockMvc.perform(post("/v1/restaurants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDTO)))
                .andExpect(status().isBadRequest());

        verify(createRestaurantUseCase, never()).execute(any(Restaurant.class));
    }

    @Test
    void shouldFindRestaurantByIdAndReturn200() throws Exception {
        when(findRestaurantByIdUseCase.execute(1L)).thenReturn(mockedRestaurant);

        mockMvc.perform(get("/v1/restaurants/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(mockedRestaurant.getId()))
                .andExpect(jsonPath("$.name").value(mockedRestaurant.getName()));

        verify(findRestaurantByIdUseCase, times(1)).execute(1L);
    }

    @Test
    void shouldListAllRestaurantsAndReturn200() throws Exception {
        when(listAllRestaurantsUseCase.execute()).thenReturn(List.of(mockedRestaurant));

        mockMvc.perform(get("/v1/restaurants"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(mockedRestaurant.getId()));

        verify(listAllRestaurantsUseCase, times(1)).execute();
    }

    @Test
    void shouldUpdateRestaurantAndReturn200() throws Exception {
        when(updateRestaurantUseCase.execute(eq(1L), any(Restaurant.class))).thenReturn(mockedRestaurant);

        mockMvc.perform(put("/v1/restaurants/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(mockedRestaurant.getId()))
                .andExpect(jsonPath("$.name").value(mockedRestaurant.getName()));

        verify(updateRestaurantUseCase, times(1)).execute(eq(1L), any(Restaurant.class));
    }

    @Test
    void shouldDeleteRestaurantAndReturn204() throws Exception {
        doNothing().when(deleteRestaurantUseCase).execute(1L);

        mockMvc.perform(delete("/v1/restaurants/{id}", 1L))
                .andExpect(status().isNoContent());

        verify(deleteRestaurantUseCase, times(1)).execute(1L);
    }
}
