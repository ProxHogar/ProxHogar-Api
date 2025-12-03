package com.tupata.demo.dto;

import lombok.Data;

@Data
public class CrearResenaDTO {
    private Long solicitudId;
    private Long usuarioCalificadoId; // A quién estás calificando
    private Integer estrellas; // 1 a 5
    private String comentario;
}