package com.tupata.demo.model.entity;

import com.tupata.demo.model.enums.EstadoOferta;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

// Importa tu enum EstadoOferta
// import com.tupata.demo.model.enums.EstadoOferta;

@Entity
@Table(name = "ofertas")
@Data
public class Oferta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "solicitud_id", nullable = false)
    private Solicitud solicitud;

    @ManyToOne
    @JoinColumn(name = "trabajador_id", nullable = false)
    private Trabajador trabajador;

    private Double montoOfrecido; // Contraoferta
    private String comentario; // "Llego en 10 min"

    @Enumerated(EnumType.STRING)
    private EstadoOferta estado; // PENDIENTE, ACEPTADA, RECHAZADA

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        if (this.estado == null) {
            this.estado = EstadoOferta.PENDIENTE;
        }
    }
}