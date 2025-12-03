package com.tupata.demo.controller;

import com.tupata.demo.dto.ConvertirATrabajadorDTO;
import com.tupata.demo.model.entity.Trabajador;
import com.tupata.demo.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    // URL: POST /api/usuarios/convertir?usuarioId=2
    // Acción: Transforma un cliente normal en un trabajador (Agricultor/Oficios)
    @PostMapping("/convertir")
    public ResponseEntity<Trabajador> convertirseEnTrabajador(
            @RequestParam Long usuarioId,
            @RequestBody ConvertirATrabajadorDTO dto) {

        Trabajador nuevoTrabajador = usuarioService.convertirEnTrabajador(usuarioId, dto);
        return ResponseEntity.ok(nuevoTrabajador);
    }

    // Aquí podrías agregar en el futuro: GET /perfil, PUT /actualizar-datos, etc.
}