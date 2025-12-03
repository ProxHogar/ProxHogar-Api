package com.tupata.demo.service.impl;

import com.tupata.demo.dto.CrearOfertaDTO;
import com.tupata.demo.model.entity.Oferta;
import com.tupata.demo.model.entity.Solicitud;
import com.tupata.demo.model.entity.Trabajador;
import com.tupata.demo.model.enums.EstadoOferta;
import com.tupata.demo.model.enums.EstadoSolicitud;
import com.tupata.demo.repository.OfertaRepository;
import com.tupata.demo.repository.SolicitudRepository;
import com.tupata.demo.repository.TrabajadorRepository;
import com.tupata.demo.service.OfertaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OfertaServiceImpl implements OfertaService {

    private final OfertaRepository ofertaRepository;
    private final SolicitudRepository solicitudRepository;
    private final TrabajadorRepository trabajadorRepository;

    @Override
    @Transactional
    public Oferta crearOferta(CrearOfertaDTO dto, Long usuarioId) {
        Trabajador trabajador = trabajadorRepository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new RuntimeException("El usuario no es trabajador o no existe"));

        Solicitud solicitud = solicitudRepository.findById(dto.getSolicitudId())
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));

        if (solicitud.getEstado() == EstadoSolicitud.EN_PROCESO || solicitud.getEstado() == EstadoSolicitud.FINALIZADA) {
            throw new RuntimeException("Esta solicitud ya fue tomada");
        }

        Oferta oferta = new Oferta();
        oferta.setSolicitud(solicitud);
        oferta.setTrabajador(trabajador);
        oferta.setMontoOfrecido(dto.getMontoOfrecido());
        oferta.setComentario(dto.getComentario());
        oferta.setEstado(EstadoOferta.PENDIENTE);

        if (solicitud.getEstado() == EstadoSolicitud.PENDIENTE) {
            solicitud.setEstado(EstadoSolicitud.OFERTANDO);
            solicitudRepository.save(solicitud);
        }

        return ofertaRepository.save(oferta);
    }

    @Override
    public List<Oferta> listarOfertasDeSolicitud(Long solicitudId) {
        return ofertaRepository.findBySolicitudIdOrderByTrabajadorVerificadoDesc(solicitudId);
    }

    @Override
    @Transactional
    public void aceptarOferta(Long ofertaId) {
        Oferta ofertaGanadora = ofertaRepository.findById(ofertaId)
                .orElseThrow(() -> new RuntimeException("Oferta no encontrada"));

        Solicitud solicitud = ofertaGanadora.getSolicitud();

        if (solicitud.getEstado() != EstadoSolicitud.PENDIENTE && solicitud.getEstado() != EstadoSolicitud.OFERTANDO) {
            throw new RuntimeException("La solicitud no está disponible");
        }

        // 1. Marcar la oferta como ACEPTADA
        ofertaGanadora.setEstado(EstadoOferta.ACEPTADA);
        ofertaRepository.save(ofertaGanadora);

        // 2. Rechazar automáticamente a las demás
        List<Oferta> otrasOfertas = ofertaRepository.findBySolicitudIdOrderByTrabajadorVerificadoDesc(solicitud.getId());
        for (Oferta o : otrasOfertas) {
            if (!o.getId().equals(ofertaId)) {
                o.setEstado(EstadoOferta.RECHAZADA);
                ofertaRepository.save(o);
            }
        }

        // 3. Actualizar la Solicitud (Vincular al trabajador ganador)
        solicitud.setEstado(EstadoSolicitud.ACEPTADO);
        solicitud.setTrabajadorElegido(ofertaGanadora.getTrabajador());
        solicitud.setPrecioSugerido(ofertaGanadora.getMontoOfrecido()); // ADDED: Update Solicitud price
        solicitudRepository.save(solicitud);
    }
}