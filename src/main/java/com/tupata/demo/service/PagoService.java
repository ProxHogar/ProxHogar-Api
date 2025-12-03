package com.tupata.demo.service;

import com.tupata.demo.dto.RegistrarPagoDTO;
import com.tupata.demo.model.entity.Pago;

public interface PagoService {
    // Registra el pago y finaliza la solicitud
    Pago registrarPago(RegistrarPagoDTO dto);
}