package com.tupata.demo.dto;

import lombok.Data;

@Data
public class RegistroUsuarioDTO {
    private String email;
    private String password;
    private String nombreCompleto;
    private String telefono;
    private String fcmToken; // Token de Firebase para notificaciones
}