package com.aliatic.core.trm.services;
import com.aliatic.core.trm.domain.dto.DTFEntranteDTO;
import com.aliatic.core.trm.persistence.entities.DTFEntity;
import java.util.List;



public interface AlmacenarDTFService {
	List<DTFEntity> convertirAInterno(List<DTFEntranteDTO> mensajeEntrante);
	void almacenarImportes(List<DTFEntity> dtfEntities);
}

