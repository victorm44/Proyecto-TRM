	package com.aliatic.core.trm.services;

	import java.util.Optional;

	import com.aliatic.core.trm.domain.dto.ParametrosRequestDTO;
	import com.aliatic.core.trm.domain.dto.StandardResponseDTO;
	import com.aliatic.core.trm.persistence.entities.ParametrosEntity;
	import org.springframework.data.domain.Page;

	public interface ParametrosService {

		ParametrosEntity save(ParametrosEntity parametroEntity);
		Page<ParametrosEntity> findByParametroContaining(String parametro, int numeroPagina);
		ParametrosEntity convertirReqADTO(ParametrosRequestDTO requestDTO);
		StandardResponseDTO almacenarParametro(ParametrosEntity parametrosEntity);
		Optional<ParametrosEntity> findById(Long id);
		StandardResponseDTO updateParametro(Long idParametro, ParametrosEntity parametrosEntity);
	}
