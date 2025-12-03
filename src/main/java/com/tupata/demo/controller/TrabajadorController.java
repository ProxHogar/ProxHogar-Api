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

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<Trabajador> getTrabajadorByUsuarioId(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(trabajadorService.findByUsuarioId(usuarioId));
    }
}
