package com.fiap.techchallenge.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.techchallenge.application.dto.menuItem.MenuItemRequestDTO;
import com.fiap.techchallenge.application.usecases.menuItem.*;
import com.fiap.techchallenge.domain.entities.MenuItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MenuItemController.class)
class MenuItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private CreateMenuItemUseCase createMenuItemUseCase;

    @MockitoBean
    private FindMenuItemByIdUseCase findMenuItemByIdUseCase;

    @MockitoBean
    private ListAllMenuItemsUseCase listAllMenuItemsUseCase;

    @MockitoBean
    private UpdateMenuItemUseCase updateMenuItemUseCase;

    @MockitoBean
    private DeleteMenuItemUseCase deleteMenuItemUseCase;

    private MenuItem menuItem;
    private MenuItemRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        menuItem = new MenuItem(1L, "Pizza", "Delicious", new BigDecimal("50.00"), true, "/images/pizza.jpg", 1L);
        requestDTO = new MenuItemRequestDTO("Pizza", "Delicious", new BigDecimal("50.00"), true, "/images/pizza.jpg", 1L);
    }

    @Test
    @DisplayName("Deve criar um item com sucesso")
    void shouldCreateMenuItem() throws Exception {
        when(createMenuItemUseCase.execute(any(MenuItem.class))).thenReturn(menuItem);

        mockMvc.perform(post("/v1/menu-items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Pizza"));
    }

    @Test
    @DisplayName("Deve buscar um item por ID")
    void shouldFindMenuItemById() throws Exception {
        when(findMenuItemByIdUseCase.execute(1L)).thenReturn(menuItem);

        mockMvc.perform(get("/v1/menu-items/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @DisplayName("Deve listar itens de um restaurante")
    void shouldListItemsByRestaurant() throws Exception {
        when(listAllMenuItemsUseCase.execute(1L)).thenReturn(List.of(menuItem));

        mockMvc.perform(get("/v1/menu-items/restaurant/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Pizza"));
    }

    @Test
    @DisplayName("Deve atualizar um item")
    void shouldUpdateMenuItem() throws Exception {
        when(updateMenuItemUseCase.execute(eq(1L), any(MenuItem.class))).thenReturn(menuItem);

        mockMvc.perform(put("/v1/menu-items/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Pizza"));
    }

    @Test
    @DisplayName("Deve deletar um item")
    void shouldDeleteMenuItem() throws Exception {
        mockMvc.perform(delete("/v1/menu-items/1"))
                .andExpect(status().isNoContent());
    }
}
