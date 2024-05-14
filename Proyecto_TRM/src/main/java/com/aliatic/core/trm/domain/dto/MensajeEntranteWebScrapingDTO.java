package com.aliatic.core.trm.domain.dto;


import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@EqualsAndHashCode(callSuper=false)
public class MensajeEntranteWebScrapingDTO {
	
	@JsonProperty("moneda")
	private String moneda;
	
	@JsonProperty("nombre")
	private String nombre;
	
	@JsonProperty("fuente_tasa")
	private String fuenteTasa;
	
	@JsonProperty("tipo_cambio")
	private String tipoCambio;
	
	@JsonProperty("valor_tasa")
	private String valorTasa;
	
	@JsonProperty("fecha_inicial")
	private String fechaInicial;
	
	@JsonProperty("fecha_final")
	private String fechaFinal;
	
	@JsonProperty("semana")
	private String semana;
	
	@JsonProperty("fecha")
	private String fecha;
	
	@JsonProperty("hora")
	private String hora;
	
	@JsonProperty("url")
	private String url;
	
	@JsonProperty("moneda_origen")
	private String monedaOrigen;
	
	@JsonProperty("moneda_destino")
	private String monedaDestino;
	
	@JsonProperty("metodo_extraccion")
	private int metodoExtraccion;
	
	@JsonProperty("tipo_tasa_int")
	private int tipoTasaInt;
}
