package com.tupata.demo.controller;

import com.tupata.demo.dto.CrearSolicitudDTO;
import com.tupata.demo.model.entity.Solicitud;
import com.tupata.demo.service.SolicitudService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/solicitudes")
@RequiredArgsConstructor
public class SolicitudController {

    private final SolicitudService solicitudService;

    // 1. Crear un pedido (Cliente)
    // URL: POST /api/solicitudes?clienteId=1
    @PostMapping
    public ResponseEntity<Solicitud> crearSolicitud(
            @RequestParam Long clienteId,
            @RequestBody CrearSolicitudDTO dto) {

        return ResponseEntity.ok(solicitudService.crearSolicitud(dto, clienteId));
    }

    // 2. Ver trabajos disponibles en el mapa/lista (Trabajador)
    // URL: GET /api/solicitudes/disponibles
    @GetMapping("/disponibles")
    public ResponseEntity<List<Solicitud>> listarDisponibles() {
        return ResponseEntity.ok(solicitudService.listarSolicitudesPendientes());
    }

    // 3. Ver mis pedidos anteriores (Cliente)
    // URL: GET /api/solicitudes/mis-pedidos?clienteId=1
    @GetMapping("/mis-pedidos")
    public ResponseEntity<List<Solicitud>> misPedidos(@RequestParam Long clienteId) {
        return ResponseEntity.ok(solicitudService.listarMisSolicitudes(clienteId));
    }

    // URL: GET /api/solicitudes/mis-trabajos?usuarioId=2
    @GetMapping("/mis-trabajos")
    public ResponseEntity<List<Solicitud>> misTrabajos(@RequestParam Long usuarioId) {
        return ResponseEntity.ok(solicitudService.listarMisTrabajos(usuarioId));
    }

    // 4. Ver detalle de una solicitud (Para ver quién ganó, precio final, etc.)
    // URL: GET /api/solicitudes/15
    @GetMapping("/{id}")
    public ResponseEntity<Solicitud> verDetalle(@PathVariable Long id) {
        return ResponseEntity.ok(solicitudService.obtenerPorId(id));
    }
    // URL: DELETE /api/solicitudes/{id}?clienteId=1
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarSolicitud(
            @PathVariable Long id,
            @RequestParam Long clienteId) {

        solicitudService.cancelarSolicitud(id, clienteId);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{id}")
    public ResponseEntity<Solicitud> actualizarSolicitud(
            @PathVariable Long id,
            @RequestParam Long clienteId, // Necesario para validar propiedad
            @RequestBody CrearSolicitudDTO dto) {

        return ResponseEntity.ok(solicitudService.actualizarSolicitud(id, dto, clienteId));
    }

    @PutMapping("/{id}/finalizar")
    public ResponseEntity<Void> finalizarSolicitud(
            @PathVariable Long id,
            @RequestParam Long clienteId) {
        solicitudService.finalizarSolicitud(id, clienteId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/iniciar")
    public ResponseEntity<Void> iniciarSolicitud(
            @PathVariable Long id,
            @RequestParam Long trabajadorUsuarioId) {
        solicitudService.iniciarSolicitud(id, trabajadorUsuarioId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/trabajador-finalizar")
    public ResponseEntity<Void> trabajadorFinalizarSolicitud(
            @PathVariable Long id,
            @RequestParam Long trabajadorUsuarioId) {
        solicitudService.trabajadorFinalizarSolicitud(id, trabajadorUsuarioId);
        return ResponseEntity.ok().build();
    }
}