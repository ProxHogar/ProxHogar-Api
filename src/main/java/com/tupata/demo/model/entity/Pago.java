package com.tupata.demo.model.entity;

import com.tupata.demo.model.enums.EstadoPago;
import com.tupata.demo.model.enums.MetodoPago;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

// Asumimos que tienes estos Enums, si no, cámbialos a String
// import com.tupata.demo.model.enums.MetodoPago;
// import com.tupata.demo.model.enums.EstadoPago;

@Entity
@Table(name = "pagos")
@Data
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Un pago pertenece a una solicitud única
    @OneToOne
    @JoinColumn(name = "solicitud_id", nullable = false, unique = true)
    private Solicitud solicitud;

    private Double monto;

    @Enumerated(EnumType.STRING)
    private MetodoPago metodo; // YAPE, PLIN, EFECTIVO

    private String codigoTransaccion; // El código que genera la app del banco

    @Enumerated(EnumType.STRING)
    private EstadoPago estado; // PENDIENTE, PAGADO

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}