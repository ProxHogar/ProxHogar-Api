package com.tupata.demo.repository;

import com.tupata.demo.model.entity.VerificacionBiometrica;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificacionBiometricaRepository extends JpaRepository<VerificacionBiometrica, Long> {
    // Ver si ya se validó biometría para una solicitud
    boolean existsBySolicitudIdAndExitoTrue(Long solicitudId);
}