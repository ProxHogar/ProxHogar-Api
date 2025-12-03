package com.tupata.demo.service.impl;

import com.tupata.demo.dto.VerificacionBiometricaDTO;
import com.tupata.demo.model.entity.Solicitud;
import com.tupata.demo.model.entity.Trabajador;
import com.tupata.demo.model.entity.VerificacionBiometrica;
import com.tupata.demo.repository.SolicitudRepository;
import com.tupata.demo.repository.TrabajadorRepository;
import com.tupata.demo.repository.VerificacionBiometricaRepository;
import com.tupata.demo.service.BiometriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class BiometriaServiceImpl implements BiometriaService {

    private final VerificacionBiometricaRepository biometriaRepository;
    private final SolicitudRepository solicitudRepository;
    private final TrabajadorRepository trabajadorRepository;

    @Override
    @Transactional
    public VerificacionBiometrica verificarIdentidadEnSitio(VerificacionBiometricaDTO dto) {
        // 1. Buscar la solicitud
        Solicitud solicitud = solicitudRepository.findById(dto.getSolicitudId())
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));

        // 2. Obtener el trabajador asignado a esa solicitud
        Trabajador trabajador = solicitud.getTrabajadorElegido();
        if (trabajador == null) {
            throw new RuntimeException("Esta solicitud aún no tiene un trabajador asignado");
        }

        // 3. Simulación de Validación FACE ID (Aquí conectarías con Python/AWS)
        // Por ahora, simulamos que el 95% de las veces es correcto.
        boolean validacionIAExitosa = simularLlamadaIA(dto.getFotoSelfieUrl(), trabajador.getFotoBiometricaReferenciaUrl());
        double porcentajeConfianza = validacionIAExitosa ? 98.5 : 45.0;

        // 4. Guardar el registro
        VerificacionBiometrica registro = new VerificacionBiometrica();
        registro.setSolicitud(solicitud);
        registro.setTrabajador(trabajador);
        registro.setFotoCapturadaUrl(dto.getFotoSelfieUrl());
        registro.setNivelConfianza(porcentajeConfianza);
        registro.setExito(validacionIAExitosa);

        return biometriaRepository.save(registro);
    }

    // Método auxiliar fake
    private boolean simularLlamadaIA(String selfie, String dniFoto) {
        // TODO: Reemplazar esto con llamada HTTP real a tu microservicio de Python
        return true; // Asumimos éxito para pruebas
    }
}