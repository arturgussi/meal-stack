package com.fiap.techchallenge.domain.entities;

import com.fiap.techchallenge.domain.enums.TipoUsuario;

import java.time.LocalDateTime;

/**
 * Entidade que representa um usuário no sistema.
 */
public class Usuario {

    private Long id;
    private String nome;
    private String email;
    private String login;
    private String senha;
    private String cpf;
    private TipoUsuario tipoUsuario;
    private String enderecoRua;
    private String enderecoNumero;
    private String enderecoCidade;
    private String enderecoCep;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;

    public Usuario() {
    }

    public Usuario(String nome, String email, String login, String senha, String cpf,
            TipoUsuario tipoUsuario, String enderecoRua, String enderecoNumero,
            String enderecoCidade, String enderecoCep) {
        this.nome = nome;
        this.email = email;
        this.login = login;
        this.senha = senha;
        this.cpf = cpf;
        this.tipoUsuario = tipoUsuario;
        this.enderecoRua = enderecoRua;
        this.enderecoNumero = enderecoNumero;
        this.enderecoCidade = enderecoCidade;
        this.enderecoCep = enderecoCep;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public String getEnderecoRua() {
        return enderecoRua;
    }

    public void setEnderecoRua(String enderecoRua) {
        this.enderecoRua = enderecoRua;
    }

    public String getEnderecoNumero() {
        return enderecoNumero;
    }

    public void setEnderecoNumero(String enderecoNumero) {
        this.enderecoNumero = enderecoNumero;
    }

    public String getEnderecoCidade() {
        return enderecoCidade;
    }

    public void setEnderecoCidade(String enderecoCidade) {
        this.enderecoCidade = enderecoCidade;
    }

    public String getEnderecoCep() {
        return enderecoCep;
    }

    public void setEnderecoCep(String enderecoCep) {
        this.enderecoCep = enderecoCep;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", login='" + login + '\'' +
                ", cpf='" + cpf + '\'' +
                ", tipoUsuario=" + tipoUsuario +
                ", dataCriacao=" + dataCriacao +
                ", dataAtualizacao=" + dataAtualizacao +
                '}';
    }
}
