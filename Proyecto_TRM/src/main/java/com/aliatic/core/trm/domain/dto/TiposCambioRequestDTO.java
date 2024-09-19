package com.aliatic.core.trm.domain.dto;

import com.aliatic.core.trm.persistence.entities.FuentesEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;
@Data
@AllArgsConstructor
public class TiposCambioRequestDTO {
    private int anioSemanaVigencia;
    private int estado;
    private float importe;
    private FuentesEntity fuente;
    private Date fechaInicioVigencia;
    private Date fechaFinVigencia;
}
