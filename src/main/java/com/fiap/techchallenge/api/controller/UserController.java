package com.fiap.techchallenge.api.controller;

import com.fiap.techchallenge.application.dto.AlterarSenhaDTO;
import com.fiap.techchallenge.application.dto.LoginDTO;
import com.fiap.techchallenge.application.dto.UserRequestDTO;
import com.fiap.techchallenge.application.dto.UserResponseDTO;
import com.fiap.techchallenge.application.usecases.user.*;
import com.fiap.techchallenge.domain.entities.User;
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
@RequestMapping("/v1/users")
@Tag(name = "Usuários", description = "API de gestão de usuários do sistema")
public class UserController {

    private final AuthenticateUserUseCase authenticateUserUseCase;
    private final ChangeUserPasswordUseCase changeUserPasswordUseCase;
    private final CreateUserUseCase createUserUseCase;
    private final DeleteUserUseCase deleteUserUseCase;
    private final FindUserByIdUseCase findUserByIdUseCase;
    private final FindUsersByNameUseCase findUsersByNameUseCase;
    private final ListAllUsersUseCase listAllUsersUseCase;
    private final UpdateUserUseCase updateUserUseCase;

    public UserController(AuthenticateUserUseCase authenticateUserUseCase, ChangeUserPasswordUseCase changeUserPasswordUseCase, CreateUserUseCase createUserUseCase, DeleteUserUseCase deleteUserUseCase, FindUserByIdUseCase findUserByIdUseCase, FindUsersByNameUseCase findUsersByNameUseCase, ListAllUsersUseCase listAllUsersUseCase, UpdateUserUseCase updateUserUseCase) {
        this.authenticateUserUseCase = authenticateUserUseCase;
        this.changeUserPasswordUseCase = changeUserPasswordUseCase;
        this.createUserUseCase = createUserUseCase;
        this.deleteUserUseCase = deleteUserUseCase;
        this.findUserByIdUseCase = findUserByIdUseCase;
        this.findUsersByNameUseCase = findUsersByNameUseCase;
        this.listAllUsersUseCase = listAllUsersUseCase;
        this.updateUserUseCase = updateUserUseCase;
    }

    @PostMapping
    @Operation(summary = "Criar novo usuário", description = "Cria um novo usuário no sistema. Valida email, login e CPF únicos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso", content = @Content(schema = @Schema(implementation = UserResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "422", description = "Email, login ou CPF já cadastrado", content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    public ResponseEntity<UserResponseDTO> create(@Valid @RequestBody UserRequestDTO dto) {
        User userToSave = toDomain(dto);
        User userSaved = createUserUseCase.execute(userToSave);
        UserResponseDTO response = toResponseDTO(userSaved);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar usuário por ID", description = "Retorna os dados de um usuário específico pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado", content = @Content(schema = @Schema(implementation = UserResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    public ResponseEntity<UserResponseDTO> findById(@PathVariable Long id) {
        User user = findUserByIdUseCase.execute(id);
        UserResponseDTO response = toResponseDTO(user);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/name/{name}")
    @Operation(summary = "Buscar usuários por nome", description = "Busca usuários cujo nome contenha o texto fornecido (case-insensitive)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de usuários encontrados", content = @Content(schema = @Schema(implementation = UserResponseDTO.class)))
    })
    public ResponseEntity<List<UserResponseDTO>> findByName(@PathVariable String name) {
        List<UserResponseDTO> response = new ArrayList<>();
        for (User user : findUsersByNameUseCase.execute(name)) {
            response.add(toResponseDTO(user));
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(summary = "Listar todos os usuários", description = "Retorna a lista completa de usuários cadastrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de usuários", content = @Content(schema = @Schema(implementation = UserResponseDTO.class)))
    })
    public ResponseEntity<List<UserResponseDTO>> findAll() {
        List<UserResponseDTO> response = new ArrayList<>();
        for (User user : listAllUsersUseCase.execute()) {
            response.add(toResponseDTO(user));
        }
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar dados cadastrais", description = "Atualiza dados do usuário (exceto senha, CPF e login). Para senha, use o endpoint PATCH /v1/users/{id}/password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso", content = @Content(schema = @Schema(implementation = UserResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "422", description = "Email já cadastrado", content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    public ResponseEntity<UserResponseDTO> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserRequestDTO dto) {
        User user = toDomain(dto);
        User updatedUser = updateUserUseCase.execute(id, user);
        UserResponseDTO response = toResponseDTO(updatedUser);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/password")
    @Operation(summary = "Alterar senha", description = "Endpoint exclusivo para troca de senha. Requer senha atual para validação.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Senha alterada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "401", description = "Senha atual incorreta", content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    public ResponseEntity<Void> updatePassword(
            @PathVariable Long id,
            @Valid @RequestBody AlterarSenhaDTO dto) {
        changeUserPasswordUseCase.execute(id, dto.getSenhaAtual(), dto.getNovaSenha());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/login")
    @Operation(summary = "Validar login", description = "Valida credenciais de login e retorna dados do usuário se válido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login válido", content = @Content(schema = @Schema(implementation = UserResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "401", description = "Credenciais inválidas", content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    public ResponseEntity<UserResponseDTO> login(@Valid @RequestBody LoginDTO dto) {
        User user = authenticateUserUseCase.execute(dto.getLogin(), dto.getSenha());
        UserResponseDTO response = toResponseDTO(user);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir usuário", description = "Remove um usuário do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuário excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        deleteUserUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }


    private User toDomain(UserRequestDTO dto) {
        return new User(
                null,
                dto.getNome(),
                dto.getEmail(),
                dto.getLogin(),
                dto.getSenha(),
                dto.getCpf(),
                dto.getTipoUsuario(),
                dto.getEnderecoRua(),
                dto.getEnderecoNumero(),
                dto.getEnderecoCidade(),
                dto.getEnderecoCep(),
                null,
                null
                );
    }

    private UserResponseDTO toResponseDTO(User entity) {
        return new UserResponseDTO(
                entity.getId(),
                entity.getName(),
                entity.getEmail(),
                entity.getLogin(),
                entity.getCpf(),
                entity.getUserType(),
                entity.getStreetAddress(),
                entity.getNumberAddress(),
                entity.getCityAddress(),
                entity.getCepAddress(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
