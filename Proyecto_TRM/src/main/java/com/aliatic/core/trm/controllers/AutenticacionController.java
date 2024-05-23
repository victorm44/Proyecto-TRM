package com.aliatic.core.trm.controllers;


import com.aliatic.core.trm.config.AliaticLogger;
import com.aliatic.core.trm.domain.dto.StandardResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.aliatic.core.trm.domain.dto.AuthRequestDTO;
import com.aliatic.core.trm.domain.dto.AuthResponseDTO;
import com.aliatic.core.trm.services.AutenticacionService;
import static com.aliatic.core.trm.config.Textos.Es.*;

import java.util.Date;


@CrossOrigin
@RestController
@RequestMapping(path = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
public class AutenticacionController {
	
	private AutenticacionService autenticacionService = null;
	private String nombreClase = null;
	
	public AutenticacionController(AutenticacionService autenticacionService) {
		this.autenticacionService = autenticacionService;
		this.nombreClase = this.getClass().getName();
	}

	@CrossOrigin
	@PostMapping
	public ResponseEntity<StandardResponseDTO> login(@RequestBody  AuthRequestDTO request) {
		String nombreMetodo = Thread.currentThread().getStackTrace()[1].getMethodName();
		StandardResponseDTO respuestaEstandar = new StandardResponseDTO();
		respuestaEstandar.setFechaHora(new Date());

		try {
			 AuthResponseDTO respuesta = autenticacionService.iniciarSesion(request);
			 if (respuesta.getToken() != null){
				 respuestaEstandar.setCodigoRespuestaInterno(HttpStatus.OK.value());
				 respuestaEstandar.setMensaje(AUTORIZACION_CONCECIDA.getVal());
				 respuestaEstandar.setPayload(respuesta);
				return ResponseEntity.ok(respuestaEstandar);
			 }
			 respuestaEstandar.setCodigoRespuestaInterno((HttpStatus.UNAUTHORIZED.value()));
			 respuestaEstandar.setMensaje(CREDENCIALES_INVALIDAS.getVal());
			 return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(respuestaEstandar);

		}catch (Exception ex) {
			respuestaEstandar.setCodigoRespuestaInterno(HttpStatus.INTERNAL_SERVER_ERROR.value());
			respuestaEstandar.setMensaje(SE_HA_PRODUCIDO_UN_ERROR_INTERNO_INICIAR_SESION.getVal());
			AliaticLogger.error(SE_HA_PRODUCIDO_UN_ERROR_INTERNO_INICIAR_SESION.getVal(), nombreClase,
					nombreMetodo, ex.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuestaEstandar);
		}
		
	}

}
