package com.aliatic.core.trm.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class DTFRequestDTO {
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date fecha;
    private String tasa;
    private float valorAnterior;
    private float valorActual;
    private float variacion;
    private int estado;
}
