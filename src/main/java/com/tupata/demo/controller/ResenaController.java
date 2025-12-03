package com.tupata.demo.controller;

import com.tupata.demo.dto.CrearResenaDTO;
import com.tupata.demo.model.entity.Resena;
import com.tupata.demo.service.ResenaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/resenas")
@RequiredArgsConstructor
public class ResenaController {

    private final ResenaService resenaService;

    // URL: POST /api/resenas
    @PostMapping
    public ResponseEntity<Resena> crearResena(@RequestBody CrearResenaDTO dto) {
        return ResponseEntity.ok(resenaService.crearResena(dto));
    }

    // URL: GET /api/resenas/usuario/5
    // Sirve para ver qué opinan de un trabajador antes de contratarlo
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Resena>> verResenasDeUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(resenaService.listarResenasDeUsuario(usuarioId));
    }
}