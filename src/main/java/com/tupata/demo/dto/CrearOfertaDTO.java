package com.tupata.demo.dto;

import lombok.Data;

@Data
public class CrearOfertaDTO {
    private Long solicitudId;
    private Double montoOfrecido; // Puede ser mayor o menor al sugerido
    private String comentario; // "Tengo herramientas, llego en 5 min"
}