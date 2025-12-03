package com.tupata.demo.service;

import com.tupata.demo.dto.CrearSolicitudDTO;
import com.tupata.demo.model.entity.Solicitud;
import java.util.List;

public interface SolicitudService {
    Solicitud crearSolicitud(CrearSolicitudDTO dto, Long clienteId);
    List<Solicitud> listarSolicitudesPendientes();
    List<Solicitud> listarMisSolicitudes(Long clienteId);
    Solicitud obtenerPorId(Long id);
    void cancelarSolicitud(Long solicitudId, Long clienteId);
    Solicitud actualizarSolicitud(Long id, CrearSolicitudDTO dto, Long clienteId);
    void finalizarSolicitud(Long solicitudId, Long clienteId);
    List<Solicitud> listarMisTrabajos(Long usuarioId);
    void iniciarSolicitud(Long solicitudId, Long trabajadorUsuarioId);
    void trabajadorFinalizarSolicitud(Long solicitudId, Long trabajadorUsuarioId);
}