package com.fiap.techchallenge.api.controller;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fiap.techchallenge.application.dto.userType.UserTypeRequestDTO;
import com.fiap.techchallenge.application.dto.userType.UserTypeResponseDTO;
import com.fiap.techchallenge.application.usecases.userType.CreateUserTypeUseCase;
import com.fiap.techchallenge.application.usecases.userType.DeleteUserTypeUseCase;
import com.fiap.techchallenge.application.usecases.userType.FindUserTypeByIdUseCase;
import com.fiap.techchallenge.application.usecases.userType.FindUserTypeByNameContainingIgnoreCaseUseCase;
import com.fiap.techchallenge.application.usecases.userType.ListAllUserTypesUseCase;
import com.fiap.techchallenge.application.usecases.userType.UpdateUserTypeUseCase;
import com.fiap.techchallenge.domain.entities.UserType;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.ProblemDetail;

@RestController
@RequestMapping("/v2/user-types")
@Tag(name = "Tipos de Usuários", description = "API de gestão de tipos de usuários")
public class UserTypeController {

    private final CreateUserTypeUseCase createUserTypeUseCase;
    private final FindUserTypeByIdUseCase findUserTypeByIdUseCase;
    private final FindUserTypeByNameContainingIgnoreCaseUseCase findUserTypeByNameContainingIgnoreCaseUseCase;
    private final ListAllUserTypesUseCase listAllUserTypesUseCase;
    private final UpdateUserTypeUseCase updateUserTypeUseCase;
    private final DeleteUserTypeUseCase deleteUserTypeUseCase;

    public UserTypeController(
            CreateUserTypeUseCase createUserTypeUseCase,
            FindUserTypeByIdUseCase findUserTypeByIdUseCase,
            FindUserTypeByNameContainingIgnoreCaseUseCase findUserTypeByNameContainingIgnoreCaseUseCase,
            ListAllUserTypesUseCase listAllUserTypesUseCase,
            UpdateUserTypeUseCase updateUserTypeUseCase,
            DeleteUserTypeUseCase deleteUserTypeUseCase) {
        this.createUserTypeUseCase = createUserTypeUseCase;
        this.findUserTypeByIdUseCase = findUserTypeByIdUseCase;
        this.findUserTypeByNameContainingIgnoreCaseUseCase = findUserTypeByNameContainingIgnoreCaseUseCase;
        this.listAllUserTypesUseCase = listAllUserTypesUseCase;
        this.updateUserTypeUseCase = updateUserTypeUseCase;
        this.deleteUserTypeUseCase = deleteUserTypeUseCase;
    }

    @PostMapping
    @Operation(summary = "Criar novo tipo de usuário", description = "Cria um novo tipo de usuário no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tipo de usuário criado com sucesso", content = @Content(schema = @Schema(implementation = UserTypeResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "422", description = "Tipo de usuário já cadastrado", content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    public ResponseEntity<UserTypeResponseDTO> create(@Valid @RequestBody UserTypeRequestDTO dto) {
        UserType userTypeToSave = toDomain(dto);
        UserType userTypeSaved = createUserTypeUseCase.execute(userTypeToSave);
        UserTypeResponseDTO response = toResponseDTO(userTypeSaved);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar tipo de usuário por ID", description = "Retorna os dados de um tipo de usuário específico pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tipo de usuário encontrado", content = @Content(schema = @Schema(implementation = UserTypeResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Tipo de usuário não encontrado", content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    public ResponseEntity<UserTypeResponseDTO> findById(@PathVariable Long id) {
        UserType userType = findUserTypeByIdUseCase.execute(id);
        UserTypeResponseDTO response = toResponseDTO(userType);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/name/{name}")
    @Operation(summary = "Buscar tipo de usuário por nome (parcial)", description = "Retorna uma lista de tipos de usuários que contêm o termo pesquisado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de tipos de usuários encontrada", content = @Content(schema = @Schema(implementation = UserTypeResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Nenhum tipo de usuário encontrado", content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    public ResponseEntity<List<UserTypeResponseDTO>> findByName(@PathVariable String name) {
        List<UserType> userTypes = findUserTypeByNameContainingIgnoreCaseUseCase.execute(name);
        if (userTypes.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<UserTypeResponseDTO> response = userTypes.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(summary = "Listar todos os tipos de usuários", description = "Retorna a lista completa de tipos de usuários cadastrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de tipos de usuários", content = @Content(schema = @Schema(implementation = UserTypeResponseDTO.class)))
    })
    public ResponseEntity<List<UserTypeResponseDTO>> findAll() {
        List<UserType> userTypes = listAllUserTypesUseCase.execute();
        List<UserTypeResponseDTO> response = userTypes.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar tipo de usuário", description = "Atualiza os dados de um tipo de usuário específico pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tipo de usuário atualizado com sucesso", content = @Content(schema = @Schema(implementation = UserTypeResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404", description = "Tipo de usuário não encontrado", content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    public ResponseEntity<UserTypeResponseDTO> update(@PathVariable Long id,
            @Valid @RequestBody UserTypeRequestDTO dto) {
        UserType userTypeToUpdate = toDomain(dto);
        UserType userTypeUpdated = updateUserTypeUseCase.execute(id, userTypeToUpdate);
        UserTypeResponseDTO response = toResponseDTO(userTypeUpdated);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar tipo de usuário", description = "Deleta um tipo de usuário específico pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Tipo de usuário deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Tipo de usuário não encontrado", content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        deleteUserTypeUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }

    private UserType toDomain(UserTypeRequestDTO dto) {
        return new UserType(null, dto.getName());
    }

    private UserTypeResponseDTO toResponseDTO(UserType userType) {
        return new UserTypeResponseDTO(userType.getId(), userType.getName());
    }
}