package com.fiap.techchallenge.infrastructure.persistence.entity;

import com.fiap.techchallenge.domain.entities.UserType;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_usuarios")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long id;

    @Column(name = "nm_usuario")
    private String name;
    @Column(name = "ds_email")
    private String email;
    @Column(name = "ds_login")
    private String login;
    @Column(name = "ds_senha")
    private String password;
    @Column(name = "nr_cpf")
    private String cpf;

    @ManyToOne
    @JoinColumn(name = "id_tipo_usuario")
    private UserTypeEntity userType;

    @Column(name = "ds_endereco_rua")
    private String streetAddress;
    @Column(name = "nr_endereco_numero")
    private Integer numberAddress;
    @Column(name = "ds_endereco_cidade")
    private String cityAddress;
    @Column(name = "nr_endereco_cep")
    private String cepAddress;

    @Column(name = "dt_criacao", insertable = false, updatable = false)
    private LocalDateTime createdAt;
    @Column(name = "dt_atualizacao", insertable = false, updatable = false)
    private LocalDateTime updatedAt;

    public UserEntity() {
    }

    public UserEntity(Long id, String name, String email, String login, String password, String cpf, UserType userType,
            String streetAddress, Integer numberAddress, String cityAddress, String cepAddress, LocalDateTime createdAt,
            LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.login = login;
        this.password = password;
        this.cpf = cpf;
        setUserType(userType);
        this.streetAddress = streetAddress;
        this.numberAddress = numberAddress;
        this.cityAddress = cityAddress;
        this.cepAddress = cepAddress;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public UserType getUserType() {
        if (this.userType == null) {
            return null;
        }
        return new UserType(this.userType.getId(), this.userType.getName(), this.userType.getCreatedAt(), this.userType.getUpdatedAt());
    }

    public void setUserType(UserType userType) {
        if (userType == null) {
            this.userType = null;
        } else {
            this.userType = new UserTypeEntity(userType.getId(), userType.getName(), userType.getCreatedAt(), userType.getUpdatedAt());
        }
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public Integer getNumberAddress() {
        return numberAddress;
    }

    public void setNumberAddress(Integer numberAddress) {
        this.numberAddress = numberAddress;
    }

    public String getCityAddress() {
        return cityAddress;
    }

    public void setCityAddress(String cityAddress) {
        this.cityAddress = cityAddress;
    }

    public String getCepAddress() {
        return cepAddress;
    }

    public void setCepAddress(String cepAddress) {
        this.cepAddress = cepAddress;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
