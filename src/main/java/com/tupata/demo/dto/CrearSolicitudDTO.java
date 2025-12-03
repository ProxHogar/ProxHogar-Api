package com.tupata.demo.dto;

import lombok.Data;

@Data
public class CrearSolicitudDTO {
    // Datos del trabajo
    private Long categoriaId;
    private String descripcion;
    private Double precioSugerido;

    // UBICACIÓN COMPLETA
    private String direccion; // Ej: "Av. España 240, Trujillo" (Para que lea el trabajador)
    private String referencia; // Ej: "Portón negro, frente a la farmacia" (Ayuda visual)

    // Coordenadas (Vitales para que el mapa funcione y calcular distancias)
    private Double latitud;
    private Double longitud;
}