package com.aliatic.core.trm.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MonedasRequestDTO {
    private String codigoMoneda;
    private String codigoISO;
    private String codigoAlternativo;
    private int estado;
}
