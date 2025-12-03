package com.tupata.demo.controller;

import com.tupata.demo.dto.SuscribirseDTO;
import com.tupata.demo.model.entity.Plan;
import com.tupata.demo.model.entity.Suscripcion;
import com.tupata.demo.service.SuscripcionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/suscripciones")
@RequiredArgsConstructor
public class SuscripcionController {

    private final SuscripcionService suscripcionService;

    // URL: GET /api/suscripciones/planes
    // URL Filtrada: GET /api/suscripciones/planes?rol=TRABAJADOR
    @GetMapping("/planes")
    public ResponseEntity<List<Plan>> listarPlanes(@RequestParam(required = false) String rol) {
        // CORRECCIÓN: Ahora pasamos el parámetro 'rol' al servicio
        return ResponseEntity.ok(suscripcionService.listarPlanes(rol));
    }

    // URL: POST /api/suscripciones/suscribir?usuarioId=1
    @PostMapping("/suscribir")
    public ResponseEntity<Suscripcion> suscribirse(
            @RequestParam Long usuarioId, // Sacar de Token en producción
            @RequestBody SuscribirseDTO dto) {
        return ResponseEntity.ok(suscripcionService.suscribirse(usuarioId, dto));
    }

    // URL: GET /api/suscripciones/mi-plan?usuarioId=1
    @GetMapping("/mi-plan")
    public ResponseEntity<Suscripcion> miPlan(@RequestParam Long usuarioId) {
        return ResponseEntity.ok(suscripcionService.obtenerSuscripcionActual(usuarioId));
    }
}