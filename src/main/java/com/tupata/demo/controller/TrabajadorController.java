package com.tupata.demo.controller;

import com.tupata.demo.model.entity.Trabajador;
import com.tupata.demo.service.TrabajadorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/trabajadores")
@RequiredArgsConstructor
public class TrabajadorController {

    private final TrabajadorService trabajadorService;

    // URL: GET /api/trabajadores/usuario/5
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<?> obtenerPorUsuario(@PathVariable Long usuarioId) {
        try {
            Trabajador trabajador = trabajadorService.obtenerPorUsuarioId(usuarioId);
            return ResponseEntity.ok(trabajador);
        } catch (RuntimeException e) {
            // Capturamos el error y devolvemos 404 en lugar de 500
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error interno: " + e.getMessage());
        }
    }
}
