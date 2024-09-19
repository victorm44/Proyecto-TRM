package com.aliatic.core.trm.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;


@AllArgsConstructor
@Data
public class LogDTO {
	private String clase;
	private String metodo;
	private String pilaTrazas;
	private String mensaje;
	
}
