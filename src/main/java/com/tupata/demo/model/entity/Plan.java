package com.tupata.demo.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "planes")
@Data
public class Plan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre; // "Profesional", "Empresas"

    @Column(nullable = false)
    private Double precio; // 29.90

    private String periodo; // "/mes", "/año"

    @Column(length = 1000)
    private String features; // Guardaremos las características separadas por comas o pipes: "Feature 1|Feature 2"

    private Boolean recomendado; // Para resaltar en el frontend

    @Column(name = "rol_objetivo")
    private String rolObjetivo;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}