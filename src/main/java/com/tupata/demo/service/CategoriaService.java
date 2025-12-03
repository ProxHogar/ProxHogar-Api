package com.tupata.demo.service;

import com.tupata.demo.model.entity.Categoria;
import java.util.List;

public interface CategoriaService {

    // Para mostrar en el Home de la App
    List<Categoria> listarTodas();

    // Para crear nuevas categorías (usualmente desde un panel admin o seeders)
    Categoria crearCategoria(String nombre, String urlIcono);
}