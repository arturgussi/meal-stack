package com.fiap.techchallenge.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.techchallenge.application.dto.AlterarSenhaDTO;
import com.fiap.techchallenge.application.dto.LoginDTO;
import com.fiap.techchallenge.application.dto.UsuarioRequestDTO;
import com.fiap.techchallenge.application.dto.UsuarioResponseDTO;
import com.fiap.techchallenge.application.service.UsuarioService;
import com.fiap.techchallenge.domain.enums.TipoUsuario;
import com.fiap.techchallenge.infrastructure.exception.RecursoNaoEncontradoException;
import com.fiap.techchallenge.infrastructure.exception.RegraNegocioException;
import com.fiap.techchallenge.infrastructure.exception.SenhaInvalidaException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UsuarioController.class)
@DisplayName("Testes do UsuarioController")
class UsuarioControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private ObjectMapper objectMapper;

        @MockBean
        private UsuarioService usuarioService;

        private UsuarioRequestDTO usuarioRequestDTO;
        private UsuarioResponseDTO usuarioResponseDTO;

        @BeforeEach
        void setUp() {
                usuarioRequestDTO = new UsuarioRequestDTO(
                                "João Silva",
                                "joao@email.com",
                                "joao.silva",
                                "senha123",
                                "12345678901",
                                TipoUsuario.CLIENTE,
                                "Rua A",
                                "100",
                                "São Paulo",
                                "01234567");

                usuarioResponseDTO = new UsuarioResponseDTO(
                                1L,
                                "João Silva",
                                "joao@email.com",
                                "joao.silva",
                                "12345678901",
                                TipoUsuario.CLIENTE,
                                "Rua A",
                                "100",
                                "São Paulo",
                                "01234567",
                                LocalDateTime.now(),
                                LocalDateTime.now());
        }

        @Test
        @DisplayName("POST /v1/usuarios - Deve criar usuário e retornar 201 Created")
        void criarUsuario_DeveRetornar201() throws Exception {
                when(usuarioService.criar(any(UsuarioRequestDTO.class))).thenReturn(usuarioResponseDTO);

                mockMvc.perform(post("/v1/usuarios")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(usuarioRequestDTO)))
                                .andExpect(status().isCreated())
                                .andExpect(jsonPath("$.id").value(1))
                                .andExpect(jsonPath("$.nome").value("João Silva"))
                                .andExpect(jsonPath("$.email").value("joao@email.com"))
                                .andExpect(jsonPath("$.login").value("joao.silva"))
                                .andExpect(jsonPath("$.tipoUsuario").value("CLIENTE"));
        }

        @Test
        @DisplayName("POST /v1/usuarios - Deve retornar 400 Bad Request para dados inválidos")
        void criarUsuario_DadosInvalidos_DeveRetornar400() throws Exception {
                // DTO com campos inválidos
                UsuarioRequestDTO dtoInvalido = new UsuarioRequestDTO();
                dtoInvalido.setNome(""); // Nome vazio - inválido
                dtoInvalido.setEmail("email-invalido"); // Email inválido

                mockMvc.perform(post("/v1/usuarios")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dtoInvalido)))
                                .andExpect(status().isBadRequest())
                                .andExpect(jsonPath("$.title").value("Dados inválidos"))
                                .andExpect(jsonPath("$.status").value(400));
        }

        @Test
        @DisplayName("POST /v1/usuarios - Deve retornar 422 para email duplicado")
        void criarUsuario_EmailDuplicado_DeveRetornar422() throws Exception {
                when(usuarioService.criar(any(UsuarioRequestDTO.class)))
                                .thenThrow(new RegraNegocioException("Email já cadastrado: joao@email.com"));

                mockMvc.perform(post("/v1/usuarios")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(usuarioRequestDTO)))
                                .andExpect(status().isUnprocessableEntity())
                                .andExpect(jsonPath("$.title").value("Regra de negócio violada"))
                                .andExpect(jsonPath("$.status").value(422))
                                .andExpect(jsonPath("$.detail").value("Email já cadastrado: joao@email.com"));
        }

        @Test
        @DisplayName("GET /v1/usuarios/{id} - Deve retornar usuário e status 200")
        void buscarPorId_DeveRetornar200() throws Exception {
                when(usuarioService.buscarPorId(1L)).thenReturn(usuarioResponseDTO);

                mockMvc.perform(get("/v1/usuarios/1"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.id").value(1))
                                .andExpect(jsonPath("$.nome").value("João Silva"));
        }

        @Test
        @DisplayName("GET /v1/usuarios/{id} - Deve retornar 404 para ID inexistente")
        void buscarPorId_IdInexistente_DeveRetornar404() throws Exception {
                when(usuarioService.buscarPorId(999L))
                                .thenThrow(new RecursoNaoEncontradoException("Usuário não encontrado com ID: 999"));

                mockMvc.perform(get("/v1/usuarios/999"))
                                .andExpect(status().isNotFound())
                                .andExpect(jsonPath("$.title").value("Recurso não encontrado"))
                                .andExpect(jsonPath("$.status").value(404))
                                .andExpect(jsonPath("$.detail").value("Usuário não encontrado com ID: 999"));
        }

        @Test
        @DisplayName("PUT /v1/usuarios/{id} - Deve atualizar usuário e retornar 200")
        void atualizarUsuario_DeveRetornar200() throws Exception {
                when(usuarioService.atualizar(eq(1L), any(UsuarioRequestDTO.class)))
                                .thenReturn(usuarioResponseDTO);

                mockMvc.perform(put("/v1/usuarios/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(usuarioRequestDTO)))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.id").value(1))
                                .andExpect(jsonPath("$.nome").value("João Silva"));
        }

        @Test
        @DisplayName("PATCH /v1/usuarios/{id}/senha - Deve alterar senha e retornar 204")
        void alterarSenha_DeveRetornar204() throws Exception {
                AlterarSenhaDTO alterarSenhaDTO = new AlterarSenhaDTO("senha123", "novaSenha456");

                mockMvc.perform(patch("/v1/usuarios/1/senha")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(alterarSenhaDTO)))
                                .andExpect(status().isNoContent());
        }

        @Test
        @DisplayName("PATCH /v1/usuarios/{id}/senha - Deve retornar 401 para senha atual incorreta")
        void alterarSenha_SenhaIncorreta_DeveRetornar401() throws Exception {
                AlterarSenhaDTO alterarSenhaDTO = new AlterarSenhaDTO("senhaErrada", "novaSenha456");
                doThrow(new SenhaInvalidaException("Senha atual incorreta"))
                                .when(usuarioService).alterarSenha(eq(1L), any(AlterarSenhaDTO.class));

                mockMvc.perform(patch("/v1/usuarios/1/senha")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(alterarSenhaDTO)))
                                .andExpect(status().isUnauthorized())
                                .andExpect(jsonPath("$.title").value("Credenciais inválidas"))
                                .andExpect(jsonPath("$.status").value(401))
                                .andExpect(jsonPath("$.detail").value("Senha atual incorreta"));
        }

        @Test
        @DisplayName("POST /v1/usuarios/login - Deve validar login e retornar 200")
        void validarLogin_DeveRetornar200() throws Exception {
                LoginDTO loginDTO = new LoginDTO("joao.silva", "senha123");
                when(usuarioService.validarLogin(any(LoginDTO.class))).thenReturn(usuarioResponseDTO);

                mockMvc.perform(post("/v1/usuarios/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(loginDTO)))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.nome").value("João Silva"))
                                .andExpect(jsonPath("$.login").value("joao.silva"));
        }

        @Test
        @DisplayName("POST /v1/usuarios/login - Deve retornar 401 para credenciais inválidas")
        void validarLogin_CredenciaisInvalidas_DeveRetornar401() throws Exception {
                LoginDTO loginDTO = new LoginDTO("joao.silva", "senhaErrada");
                when(usuarioService.validarLogin(any(LoginDTO.class)))
                                .thenThrow(new SenhaInvalidaException("Credenciais inválidas"));

                mockMvc.perform(post("/v1/usuarios/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(loginDTO)))
                                .andExpect(status().isUnauthorized())
                                .andExpect(jsonPath("$.title").value("Credenciais inválidas"))
                                .andExpect(jsonPath("$.status").value(401));
        }

        @Test
        @DisplayName("DELETE /v1/usuarios/{id} - Deve excluir usuário e retornar 204")
        void excluirUsuario_DeveRetornar204() throws Exception {
                mockMvc.perform(delete("/v1/usuarios/1"))
                                .andExpect(status().isNoContent());
        }

        @Test
        @DisplayName("DELETE /v1/usuarios/{id} - Deve retornar 404 para ID inexistente")
        void excluirUsuario_IdInexistente_DeveRetornar404() throws Exception {
                doThrow(new RecursoNaoEncontradoException("Usuário não encontrado com ID: 999"))
                                .when(usuarioService).excluir(999L);

                mockMvc.perform(delete("/v1/usuarios/999"))
                                .andExpect(status().isNotFound())
                                .andExpect(jsonPath("$.title").value("Recurso não encontrado"))
                                .andExpect(jsonPath("$.status").value(404));
        }
}
