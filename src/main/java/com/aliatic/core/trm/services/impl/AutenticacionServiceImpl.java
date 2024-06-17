package com.aliatic.core.trm.services.impl;

import com.aliatic.core.trm.config.JWTAuthtenticationConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aliatic.core.trm.domain.dto.AuthRequestDTO;
import com.aliatic.core.trm.domain.dto.AuthResponseDTO;
import com.aliatic.core.trm.services.AutenticacionService;

@Service
public class AutenticacionServiceImpl implements AutenticacionService {

	private final JWTAuthtenticationConfig jwtAuthtenticationConfig;

	public AutenticacionServiceImpl(JWTAuthtenticationConfig jwtAuthtenticationConfig){
		this.jwtAuthtenticationConfig = jwtAuthtenticationConfig;
	}

	@Override
	public AuthResponseDTO iniciarSesion(AuthRequestDTO authRequest) {

		//TODO: Crear lógica de autorización
		if("AliaticDev".equals(authRequest.getClave())){
			String token = jwtAuthtenticationConfig.getJWTToken(authRequest.getUsuario());
			return new AuthResponseDTO(authRequest.getUsuario(), token);
		}

		return new AuthResponseDTO(authRequest.getUsuario(), null);

	}

}
