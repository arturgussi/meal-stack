package com.fiap.techchallenge.domain.entities;

import java.time.LocalDateTime;

/**
 * Entidade que representa o tipo de um usuário (ex: CLIENTE, DONO_RESTAURANTE).
 */
public class UserType {
    private Long id;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public UserType() {
    }

    public UserType(Long id, String name, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UserType(Long id, String name) {
        this.id = id;
        this.name = name;
        this.createdAt = null;
        this.updatedAt = null;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserType other)) return false;
        return java.util.Objects.equals(id, other.id) &&
               java.util.Objects.equals(name, other.name);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "UserType{" +
                "id=" + id +
                ", name=" + name +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
