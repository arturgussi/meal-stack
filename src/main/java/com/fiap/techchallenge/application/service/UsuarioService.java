package com.fiap.techchallenge.application.service;

import com.fiap.techchallenge.application.dto.AlterarSenhaDTO;
import com.fiap.techchallenge.application.dto.LoginDTO;
import com.fiap.techchallenge.application.dto.UsuarioRequestDTO;
import com.fiap.techchallenge.application.dto.UsuarioResponseDTO;

import java.util.List;

public interface UsuarioService {

    /**
     * Cria um novo usuário.
     * Regra de negócio: Valida se o email já existe.
     *
     * @param dto Dados do usuário a ser criado
     * @return Usuário criado
     * @throws RegraNegocioException se email já estiver cadastrado
     */
    UsuarioResponseDTO criar(UsuarioRequestDTO dto);

    /**
     * Busca um usuário por ID.
     *
     * @param id ID do usuário
     * @return Usuário encontrado
     * @throws RecursoNaoEncontradoException se não encontrar
     */
    UsuarioResponseDTO buscarPorId(Long id);

    /**
     * Busca usuários por nome (busca parcial).
     *
     * @param nome Nome ou parte dele
     * @return Lista de usuários encontrados
     */
    List<UsuarioResponseDTO> buscarPorNome(String nome);

    /**
     * Lista todos os usuários.
     *
     * @return Lista de todos os usuários
     */
    List<UsuarioResponseDTO> listarTodos();

    /**
     * Atualiza dados cadastrais de um usuário (exceto senha).
     * Regra: CPF e Login são imutáveis após criação.
     *
     * @param id  ID do usuário
     * @param dto Dados atualizados
     * @return Usuário atualizado
     * @throws RecursoNaoEncontradoException se não encontrar
     * @throws RegraNegocioException         se tentar alterar email para um já
     *                                       existente
     */
    UsuarioResponseDTO atualizar(Long id, UsuarioRequestDTO dto);

    /**
     * Altera a senha de um usuário.
     * Endpoint exclusivo conforme specs_tech_challenge.md
     * Regra: Valida a senha atual antes de trocar.
     *
     * @param id  ID do usuário
     * @param dto Dados com senha atual e nova senha
     * @throws RecursoNaoEncontradoException se não encontrar
     * @throws SenhaInvalidaException        se a senha atual não conferir
     */
    void alterarSenha(Long id, AlterarSenhaDTO dto);

    /**
     * Valida credenciais de login.
     * Conforme specs_tech_challenge.md - validação simples na Fase 1.
     *
     * @param dto Credenciais de login
     * @return Usuário autenticado
     * @throws SenhaInvalidaException se credenciais inválidas
     */
    UsuarioResponseDTO validarLogin(LoginDTO dto);

    /**
     * Exclui um usuário.
     *
     * @param id ID do usuário
     * @throws RecursoNaoEncontradoException se não encontrar
     */
    void excluir(Long id);
}
