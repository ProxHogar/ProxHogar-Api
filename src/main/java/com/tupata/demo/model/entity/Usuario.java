package com.tupata.demo.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "usuarios")
@Data
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private String nombreCompleto;
    private String telefono;
    private String fcmToken; // Para notificaciones

    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @JsonIgnore
    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL)
    private Trabajador trabajador;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}