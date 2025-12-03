package com.tupata.demo.service.impl;

import com.tupata.demo.dto.RegistrarPagoDTO;
import com.tupata.demo.model.entity.Pago;
import com.tupata.demo.model.entity.Solicitud;
import com.tupata.demo.model.enums.EstadoPago;
import com.tupata.demo.model.enums.EstadoSolicitud;
import com.tupata.demo.model.enums.MetodoPago;
import com.tupata.demo.repository.PagoRepository;
import com.tupata.demo.repository.SolicitudRepository;
import com.tupata.demo.service.PagoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PagoServiceImpl implements PagoService {

    private final PagoRepository pagoRepository;
    private final SolicitudRepository solicitudRepository;

    @Override
    @Transactional
    public Pago registrarPago(RegistrarPagoDTO dto) {
        Solicitud solicitud = solicitudRepository.findById(dto.getSolicitudId())
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));

        // Validar que no esté pagada ya
        if (solicitud.getEstado() == EstadoSolicitud.FINALIZADA) {
            throw new RuntimeException("Esta solicitud ya fue pagada y finalizada");
        }

        Pago pago = new Pago();
        pago.setSolicitud(solicitud);
        pago.setMonto(dto.getMonto());
        pago.setCodigoTransaccion(dto.getCodigoTransaccion());

        // Convertimos el String del DTO al Enum (YAPE, PLIN, etc.)
        try {
            pago.setMetodo(MetodoPago.valueOf(dto.getMetodoPago().toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Método de pago inválido. Usar: YAPE, PLIN, EFECTIVO");
        }

        pago.setEstado(EstadoPago.PAGADO); // Asumimos éxito directo si mandan el voucher

        // *** IMPORTANTE: CERRAR EL CICLO DEL TRABAJO ***
        solicitud.setEstado(EstadoSolicitud.FINALIZADA);
        solicitudRepository.save(solicitud);

        return pagoRepository.save(pago);
    }
}