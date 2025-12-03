package com.tupata.demo.controller;

import com.tupata.demo.model.entity.Categoria;
import com.tupata.demo.service.CategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaService categoriaService;

    // URL: GET /api/categorias
    @GetMapping
    public ResponseEntity<List<Categoria>> listarTodas() {
        return ResponseEntity.ok(categoriaService.listarTodas());
    }

    // URL: POST /api/categorias (Para inicializar datos)
    @PostMapping
    public ResponseEntity<Categoria> crearCategoria(@RequestParam String nombre, @RequestParam String urlIcono) {
        return ResponseEntity.ok(categoriaService.crearCategoria(nombre, urlIcono));
    }
}