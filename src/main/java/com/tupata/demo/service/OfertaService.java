package com.tupata.demo.service;

import com.tupata.demo.dto.CrearOfertaDTO;
import com.tupata.demo.model.entity.Oferta;
import java.util.List;

public interface OfertaService {
    Oferta crearOferta(CrearOfertaDTO dto, Long trabajadorUsuarioId);
    List<Oferta> listarOfertasDeSolicitud(Long solicitudId);
    void aceptarOferta(Long ofertaId);
}