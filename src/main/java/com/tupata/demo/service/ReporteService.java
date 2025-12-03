package com.tupata.demo.service;

import com.tupata.demo.dto.CrearReporteDTO;
import com.tupata.demo.model.entity.Reporte;
import java.util.List;

public interface ReporteService {

    // Crear una denuncia
    Reporte crearReporte(CrearReporteDTO dto, Long reportanteId);

    // Ver denuncias pendientes (Para panel administrativo)
    List<Reporte> listarReportesAbiertos();
}