package com.fiap.techchallenge.api.controller;

import com.fiap.techchallenge.application.dto.menuItem.MenuItemRequestDTO;
import com.fiap.techchallenge.application.dto.menuItem.MenuItemResponseDTO;
import com.fiap.techchallenge.application.usecases.menuItem.*;
import com.fiap.techchallenge.domain.entities.MenuItem;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/menu-items")
@Tag(name = "Itens do Cardápio", description = "API de gestão de itens do cardápio")
public class MenuItemController {

    private final CreateMenuItemUseCase createMenuItemUseCase;
    private final FindMenuItemByIdUseCase findMenuItemByIdUseCase;
    private final ListAllMenuItemsUseCase listAllMenuItemsUseCase;
    private final UpdateMenuItemUseCase updateMenuItemUseCase;
    private final DeleteMenuItemUseCase deleteMenuItemUseCase;

    public MenuItemController(
            CreateMenuItemUseCase createMenuItemUseCase,
            FindMenuItemByIdUseCase findMenuItemByIdUseCase,
            ListAllMenuItemsUseCase listAllMenuItemsUseCase,
            UpdateMenuItemUseCase updateMenuItemUseCase,
            DeleteMenuItemUseCase deleteMenuItemUseCase) {
        this.createMenuItemUseCase = createMenuItemUseCase;
        this.findMenuItemByIdUseCase = findMenuItemByIdUseCase;
        this.listAllMenuItemsUseCase = listAllMenuItemsUseCase;
        this.updateMenuItemUseCase = updateMenuItemUseCase;
        this.deleteMenuItemUseCase = deleteMenuItemUseCase;
    }

    @PostMapping
    @Operation(summary = "Criar novo item no cardápio")
    public ResponseEntity<MenuItemResponseDTO> create(@Valid @RequestBody MenuItemRequestDTO dto) {
        MenuItem item = toDomain(dto);
        MenuItem saved = createMenuItemUseCase.execute(item);
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponseDTO(saved));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar item por ID")
    public ResponseEntity<MenuItemResponseDTO> findById(@PathVariable Long id) {
        MenuItem item = findMenuItemByIdUseCase.execute(id);
        return ResponseEntity.ok(toResponseDTO(item));
    }

    @GetMapping("/restaurant/{restaurantId}")
    @Operation(summary = "Listar itens de um restaurante")
    public ResponseEntity<List<MenuItemResponseDTO>> listByRestaurant(@PathVariable Long restaurantId) {
        List<MenuItem> items = listAllMenuItemsUseCase.execute(restaurantId);
        List<MenuItemResponseDTO> response = items.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar item")
    public ResponseEntity<MenuItemResponseDTO> update(@PathVariable Long id, @Valid @RequestBody MenuItemRequestDTO dto) {
        MenuItem updates = toDomain(dto);
        MenuItem updated = updateMenuItemUseCase.execute(id, updates);
        return ResponseEntity.ok(toResponseDTO(updated));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover item")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        deleteMenuItemUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }

    private MenuItem toDomain(MenuItemRequestDTO dto) {
        return new MenuItem(null, dto.getName(), dto.getDescription(), dto.getPrice(), dto.isOnlyOnSite(), dto.getPhotoPath(), dto.getRestaurantId());
    }

    private MenuItemResponseDTO toResponseDTO(MenuItem item) {
        return new MenuItemResponseDTO(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.getPrice(),
                item.isOnlyOnSite(),
                item.getPhotoPath(),
                item.getRestaurantId(),
                item.getCreatedAt(),
                item.getUpdatedAt()
        );
    }
}
