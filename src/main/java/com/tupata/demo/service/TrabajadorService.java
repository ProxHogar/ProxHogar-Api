package com.tupata.demo.service;

import com.tupata.demo.model.entity.Trabajador;

public interface TrabajadorService {
    Trabajador findByUsuarioId(Long usuarioId);
}
