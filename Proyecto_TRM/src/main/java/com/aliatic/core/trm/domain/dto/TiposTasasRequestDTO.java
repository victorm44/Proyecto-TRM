package com.aliatic.core.trm.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TiposTasasRequestDTO {
    private String codigoTipoTasa;
    private String denominacionTipoTasa;
    private int estado;
}
