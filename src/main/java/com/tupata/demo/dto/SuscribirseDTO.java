package com.tupata.demo.dto;

import lombok.Data;

@Data
public class SuscribirseDTO {
    private Long planId;
    private Integer meses; // Cuántos meses quiere pagar (por defecto 1)
}