package com.tupata.demo.repository;

import com.tupata.demo.model.entity.Solicitud;
import com.tupata.demo.model.enums.EstadoSolicitud;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SolicitudRepository extends JpaRepository<Solicitud, Long> {
    // Ver el historial de pedidos de un cliente
    List<Solicitud> findByClienteId(Long clienteId);

    // Ver solicitudes activas para mostrar en el mapa a los trabajadores
    List<Solicitud> findByEstadoIn(List<EstadoSolicitud> estados);

    // Ver trabajos asignados a un trabajador específico
    List<Solicitud> findByTrabajadorElegidoId(Long trabajadorId);

    // Ver trabajos asignados a un trabajador por su ID de USUARIO
    List<Solicitud> findByTrabajadorElegido_Usuario_Id(Long usuarioId);
}