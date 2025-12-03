package com.tupata.demo.model.enums;

public enum EstadoSolicitud {
    PENDIENTE,      // Recién creada, esperando ofertas
    OFERTANDO,      // Ya tiene ofertas, cliente decidiendo
    ACEPTADO,       // Oferta aceptada, pendiente de inicio por parte del trabajador
    EN_PROCESO,     // Trabajo iniciado por el trabajador
    FINALIZADO_POR_TRABAJADOR, // Trabajo completado por el trabajador, pendiente de pago
    FINALIZADA,     // Trabajo completado y pagado
    CANCELADA
}