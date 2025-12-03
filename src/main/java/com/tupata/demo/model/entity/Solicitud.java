package com.tupata.demo.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tupata.demo.model.enums.EstadoSolicitud;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

// Importa tus enums si los creaste en otro paquete, ej:
// import com.tupata.demo.model.enums.EstadoSolicitud;

@Entity
@Table(name = "solicitudes")
@Data
public class Solicitud {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Usuario cliente;

    @ManyToOne
    @JoinColumn(name = "trabajador_elegido_id")
    private Trabajador trabajadorElegido; // Se llena al aceptar oferta

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    @Column(nullable = false)
    private String descripcion;
    private Double precioSugerido;

    // Ubicación
    private String Direccion;
    private String referenciaDireccion;
    private Double latitud;
    private Double longitud;

    // Estado: PENDIENTE, OFERTANDO, EN_PROCESO, FINALIZADA
    @Enumerated(EnumType.STRING)
    private EstadoSolicitud estado;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @JsonIgnore
    @OneToMany(mappedBy = "solicitud", cascade = CascadeType.ALL)
    private List<Oferta> ofertas;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        if (this.estado == null) {
            this.estado = EstadoSolicitud.PENDIENTE;
        }
    }
}