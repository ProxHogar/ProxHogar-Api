package com.tupata.demo.dto;

import lombok.Data;

@Data
public class ConvertirATrabajadorDTO {
    private String biografia;

    // URL de la foto del DNI o rostro que servirá de base para comparar después
    private String fotoBiometricaReferenciaUrl;
}