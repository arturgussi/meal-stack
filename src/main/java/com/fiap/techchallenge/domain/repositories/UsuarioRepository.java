package com.fiap.techchallenge.domain.repositories;

import com.fiap.techchallenge.domain.entities.User;
import com.fiap.techchallenge.domain.enums.UserType;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository {

    /**
     * Salva um novo usuário ou atualiza um existente.
     *
     * @param user Usuário a ser salvo
     * @return Usuário salvo com ID gerado (se novo)
     */
    User save(User user);

    /**
     * Busca um usuário pelo ID.
     *
     * @param id ID do usuário
     * @return Optional com o usuário se encontrado
     */
    Optional<User> findById(Long id);

    /**
     * Retorna todos os usuários.
     *
     * @return Lista de todos os usuários
     */
    List<User> findAll();

    /**
     * Busca um usuário pelo email.
     *
     * @param email Email do usuário
     * @return Optional com o usuário se encontrado
     */
    Optional<User> findByEmail(String email);

    /**
     * Busca um usuário pelo login.
     *
     * @param login Login do usuário
     * @return Optional com o usuário se encontrado
     */
    Optional<User> findByLogin(String login);

    /**
     * Busca usuários cujo nome contém a string especificada (case-insensitive).
     *
     * @param nome Parte do nome do usuário
     * @return Lista de usuários encontrados
     */
    List<User> findByNomeContainingIgnoreCase(String nome);

    /**
     * Busca um usuário pelo CPF.
     *
     * @param cpf CPF do usuário
     * @return Optional com o usuário se encontrado
     */
    Optional<User> findByCpf(String cpf);

    /**
     * Busca usuários por tipo.
     *
     * @param tipoUsuario Tipo do usuário
     * @return Lista de usuários do tipo especificado
     */
    List<User> findByTipoUsuario(UserType tipoUsuario);

    /**
     * Verifica se existe um usuário com o email especificado.
     *
     * @param email Email a verificar
     * @return true se existir, false caso contrário
     */
    boolean existsByEmail(String email);

    /**
     * Verifica se existe um usuário com o login especificado.
     *
     * @param login Login a verificar
     * @return true se existir, false caso contrário
     */
    boolean existsByLogin(String login);

    /**
     * Verifica se existe um usuário com o CPF especificado.
     *
     * @param cpf CPF a verificar
     * @return true se existir, false caso contrário
     */
    boolean existsByCpf(String cpf);

    /**
     * Verifica se existe um usuário com o ID especificado.
     *
     * @param id ID a verificar
     * @return true se existir, false caso contrário
     */
    boolean existsById(Long id);

    /**
     * Exclui um usuário pelo ID.
     *
     * @param id ID do usuário a excluir
     */
    void deleteById(Long id);
}
