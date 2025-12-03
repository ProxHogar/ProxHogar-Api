package com.tupata.demo.dto;

import lombok.Data;

@Data
public class VerificacionBiometricaDTO {
    private Long solicitudId;
    private Double latitudActual; // Para asegurar que está en el lugar (Geofence básico)
    private Double longitudActual;

    // La foto selfie que se acaba de tomar para enviarla a comparar
    private String fotoSelfieUrl;
}