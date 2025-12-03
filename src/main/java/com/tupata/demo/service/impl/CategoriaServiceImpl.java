package com.tupata.demo.service.impl;

import com.tupata.demo.model.entity.Categoria;
import com.tupata.demo.repository.CategoriaRepository;
import com.tupata.demo.service.CategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaRepository categoriaRepository;

    @Override
    public List<Categoria> listarTodas() {
        return categoriaRepository.findAll();
    }

    // Método opcional para inicializar datos si está vacío
    @Override
    public Categoria crearCategoria(String nombre, String urlIcono) {
        Categoria cat = new Categoria();
        cat.setNombre(nombre);
        cat.setUrlIcono(urlIcono);
        return categoriaRepository.save(cat);
    }
}