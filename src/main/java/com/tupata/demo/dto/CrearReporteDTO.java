package com.tupata.demo.dto;

import lombok.Data;

@Data
public class CrearReporteDTO {
    private Long usuarioReportadoId;
    private Long solicitudId; // Opcional, si pasó durante un trabajo
    private String motivo;
    private String evidenciaUrl; // Foto opcional
}