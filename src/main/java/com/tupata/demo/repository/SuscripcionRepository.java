package com.tupata.demo.repository;

import com.tupata.demo.model.entity.Suscripcion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface SuscripcionRepository extends JpaRepository<Suscripcion, Long> {
    // Buscar si el usuario tiene una suscripción activa
    Optional<Suscripcion> findByUsuarioIdAndActivaTrue(Long usuarioId);
}