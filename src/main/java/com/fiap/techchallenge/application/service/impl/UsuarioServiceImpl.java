package com.fiap.techchallenge.application.service.impl;

import com.fiap.techchallenge.application.dto.AlterarSenhaDTO;
import com.fiap.techchallenge.application.dto.LoginDTO;
import com.fiap.techchallenge.application.dto.UsuarioRequestDTO;
import com.fiap.techchallenge.application.dto.UsuarioResponseDTO;
import com.fiap.techchallenge.application.service.UsuarioService;
import com.fiap.techchallenge.domain.entities.Usuario;
import com.fiap.techchallenge.domain.repositories.UsuarioRepository;
import com.fiap.techchallenge.infrastructure.exception.RecursoNaoEncontradoException;
import com.fiap.techchallenge.infrastructure.exception.RegraNegocioException;
import com.fiap.techchallenge.infrastructure.exception.SenhaInvalidaException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UsuarioResponseDTO criar(UsuarioRequestDTO dto) {
        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new RegraNegocioException("Email já cadastrado: " + dto.getEmail());
        }

        if (usuarioRepository.existsByLogin(dto.getLogin())) {
            throw new RegraNegocioException("Login já cadastrado: " + dto.getLogin());
        }

        if (usuarioRepository.existsByCpf(dto.getCpf())) {
            throw new RegraNegocioException("CPF já cadastrado: " + dto.getCpf());
        }

        Usuario usuario = toEntity(dto);

        Usuario salvo = usuarioRepository.save(usuario);

        return toResponseDTO(salvo);
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioResponseDTO buscarPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Usuário não encontrado com ID: " + id));

        return toResponseDTO(usuario);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioResponseDTO> buscarPorNome(String nome) {
        List<Usuario> usuarios = usuarioRepository.findByNomeContainingIgnoreCase(nome);
        return usuarios.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioResponseDTO> listarTodos() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UsuarioResponseDTO atualizar(Long id, UsuarioRequestDTO dto) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Usuário não encontrado com ID: " + id));

        if (!usuario.getEmail().equals(dto.getEmail()) &&
                usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new RegraNegocioException("Email já cadastrado: " + dto.getEmail());
        }

        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setTipoUsuario(dto.getTipoUsuario());
        usuario.setEnderecoRua(dto.getEnderecoRua());
        usuario.setEnderecoNumero(dto.getEnderecoNumero());
        usuario.setEnderecoCidade(dto.getEnderecoCidade());
        usuario.setEnderecoCep(dto.getEnderecoCep());

        Usuario atualizado = usuarioRepository.save(usuario);
        return toResponseDTO(atualizado);
    }

    @Override
    public void alterarSenha(Long id, AlterarSenhaDTO dto) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Usuário não encontrado com ID: " + id));

        if (!usuario.getSenha().equals(dto.getSenhaAtual())) {
            throw new SenhaInvalidaException("Senha atual incorreta");
        }

        usuario.setSenha(dto.getNovaSenha());
        usuarioRepository.save(usuario);
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioResponseDTO validarLogin(LoginDTO dto) {
        Usuario usuario = usuarioRepository.findByLogin(dto.getLogin())
                .orElseThrow(() -> new SenhaInvalidaException("Credenciais inválidas"));

        if (!usuario.getSenha().equals(dto.getSenha())) {
            throw new SenhaInvalidaException("Credenciais inválidas");
        }

        return toResponseDTO(usuario);
    }

    @Override
    public void excluir(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RecursoNaoEncontradoException(
                    "Usuário não encontrado com ID: " + id);
        }

        usuarioRepository.deleteById(id);
    }

    private Usuario toEntity(UsuarioRequestDTO dto) {
        return new Usuario(
                dto.getNome(),
                dto.getEmail(),
                dto.getLogin(),
                dto.getSenha(),
                dto.getCpf(),
                dto.getTipoUsuario(),
                dto.getEnderecoRua(),
                dto.getEnderecoNumero(),
                dto.getEnderecoCidade(),
                dto.getEnderecoCep());
    }

    private UsuarioResponseDTO toResponseDTO(Usuario entity) {
        return new UsuarioResponseDTO(
                entity.getId(),
                entity.getNome(),
                entity.getEmail(),
                entity.getLogin(),
                entity.getCpf(),
                entity.getTipoUsuario(),
                entity.getEnderecoRua(),
                entity.getEnderecoNumero(),
                entity.getEnderecoCidade(),
                entity.getEnderecoCep(),
                entity.getDataCriacao(),
                entity.getDataAtualizacao());
    }
}
