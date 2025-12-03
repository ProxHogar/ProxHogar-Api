package com.tupata.demo.controller;

import com.tupata.demo.dto.CrearReporteDTO;
import com.tupata.demo.model.entity.Reporte;
import com.tupata.demo.service.ReporteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reportes")
@RequiredArgsConstructor
public class ReporteController {

    private final ReporteService reporteService;

    // URL: POST /api/reportes?reportanteId=1
    @PostMapping
    public ResponseEntity<Reporte> crearReporte(
            @RequestParam Long reportanteId, // Temporal hasta tener JWT
            @RequestBody CrearReporteDTO dto) {

        return ResponseEntity.ok(reporteService.crearReporte(dto, reportanteId));
    }

    // URL: GET /api/reportes (Solo para admin)
    @GetMapping
    public ResponseEntity<List<Reporte>> listarReportes() {
        return ResponseEntity.ok(reporteService.listarReportesAbiertos());
    }
}