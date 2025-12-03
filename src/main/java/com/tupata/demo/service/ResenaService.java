package com.tupata.demo.service;

import com.tupata.demo.dto.CrearResenaDTO;
import com.tupata.demo.model.entity.Resena;

import java.util.List;

public interface ResenaService {
    Resena crearResena(CrearResenaDTO dto);

    // Obtener todas las reseñas de un trabajador para mostrar en su perfil
    List<Resena> listarResenasDeUsuario(Long usuarioId);
}