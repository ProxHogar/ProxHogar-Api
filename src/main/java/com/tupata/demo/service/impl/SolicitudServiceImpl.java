package com.tupata.demo.service.impl;

import com.tupata.demo.dto.CrearSolicitudDTO;
import com.tupata.demo.model.entity.Categoria;
import com.tupata.demo.model.entity.Solicitud;
import com.tupata.demo.model.entity.Usuario;
import com.tupata.demo.model.enums.EstadoSolicitud;
import com.tupata.demo.repository.CategoriaRepository;
import com.tupata.demo.repository.SolicitudRepository;
import com.tupata.demo.repository.UsuarioRepository;
import com.tupata.demo.service.SolicitudService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SolicitudServiceImpl implements SolicitudService {

    private final SolicitudRepository solicitudRepository;
    private final UsuarioRepository usuarioRepository;
    private final CategoriaRepository categoriaRepository;

    @Override
    @Transactional
    public Solicitud crearSolicitud(CrearSolicitudDTO dto, Long clienteId) {
        Usuario cliente = usuarioRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        Solicitud solicitud = new Solicitud();
        solicitud.setCliente(cliente);
        solicitud.setCategoria(categoria);
        solicitud.setDescripcion(dto.getDescripcion()); // Ojo: asegúrate que en DTO sea getDescripcion
        solicitud.setPrecioSugerido(dto.getPrecioSugerido());

        // Ubicación Completa (Lat/Long + Texto)
        solicitud.setLatitud(dto.getLatitud());
        solicitud.setLongitud(dto.getLongitud());
        solicitud.setDireccion(dto.getDireccion()); // Campo nuevo
        solicitud.setReferenciaDireccion(dto.getReferencia());

        solicitud.setEstado(EstadoSolicitud.PENDIENTE);

        return solicitudRepository.save(solicitud);
    }

    @Override
    public List<Solicitud> listarSolicitudesPendientes() {
        // Devuelve las que están PENDIENTES o recibiendo OFERTAS
        return solicitudRepository.findByEstadoIn(
                List.of(EstadoSolicitud.PENDIENTE, EstadoSolicitud.OFERTANDO)
        );
    }

    @Override
    public List<Solicitud> listarMisSolicitudes(Long clienteId) {
        return solicitudRepository.findByClienteId(clienteId);
    }

    @Override
    public List<Solicitud> listarMisTrabajos(Long usuarioId) {
        return solicitudRepository.findByTrabajadorElegido_Usuario_Id(usuarioId);
    }

    @Override
    public Solicitud obtenerPorId(Long id) {
        return solicitudRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));
    }
    @Override
    @Transactional
    public void cancelarSolicitud(Long solicitudId, Long clienteId) {
        Solicitud solicitud = solicitudRepository.findById(solicitudId)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));

        if (!solicitud.getCliente().getId().equals(clienteId)) {
            throw new RuntimeException("No tienes permiso para eliminar esta solicitud");
        }

        // Solo permitimos eliminar si no ha iniciado el trabajo
        if (solicitud.getEstado() == EstadoSolicitud.EN_PROCESO || solicitud.getEstado() == EstadoSolicitud.FINALIZADA) {
            throw new RuntimeException("No se puede cancelar un trabajo en curso o finalizado");
        }

        // Opción A: Borrado Físico (Desaparece de la BD)
        solicitudRepository.delete(solicitud);

        // Opción B: Borrado Lógico (Recomendado para historial) -> Descomentar si prefieres esto
        // solicitud.setEstado(EstadoSolicitud.CANCELADA);
        // solicitudRepository.save(solicitud);
    }
    @Override
    @Transactional
    public void finalizarSolicitud(Long solicitudId, Long clienteId) {
        Solicitud solicitud = solicitudRepository.findById(solicitudId)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));

        if (!solicitud.getCliente().getId().equals(clienteId)) {
            throw new RuntimeException("No tienes permiso para finalizar esta solicitud");
        }

        // Solo se finaliza si está EN_PROCESO o FINALIZADO_POR_TRABAJADOR
        if (solicitud.getEstado() != EstadoSolicitud.EN_PROCESO && solicitud.getEstado() != EstadoSolicitud.FINALIZADO_POR_TRABAJADOR) {
            throw new RuntimeException("Esta solicitud no se puede finalizar porque no está en proceso o el trabajador aún no la ha marcado como terminada.");
        }

        solicitud.setEstado(EstadoSolicitud.FINALIZADA);
        solicitudRepository.save(solicitud);
    }

    @Override
    @Transactional
    public void iniciarSolicitud(Long solicitudId, Long trabajadorUsuarioId) {
        Solicitud solicitud = solicitudRepository.findById(solicitudId)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));

        // Validar que el trabajador asignado sea quien inicia el trabajo
        if (solicitud.getTrabajadorElegido() == null || !solicitud.getTrabajadorElegido().getUsuario().getId().equals(trabajadorUsuarioId)) {
            throw new RuntimeException("No tienes permiso para iniciar esta solicitud");
        }

        // Solo se inicia si está en estado ACEPTADO
        if (solicitud.getEstado() != EstadoSolicitud.ACEPTADO) {
            throw new RuntimeException("Esta solicitud no puede ser iniciada.");
        }

        solicitud.setEstado(EstadoSolicitud.EN_PROCESO);
        solicitudRepository.save(solicitud);
    }

    @Override
    @Transactional
    public void trabajadorFinalizarSolicitud(Long solicitudId, Long trabajadorUsuarioId) {
        Solicitud solicitud = solicitudRepository.findById(solicitudId)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));

        // Validar que el trabajador asignado sea quien finaliza el trabajo
        if (solicitud.getTrabajadorElegido() == null || !solicitud.getTrabajadorElegido().getUsuario().getId().equals(trabajadorUsuarioId)) {
            throw new RuntimeException("No tienes permiso para finalizar esta solicitud");
        }

        // Solo se finaliza si está EN_PROCESO
        if (solicitud.getEstado() != EstadoSolicitud.EN_PROCESO) {
            throw new RuntimeException("Esta solicitud no puede ser finalizada porque no está en proceso.");
        }

        solicitud.setEstado(EstadoSolicitud.FINALIZADO_POR_TRABAJADOR);
        solicitudRepository.save(solicitud);
    }

    @Override
    @Transactional
    public Solicitud actualizarSolicitud(Long id, CrearSolicitudDTO dto, Long clienteId) {
        // 1. Buscar la solicitud
        Solicitud solicitud = solicitudRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));

        // 2. Validar que el usuario sea el dueño
        if (!solicitud.getCliente().getId().equals(clienteId)) {
            throw new RuntimeException("No tienes permiso para editar esta solicitud");
        }

        // 3. Validar estado (Solo se edita si nadie la ha tomado aún)
        if (solicitud.getEstado() != EstadoSolicitud.PENDIENTE) {
            throw new RuntimeException("No se puede editar una solicitud que ya está en proceso o finalizada");
        }

        // 4. Actualizar datos
        // Nota: Si cambias la categoría, tendrías que buscarla de nuevo en el repo
        if (dto.getCategoriaId() != null) {
            Categoria nuevaCat = categoriaRepository.findById(dto.getCategoriaId())
                    .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
            solicitud.setCategoria(nuevaCat);
        }

        solicitud.setDescripcion(dto.getDescripcion());
        solicitud.setPrecioSugerido(dto.getPrecioSugerido());
        solicitud.setDireccion(dto.getDireccion());
        solicitud.setReferenciaDireccion(dto.getReferencia());
        solicitud.setLatitud(dto.getLatitud());
        solicitud.setLongitud(dto.getLongitud());

        return solicitudRepository.save(solicitud);
    }
}