package com.tupata.demo.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "verificaciones_biometricas")
@Data
public class VerificacionBiometrica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "solicitud_id", nullable = false)
    private Solicitud solicitud; // Vinculado al trabajo específico

    @ManyToOne
    @JoinColumn(name = "trabajador_id", nullable = false)
    private Trabajador trabajador;

    private String fotoCapturadaUrl; // La selfie tomada en el sitio

    // Nivel de confianza que devuelve la IA (Ej: 98.50)
    private Double nivelConfianza;

    private Boolean exito; // true si pasó, false si falló

    @Column(name = "created_at")
    private LocalDateTime createdAt; // Fecha y hora exacta del escaneo

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}