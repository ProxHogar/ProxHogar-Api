package com.tupata.demo.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "trabajadores")
@Data
public class Trabajador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Vinculamos al Usuario (Dueño de la cuenta)
    @OneToOne
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    @JsonIgnoreProperties("trabajador")
    private Usuario usuario;

    private String biografia;
    private String dni;
    private boolean antecedentesPenalesVerificados;
    private Double calificacionPromedio; // Ej: 4.5
    private Integer trabajosCompletados;

    private Boolean disponible; // Botón ON/OFF

    // Datos Biométricos
    private String fotoBiometricaReferenciaUrl;
    private Boolean verificado; // Admin check

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}