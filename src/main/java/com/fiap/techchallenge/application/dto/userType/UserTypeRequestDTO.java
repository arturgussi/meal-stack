package com.fiap.techchallenge.application.dto.userType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserTypeRequestDTO {

    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 3, max = 100, message = "Nome deve ter entre 3 e 100 caracteres")
    private String name;

    public UserTypeRequestDTO() {
    }

    public UserTypeRequestDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
