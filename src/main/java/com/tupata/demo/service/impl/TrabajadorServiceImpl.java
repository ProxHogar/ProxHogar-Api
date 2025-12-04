package com.tupata.demo.service.impl;

import com.tupata.demo.model.entity.Trabajador;
import com.tupata.demo.repository.TrabajadorRepository;
import com.tupata.demo.service.TrabajadorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TrabajadorServiceImpl implements TrabajadorService {

    private final TrabajadorRepository trabajadorRepository;

    @Override
    public Trabajador obtenerPorUsuarioId(Long usuarioId) {
        // Buscamos el perfil de trabajador asociado a ese usuario
        return trabajadorRepository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new RuntimeException("El usuario con ID " + usuarioId + " no tiene perfil de trabajador activo."));
    }
}
