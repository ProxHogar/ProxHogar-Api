package com.tupata.demo.controller;

import com.tupata.demo.dto.VerificacionBiometricaDTO;
import com.tupata.demo.model.entity.VerificacionBiometrica;
import com.tupata.demo.service.BiometriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/biometria")
@RequiredArgsConstructor
public class BiometriaController {

    private final BiometriaService biometriaService;

    // URL: POST /api/biometria/verificar
    @PostMapping("/verificar")
    public ResponseEntity<VerificacionBiometrica> verificarIdentidad(@RequestBody VerificacionBiometricaDTO dto) {
        VerificacionBiometrica resultado = biometriaService.verificarIdentidadEnSitio(dto);
        return ResponseEntity.ok(resultado);
    }
}