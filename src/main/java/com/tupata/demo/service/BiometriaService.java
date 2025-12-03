package com.tupata.demo.service;

import com.tupata.demo.dto.VerificacionBiometricaDTO;
import com.tupata.demo.model.entity.VerificacionBiometrica;

public interface BiometriaService {
    // Recibe la selfie y las coordenadas, devuelve el registro de verificación
    VerificacionBiometrica verificarIdentidadEnSitio(VerificacionBiometricaDTO dto);
}