package com.fiap.techchallenge.infrastructure.persistence.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_tipo_usuario")
public class UserTypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_usuario")
    private Long id;

    @Column(name = "nm_tipo_usuario", nullable = false, length = 50)
    private String name;

    @Column(name = "dt_criacao", nullable = false, updatable = false, insertable = false)
    private LocalDateTime createdAt;

    @Column(name = "dt_atualizacao", nullable = false, updatable = false, insertable = false)
    private LocalDateTime updatedAt;

    public UserTypeEntity() {
    }

    public UserTypeEntity(Long id, String name, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

}
