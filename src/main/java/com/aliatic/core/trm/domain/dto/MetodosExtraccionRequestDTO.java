package com.aliatic.core.trm.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MetodosExtraccionRequestDTO {
    private String codigoMetodoExtraccion;
    private String denominacionMetodoExtraccion;
    private Integer estado;
}
