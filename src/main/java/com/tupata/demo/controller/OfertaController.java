package com.tupata.demo.controller;

import com.tupata.demo.dto.CrearOfertaDTO;
import com.tupata.demo.model.entity.Oferta;
import com.tupata.demo.service.OfertaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ofertas")
@RequiredArgsConstructor
public class OfertaController {

    private final OfertaService ofertaService;

    // 1. Enviar una oferta/propuesta (Trabajador)
    // URL: POST /api/ofertas?trabajadorUsuarioId=2
    @PostMapping
    public ResponseEntity<Oferta> crearOferta(
            @RequestParam Long trabajadorUsuarioId,
            @RequestBody CrearOfertaDTO dto) {

        return ResponseEntity.ok(ofertaService.crearOferta(dto, trabajadorUsuarioId));
    }

    // 2. Ver quiénes han ofertado a mi pedido (Cliente)
    // URL: GET /api/ofertas/solicitud/10
    @GetMapping("/solicitud/{solicitudId}")
    public ResponseEntity<List<Oferta>> listarOfertasDeSolicitud(@PathVariable Long solicitudId) {
        return ResponseEntity.ok(ofertaService.listarOfertasDeSolicitud(solicitudId));
    }

    // 3. ACEPTAR una oferta y cerrar el trato (Cliente)
    // URL: PUT /api/ofertas/5/aceptar
    @PutMapping("/{ofertaId}/aceptar")
    public ResponseEntity<Void> aceptarOferta(@PathVariable Long ofertaId) {
        ofertaService.aceptarOferta(ofertaId);
        return ResponseEntity.ok().build();
    }
}
