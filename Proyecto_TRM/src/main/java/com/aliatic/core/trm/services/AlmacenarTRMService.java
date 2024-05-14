package com.aliatic.core.trm.services;

import java.util.List;

import com.aliatic.core.trm.domain.dto.MensajeEntranteWebScrapingDTO;
import com.aliatic.core.trm.persistence.entities.TiposCambioEntity;

public interface AlmacenarTRMService {
	
	List<TiposCambioEntity> convertirAInterno(List<MensajeEntranteWebScrapingDTO> mensajeEntrante);
	void almacenarImportes(List<TiposCambioEntity> tiposCambioEntity);

}
