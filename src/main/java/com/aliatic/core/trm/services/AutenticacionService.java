package com.aliatic.core.trm.services;

import com.aliatic.core.trm.domain.dto.AuthRequestDTO;
import com.aliatic.core.trm.domain.dto.AuthResponseDTO;

public interface AutenticacionService {
	
	AuthResponseDTO iniciarSesion(AuthRequestDTO authRequest);

}
