package com.fiap.techchallenge.api.controller;

import com.fiap.techchallenge.application.dto.restaurant.RestaurantRequestDTO;
import com.fiap.techchallenge.application.dto.restaurant.RestaurantResponseDTO;
import com.fiap.techchallenge.application.usecases.restaurante.*;
import com.fiap.techchallenge.domain.entities.Restaurant;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("v1/restaurants")
@Tag(name = "Restaurantes", description = "API de gestão de restaurantes do sistema")
public class RestaurantController {

    private final CreateRestaurantUseCase createRestaurantUseCase;
    private final DeleteRestaurantUseCase deleteRestaurantUseCase;
    private final FindRestaurantByIdUseCase findRestaurantByIdUseCase;
    private final ListAllRestaurantsUseCase listAllRestaurantsUseCase;
    private final UpdateRestaurantUseCase updateRestaurantUseCase;

    public RestaurantController(CreateRestaurantUseCase createRestaurantUseCase, DeleteRestaurantUseCase deleteRestaurantUseCase, FindRestaurantByIdUseCase findRestaurantByIdUseCase, ListAllRestaurantsUseCase listAllRestaurantsUseCase, UpdateRestaurantUseCase updateRestaurantUseCase) {
        this.createRestaurantUseCase = createRestaurantUseCase;
        this.deleteRestaurantUseCase = deleteRestaurantUseCase;
        this.findRestaurantByIdUseCase = findRestaurantByIdUseCase;
        this.listAllRestaurantsUseCase = listAllRestaurantsUseCase;
        this.updateRestaurantUseCase = updateRestaurantUseCase;
    }

    @PostMapping
    @Operation(summary = "Criar novo restaurante", description = "Cria um novo restaurante no sistema. Valida nome único e dono do tipo DONO_RESTAURANTE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Restaurante criado com sucesso", content = @Content(schema = @Schema(implementation = RestaurantResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "422", description = "Nome já cadastrado", content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    public ResponseEntity<RestaurantResponseDTO> create(@Valid@RequestBody RestaurantRequestDTO dto) {
        Restaurant restaurantToSave = toDomain(dto);
        Restaurant restaurantSaved = createRestaurantUseCase.execute(restaurantToSave);
        RestaurantResponseDTO response = toResponseDTO(restaurantSaved);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar restaurante por ID", description = "Retorna os dados de um restaurante específico pelo se ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Restaurante encontrado", content = @Content(schema = @Schema(implementation = RestaurantResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    public ResponseEntity<RestaurantResponseDTO> findById(@PathVariable Long id) {
        Restaurant restaurant = findRestaurantByIdUseCase.execute(id);
        RestaurantResponseDTO response = toResponseDTO(restaurant);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(summary = "Listar todos os restaurantes", description = "Retorna a lista completa de restaurantes cadastrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de restaurantes", content = @Content(schema = @Schema(implementation = RestaurantResponseDTO.class)))
    })
    public ResponseEntity<List<RestaurantResponseDTO>> findAll() {
        List<RestaurantResponseDTO> response = new ArrayList<>();
        for (Restaurant restaurant : listAllRestaurantsUseCase.execute()) {
            response.add(toResponseDTO(restaurant));
        }

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar dados cadastrais", description = "Atualiza os dados do restaurante")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Restaurante atualizado com sucesso", content = @Content(schema = @Schema(implementation = RestaurantResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
    })
    public ResponseEntity<RestaurantResponseDTO> updateRestaurant(
            @PathVariable Long id,
            @Valid @RequestBody RestaurantRequestDTO dto) {
        Restaurant restaurant = toDomain(dto);
        Restaurant updateRestaurant = updateRestaurantUseCase.execute(id, restaurant);
        RestaurantResponseDTO response = toResponseDTO(updateRestaurant);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir restaurante", description = "Remove um restaurante do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Restaurante excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        deleteRestaurantUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }


    private Restaurant toDomain(RestaurantRequestDTO dto) {
        return new Restaurant(
                null,
                dto.getName(),
                dto.getCuisineType(),
                dto.getOperatingHours(),
                dto.getStreetAddress(),
                dto.getNumberAddress(),
                dto.getCityAddress(),
                dto.getCepAddress(),
                dto.getOwnerId()
        );
    }

    private RestaurantResponseDTO toResponseDTO(Restaurant entity) {
        return new RestaurantResponseDTO(
                entity.getId(),
                entity.getName(),
                entity.getCuisineType(),
                entity.getOperatingHours(),
                entity.getStreetAddress(),
                entity.getNumberAddress(),
                entity.getCityAddress(),
                entity.getCepAddress(),
                entity.getOwnerId(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
