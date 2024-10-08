package com.aliatic.core.trm.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ParametrosRequestDTO {
    private String codigoParametro;
    private String sistemaOComponente;
    private String texto1;
    private String texto2;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fecha1;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fecha2;
    private Double numero1;
    private Double numero2;
    private int estado;
}
