package com.fiap.techchallenge.application.dto.userType;

public class UserTypeResponseDTO {
    private Long id;
    private String name;

    public UserTypeResponseDTO() {
    }

    public UserTypeResponseDTO(Long id, String name) {
        this.id = id;
        this.name = name;
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
}
