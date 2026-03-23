package com.fiap.techchallenge.application.service.impl;

import com.fiap.techchallenge.application.dto.AlterarSenhaDTO;
import com.fiap.techchallenge.application.dto.LoginDTO;
import com.fiap.techchallenge.application.dto.UserRequestDTO;
import com.fiap.techchallenge.application.dto.UserResponseDTO;
import com.fiap.techchallenge.application.service.UsuarioService;
import com.fiap.techchallenge.domain.entities.User;
import com.fiap.techchallenge.domain.repositories.UsuarioRepository;
import com.fiap.techchallenge.infrastructure.exception.ResourceNotFoundException;
import com.fiap.techchallenge.infrastructure.exception.BusinessRuleException;
import com.fiap.techchallenge.infrastructure.exception.InvalidPasswordException;
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
    public UserResponseDTO criar(UserRequestDTO dto) {
        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new BusinessRuleException("Email já cadastrado: " + dto.getEmail());
        }

        if (usuarioRepository.existsByLogin(dto.getLogin())) {
            throw new BusinessRuleException("Login já cadastrado: " + dto.getLogin());
        }

        if (usuarioRepository.existsByCpf(dto.getCpf())) {
            throw new BusinessRuleException("CPF já cadastrado: " + dto.getCpf());
        }

        User user = toEntity(dto);

        User salvo = usuarioRepository.save(user);

        return toResponseDTO(salvo);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponseDTO buscarPorId(Long id) {
        User user = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Usuário não encontrado com ID: " + id));

        return toResponseDTO(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponseDTO> buscarPorNome(String nome) {
        List<User> users = usuarioRepository.findByNomeContainingIgnoreCase(nome);
        return users.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponseDTO> listarTodos() {
        List<User> users = usuarioRepository.findAll();
        return users.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserResponseDTO atualizar(Long id, UserRequestDTO dto) {
        User user = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Usuário não encontrado com ID: " + id));

        if (!user.getEmail().equals(dto.getEmail()) &&
                usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new BusinessRuleException("Email já cadastrado: " + dto.getEmail());
        }

        user.setName(dto.getNome());
        user.setEmail(dto.getEmail());
        user.setUserType(dto.getTipoUsuario());
        user.setStreetAddress(dto.getEnderecoRua());
        user.setNumberAddress(dto.getEnderecoNumero());
        user.setCityAddress(dto.getEnderecoCidade());
        user.setCepAddress(dto.getEnderecoCep());

        User atualizado = usuarioRepository.save(user);
        return toResponseDTO(atualizado);
    }

    @Override
    public void alterarSenha(Long id, AlterarSenhaDTO dto) {
        User user = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Usuário não encontrado com ID: " + id));

        if (!user.getPassword().equals(dto.getSenhaAtual())) {
            throw new InvalidPasswordException("Senha atual incorreta");
        }

        user.setPassword(dto.getNovaSenha());
        usuarioRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponseDTO validarLogin(LoginDTO dto) {
        User user = usuarioRepository.findByLogin(dto.getLogin())
                .orElseThrow(() -> new InvalidPasswordException("Credenciais inválidas"));

        if (!user.getPassword().equals(dto.getSenha())) {
            throw new InvalidPasswordException("Credenciais inválidas");
        }

        return toResponseDTO(user);
    }

    @Override
    public void excluir(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new ResourceNotFoundException(
                    "Usuário não encontrado com ID: " + id);
        }

        usuarioRepository.deleteById(id);
    }

    private User toEntity(UserRequestDTO dto) {
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
                entity.getUpdatedAt());
    }
}
