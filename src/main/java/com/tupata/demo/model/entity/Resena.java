package com.tupata.demo.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "resenas")
@Data
public class Resena {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "solicitud_id", nullable = false)
    private Solicitud solicitud;

    @ManyToOne
    @JoinColumn(name = "autor_id", nullable = false)
    private Usuario autor; // Quién escribe la reseña

    @ManyToOne
    @JoinColumn(name = "receptor_id", nullable = false)
    private Usuario receptor; // A quién califican (puede ser el trabajador o el cliente)

    private Integer calificacion; // 1 a 5
    private String comentario;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}