package com.aliatic.core.trm.domain.dto;

import lombok.Data;

@Data
public class FuentesRequestDTO {
    private String codigoFuente;
    private String tipoCotizacion;
    private String denominacionTipoFuente;
    private Long tipoTasaId;
    private Long monedaProcedenciaId;
    private Long monedaDestinoId;
    private Long metodoExtracionId;
    private Long proveedoresInfoId;
    private String observaciones;
    private int estado;
}
