package com.tupata.demo.repository;

import com.tupata.demo.model.entity.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PagoRepository extends JpaRepository<Pago, Long> {
    // Buscar el pago asociado a una solicitud específica
    Optional<Pago> findBySolicitudId(Long solicitudId);
}