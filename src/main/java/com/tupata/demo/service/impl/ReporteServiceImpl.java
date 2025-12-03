package com.tupata.demo.service.impl;

import com.tupata.demo.dto.CrearReporteDTO;
import com.tupata.demo.model.entity.Reporte;
import com.tupata.demo.model.entity.Usuario;
import com.tupata.demo.repository.ReporteRepository;
import com.tupata.demo.repository.UsuarioRepository;
import com.tupata.demo.service.ReporteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReporteServiceImpl implements ReporteService {

    private final ReporteRepository reporteRepository;
    private final UsuarioRepository usuarioRepository;

    @Override
    public Reporte crearReporte(CrearReporteDTO dto, Long reportanteId) {
        Usuario reportante = usuarioRepository.findById(reportanteId)
                .orElseThrow(() -> new RuntimeException("Usuario reportante no encontrado"));

        Usuario reportado = usuarioRepository.findById(dto.getUsuarioReportadoId())
                .orElseThrow(() -> new RuntimeException("Usuario reportado no encontrado"));

        Reporte reporte = new Reporte();
        reporte.setReportante(reportante);
        reporte.setReportado(reportado);
        reporte.setMotivo(dto.getMotivo());
        reporte.setEvidenciaUrl(dto.getEvidenciaUrl());
        reporte.setEstado("ABIERTO");

        return reporteRepository.save(reporte);
    }

    @Override
    public List<Reporte> listarReportesAbiertos() {
        return reporteRepository.findByEstado("ABIERTO");
    }
}