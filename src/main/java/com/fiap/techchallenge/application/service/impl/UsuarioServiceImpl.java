package com.fiap.techchallenge.application.service.impl;

import com.fiap.techchallenge.application.dto.AlterarSenhaDTO;
import com.fiap.techchallenge.application.dto.LoginDTO;
import com.fiap.techchallenge.application.dto.UsuarioRequestDTO;
import com.fiap.techchallenge.application.dto.UsuarioResponseDTO;
import com.fiap.techchallenge.application.service.UsuarioService;
import com.fiap.techchallenge.domain.entities.User;
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

        User user = toEntity(dto);

        User salvo = usuarioRepository.save(user);

        return toResponseDTO(salvo);
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioResponseDTO buscarPorId(Long id) {
        User user = usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Usuário não encontrado com ID: " + id));

        return toResponseDTO(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioResponseDTO> buscarPorNome(String nome) {
        List<User> users = usuarioRepository.findByNomeContainingIgnoreCase(nome);
        return users.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioResponseDTO> listarTodos() {
        List<User> users = usuarioRepository.findAll();
        return users.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UsuarioResponseDTO atualizar(Long id, UsuarioRequestDTO dto) {
        User user = usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Usuário não encontrado com ID: " + id));

        if (!user.getEmail().equals(dto.getEmail()) &&
                usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new RegraNegocioException("Email já cadastrado: " + dto.getEmail());
        }

        user.setNome(dto.getNome());
        user.setEmail(dto.getEmail());
        user.setTipoUsuario(dto.getTipoUsuario());
        user.setEnderecoRua(dto.getEnderecoRua());
        user.setEnderecoNumero(dto.getEnderecoNumero());
        user.setEnderecoCidade(dto.getEnderecoCidade());
        user.setEnderecoCep(dto.getEnderecoCep());

        User atualizado = usuarioRepository.save(user);
        return toResponseDTO(atualizado);
    }

    @Override
    public void alterarSenha(Long id, AlterarSenhaDTO dto) {
        User user = usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Usuário não encontrado com ID: " + id));

        if (!user.getSenha().equals(dto.getSenhaAtual())) {
            throw new SenhaInvalidaException("Senha atual incorreta");
        }

        user.setSenha(dto.getNovaSenha());
        usuarioRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioResponseDTO validarLogin(LoginDTO dto) {
        User user = usuarioRepository.findByLogin(dto.getLogin())
                .orElseThrow(() -> new SenhaInvalidaException("Credenciais inválidas"));

        if (!user.getSenha().equals(dto.getSenha())) {
            throw new SenhaInvalidaException("Credenciais inválidas");
        }

        return toResponseDTO(user);
    }

    @Override
    public void excluir(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RecursoNaoEncontradoException(
                    "Usuário não encontrado com ID: " + id);
        }

        usuarioRepository.deleteById(id);
    }

    private User toEntity(UsuarioRequestDTO dto) {
        return new User(
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

    private UsuarioResponseDTO toResponseDTO(User entity) {
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
