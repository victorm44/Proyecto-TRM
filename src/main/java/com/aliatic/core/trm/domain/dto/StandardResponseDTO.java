package com.aliatic.core.trm.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StandardResponseDTO {
    private int codigoRespuestaInterno;
    private String mensaje;
    private Date fechaHora;
    private Object payload;
}
