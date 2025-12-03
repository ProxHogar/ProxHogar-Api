package com.tupata.demo.service;

import com.tupata.demo.dto.SuscribirseDTO;
import com.tupata.demo.model.entity.Plan;
import com.tupata.demo.model.entity.Suscripcion;
import java.util.List;

public interface SuscripcionService {
    List<Plan> listarPlanes(String rol);

    Suscripcion suscribirse(Long usuarioId, SuscribirseDTO dto);

    Suscripcion obtenerSuscripcionActual(Long usuarioId);
}