package com.tupata.demo.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "reportes")
@Data
public class Reporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "reportante_id", nullable = false)
    private Usuario reportante; // El "acusador"

    @ManyToOne
    @JoinColumn(name = "reportado_id", nullable = false)
    private Usuario reportado; // El "acusado"

    @ManyToOne
    @JoinColumn(name = "solicitud_id")
    private Solicitud solicitud; // Opcional, si el problema fue en un trabajo específico

    private String motivo;
    private String evidenciaUrl; // Foto o captura de pantalla

    private String estado; // "ABIERTO", "EN_REVISION", "CERRADO" (Lo dejé String para simplificar)

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        if (this.estado == null) {
            this.estado = "ABIERTO";
        }
    }
}