package com.aliatic.core.trm.config;

import com.aliatic.core.trm.domain.dto.LogDTO;
import lombok.extern.log4j.Log4j2;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import static com.aliatic.core.trm.config.Textos.Es.*;

/**
 * Clase controladora de logs para ser enviados a los visualizadores
 *
 * @author Arenas Silva, Juan
 * @Empresa: Aliatic S.A.S.
 */

@Log4j2
public class AliaticLogger {

	private AliaticLogger(){ }
	
	public static void error(String mensaje, String clase, String metodo, String stackTrace) {
		
		ObjectMapper mapper = new ObjectMapper();
		LogDTO errorLog = new LogDTO(clase, metodo, stackTrace, mensaje);
		try {
			String errorLogStr = mapper.writeValueAsString(errorLog);
			log.error(errorLogStr);
		} catch (JsonProcessingException e) {
            log.error("{} {}", ERROR_CONSTRUYENDO_EN_JSON_DEL_LOGGER, e.getMessage());
		}
		
	}
	
	public static void info(String mensaje, String clase, String metodo) {
		ObjectMapper mapper = new ObjectMapper();
		LogDTO errorLog = new LogDTO(clase, metodo, null, mensaje);
		try {
			String errorLogStr = mapper.writeValueAsString(errorLog);
			log.info(errorLogStr);
		} catch (JsonProcessingException e) {
			log.error("{} {}", ERROR_CONSTRUYENDO_EN_JSON_DEL_LOGGER, e.getMessage());
		}
	}
	
}
