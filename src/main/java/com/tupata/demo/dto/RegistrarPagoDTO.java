package com.tupata.demo.dto;

import lombok.Data;

@Data
public class RegistrarPagoDTO {
    private Long solicitudId;
    private Double monto;
    private String metodoPago; // "YAPE", "PLIN", "EFECTIVO" (Se recibe como String y se valida)
    private String codigoTransaccion; // El voucher o código de operación
}