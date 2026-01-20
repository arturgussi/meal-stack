package com.fiap.techchallenge.application.service.impl;

import com.fiap.techchallenge.application.dto.AlterarSenhaDTO;
import com.fiap.techchallenge.application.dto.LoginDTO;
import com.fiap.techchallenge.application.dto.UsuarioRequestDTO;
import com.fiap.techchallenge.application.dto.UsuarioResponseDTO;
import com.fiap.techchallenge.domain.entities.Usuario;
import com.fiap.techchallenge.domain.enums.TipoUsuario;
import com.fiap.techchallenge.domain.repositories.UsuarioRepository;
import com.fiap.techchallenge.infrastructure.exception.RecursoNaoEncontradoException;
import com.fiap.techchallenge.infrastructure.exception.RegraNegocioException;
import com.fiap.techchallenge.infrastructure.exception.SenhaInvalidaException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes do UsuarioService")
class UsuarioServiceImplTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioServiceImpl usuarioService;

    private UsuarioRequestDTO usuarioRequestDTO;
    private Usuario usuario;

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

        usuario = new Usuario(
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
        usuario.setId(1L);
    }

    @Test
    @DisplayName("Deve criar usuário com sucesso")
    void criarUsuario_ComSucesso() {
        when(usuarioRepository.existsByEmail(anyString())).thenReturn(false);
        when(usuarioRepository.existsByLogin(anyString())).thenReturn(false);
        when(usuarioRepository.existsByCpf(anyString())).thenReturn(false);
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        UsuarioResponseDTO resultado = usuarioService.criar(usuarioRequestDTO);

        assertNotNull(resultado);
        assertEquals("João Silva", resultado.getNome());
        assertEquals("joao@email.com", resultado.getEmail());
        assertEquals("joao.silva", resultado.getLogin());
        assertEquals(TipoUsuario.CLIENTE, resultado.getTipoUsuario());

        // Verificar que a senha NÃO está no DTO de resposta
        // (UsuarioResponseDTO não tem campo senha)

        // Verificar chamadas aos mocks
        verify(usuarioRepository, times(1)).existsByEmail("joao@email.com");
        verify(usuarioRepository, times(1)).existsByLogin("joao.silva");
        verify(usuarioRepository, times(1)).existsByCpf("12345678901");
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    @DisplayName("Deve lançar exceção ao criar usuário com email duplicado")
    void criarUsuario_ComEmailDuplicado() {
        when(usuarioRepository.existsByEmail(anyString())).thenReturn(true);

        RegraNegocioException exception = assertThrows(
                RegraNegocioException.class,
                () -> usuarioService.criar(usuarioRequestDTO));

        assertEquals("Email já cadastrado: joao@email.com", exception.getMessage());

        // Verificar que o save() NÃO foi chamado
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar usuário inexistente")
    void buscarPorId_NaoExistente() {
        Long idInexistente = 999L;
        when(usuarioRepository.findById(idInexistente)).thenReturn(Optional.empty());

        RecursoNaoEncontradoException exception = assertThrows(
                RecursoNaoEncontradoException.class,
                () -> usuarioService.buscarPorId(idInexistente));

        assertEquals("Usuário não encontrado com ID: 999", exception.getMessage());
        verify(usuarioRepository, times(1)).findById(idInexistente);
    }

    @Test
    @DisplayName("Deve lançar exceção ao alterar senha com senha atual incorreta")
    void alterarSenha_ComSenhaIncorreta() {
        AlterarSenhaDTO alterarSenhaDTO = new AlterarSenhaDTO("senhaErrada", "novaSenha123");
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        SenhaInvalidaException exception = assertThrows(
                SenhaInvalidaException.class,
                () -> usuarioService.alterarSenha(1L, alterarSenhaDTO));

        assertEquals("Senha atual incorreta", exception.getMessage());

        // Verificar que o save() NÃO foi chamado
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }

    @Test
    @DisplayName("Deve buscar usuário por ID com sucesso")
    void buscarPorId_ComSucesso() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        UsuarioResponseDTO resultado = usuarioService.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("João Silva", resultado.getNome());
        verify(usuarioRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Deve buscar usuários por nome")
    void buscarPorNome_ComSucesso() {
        List<Usuario> usuarios = Arrays.asList(usuario);
        when(usuarioRepository.findByNomeContainingIgnoreCase("Silva")).thenReturn(usuarios);

        List<UsuarioResponseDTO> resultado = usuarioService.buscarPorNome("Silva");

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("João Silva", resultado.get(0).getNome());
        verify(usuarioRepository, times(1)).findByNomeContainingIgnoreCase("Silva");
    }

    @Test
    @DisplayName("Deve listar todos os usuários")
    void listarTodos_ComSucesso() {
        List<Usuario> usuarios = Arrays.asList(usuario);
        when(usuarioRepository.findAll()).thenReturn(usuarios);

        List<UsuarioResponseDTO> resultado = usuarioService.listarTodos();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(usuarioRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Deve atualizar usuário com sucesso")
    void atualizar_ComSucesso() {
        UsuarioRequestDTO dtoAtualizado = new UsuarioRequestDTO(
                "João Silva Atualizado",
                "joao@email.com", // Mesmo email
                "joao.silva",
                "senha123",
                "12345678901",
                TipoUsuario.CLIENTE,
                "Rua B",
                "200",
                "Rio de Janeiro",
                "20000000");

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        UsuarioResponseDTO resultado = usuarioService.atualizar(1L, dtoAtualizado);

        assertNotNull(resultado);
        verify(usuarioRepository, times(1)).findById(1L);
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    @DisplayName("Deve lançar exceção ao atualizar com email já existente")
    void atualizar_ComEmailDuplicado() {
        UsuarioRequestDTO dtoAtualizado = new UsuarioRequestDTO(
                "João Silva",
                "outro@email.com", // Email diferente
                "joao.silva",
                "senha123",
                "12345678901",
                TipoUsuario.CLIENTE,
                "Rua A",
                "100",
                "São Paulo",
                "01234567");

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(usuarioRepository.existsByEmail("outro@email.com")).thenReturn(true);

        RegraNegocioException exception = assertThrows(
                RegraNegocioException.class,
                () -> usuarioService.atualizar(1L, dtoAtualizado));

        assertEquals("Email já cadastrado: outro@email.com", exception.getMessage());
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }

    @Test
    @DisplayName("Deve alterar senha com sucesso")
    void alterarSenha_ComSucesso() {
        AlterarSenhaDTO alterarSenhaDTO = new AlterarSenhaDTO("senha123", "novaSenha456");
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        assertDoesNotThrow(() -> usuarioService.alterarSenha(1L, alterarSenhaDTO));

        verify(usuarioRepository, times(1)).findById(1L);
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    @DisplayName("Deve validar login com sucesso")
    void validarLogin_ComSucesso() {
        LoginDTO loginDTO = new LoginDTO("joao.silva", "senha123");
        when(usuarioRepository.findByLogin("joao.silva")).thenReturn(Optional.of(usuario));

        UsuarioResponseDTO resultado = usuarioService.validarLogin(loginDTO);

        assertNotNull(resultado);
        assertEquals("João Silva", resultado.getNome());
        verify(usuarioRepository, times(1)).findByLogin("joao.silva");
    }

    @Test
    @DisplayName("Deve lançar exceção ao validar login com credenciais inválidas")
    void validarLogin_ComCredenciaisInvalidas() {
        LoginDTO loginDTO = new LoginDTO("joao.silva", "senhaErrada");
        when(usuarioRepository.findByLogin("joao.silva")).thenReturn(Optional.of(usuario));

        SenhaInvalidaException exception = assertThrows(
                SenhaInvalidaException.class,
                () -> usuarioService.validarLogin(loginDTO));

        assertEquals("Credenciais inválidas", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção ao validar login inexistente")
    void validarLogin_LoginInexistente() {
        LoginDTO loginDTO = new LoginDTO("inexistente", "senha123");
        when(usuarioRepository.findByLogin("inexistente")).thenReturn(Optional.empty());

        SenhaInvalidaException exception = assertThrows(
                SenhaInvalidaException.class,
                () -> usuarioService.validarLogin(loginDTO));

        assertEquals("Credenciais inválidas", exception.getMessage());
    }

    @Test
    @DisplayName("Deve excluir usuário com sucesso")
    void excluir_ComSucesso() {
        when(usuarioRepository.existsById(1L)).thenReturn(true);
        doNothing().when(usuarioRepository).deleteById(1L);

        assertDoesNotThrow(() -> usuarioService.excluir(1L));

        verify(usuarioRepository, times(1)).existsById(1L);
        verify(usuarioRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Deve lançar exceção ao excluir usuário inexistente")
    void excluir_UsuarioInexistente() {
        when(usuarioRepository.existsById(999L)).thenReturn(false);

        RecursoNaoEncontradoException exception = assertThrows(
                RecursoNaoEncontradoException.class,
                () -> usuarioService.excluir(999L));

        assertEquals("Usuário não encontrado com ID: 999", exception.getMessage());
        verify(usuarioRepository, never()).deleteById(any());
    }

    @Test
    @DisplayName("Deve lançar exceção ao criar usuário com login duplicado")
    void criarUsuario_ComLoginDuplicado() {
        when(usuarioRepository.existsByEmail(anyString())).thenReturn(false);
        when(usuarioRepository.existsByLogin(anyString())).thenReturn(true);

        RegraNegocioException exception = assertThrows(
                RegraNegocioException.class,
                () -> usuarioService.criar(usuarioRequestDTO));

        assertEquals("Login já cadastrado: joao.silva", exception.getMessage());
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }

    @Test
    @DisplayName("Deve lançar exceção ao criar usuário com CPF duplicado")
    void criarUsuario_ComCpfDuplicado() {
        when(usuarioRepository.existsByEmail(anyString())).thenReturn(false);
        when(usuarioRepository.existsByLogin(anyString())).thenReturn(false);
        when(usuarioRepository.existsByCpf(anyString())).thenReturn(true);

        RegraNegocioException exception = assertThrows(
                RegraNegocioException.class,
                () -> usuarioService.criar(usuarioRequestDTO));

        assertEquals("CPF já cadastrado: 12345678901", exception.getMessage());
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }
}
