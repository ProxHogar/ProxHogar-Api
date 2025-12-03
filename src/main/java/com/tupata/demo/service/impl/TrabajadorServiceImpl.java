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
    public Trabajador findByUsuarioId(Long usuarioId) {
        return trabajadorRepository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new RuntimeException("Trabajador no encontrado para el usuario con ID: " + usuarioId));
    }
}
