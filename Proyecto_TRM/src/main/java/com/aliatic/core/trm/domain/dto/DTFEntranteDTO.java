package com.aliatic.core.trm.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DTFEntranteDTO {
    @JsonProperty("fecha")
    private String fecha;

    @JsonProperty("tasa")
    private String tasa;

    @JsonProperty("anterior")
    private String anterior;

    @JsonProperty("actual")
    private String actual;

    @JsonProperty("variacion")
    private String variacion;
}
