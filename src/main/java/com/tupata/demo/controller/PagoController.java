package com.tupata.demo.controller;

import com.tupata.demo.dto.RegistrarPagoDTO;
import com.tupata.demo.model.entity.Pago;
import com.tupata.demo.service.PagoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pagos")
@RequiredArgsConstructor
public class PagoController {

    private final PagoService pagoService;

    // URL: POST /api/pagos
    @PostMapping
    public ResponseEntity<Pago> registrarPago(@RequestBody RegistrarPagoDTO dto) {
        Pago nuevoPago = pagoService.registrarPago(dto);
        return ResponseEntity.ok(nuevoPago);
    }
}