package com.fiap.techchallenge.application.dto.user;

import com.fiap.techchallenge.domain.enums.UserType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UserRequestDTO {

    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 3, max = 100, message = "Nome deve ter entre 3 e 100 caracteres")
    private String name;

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email deve ser válido")
    @Size(max = 100, message = "Email deve ter no máximo 100 caracteres")
    private String email;

    @NotBlank(message = "Login é obrigatório")
    @Size(min = 3, max = 50, message = "Login deve ter entre 3 e 50 caracteres")
    private String login;

    @NotBlank(message = "Senha é obrigatória")
    @Size(min = 6, max = 255, message = "Senha deve ter entre 6 e 255 caracteres")
    private String password;

    @NotBlank(message = "CPF é obrigatório")
    @Size(min = 11, max = 11, message = "CPF deve ter 11 dígitos")
    private String cpf;

    @NotNull(message = "Tipo de usuário é obrigatório")
    private UserType userType;

    private String streetAddress;

    private Integer numberAddress;

    @Size(max = 100, message = "Cidade deve ter no máximo 100 caracteres")
    private String cityAddress;

    @Size(min = 8, max = 8, message = "CEP deve ter 8 dígitos")
    private String cepAddress;

    public UserRequestDTO() {
    }

    public UserRequestDTO(String name, String email, String login, String password, String cpf,
                          UserType userType, String streetAddress, Integer numberAddress,
                          String cityAddress, String cepAddress) {
        this.name = name;
        this.email = email;
        this.login = login;
        this.password = password;
        this.cpf = cpf;
        this.userType = userType;
        this.streetAddress = streetAddress;
        this.numberAddress = numberAddress;
        this.cityAddress = cityAddress;
        this.cepAddress = cepAddress;
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
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
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
}
