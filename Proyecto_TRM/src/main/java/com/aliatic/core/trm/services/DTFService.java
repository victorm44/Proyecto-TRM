package com.aliatic.core.trm.services;

import java.util.Optional;

import com.aliatic.core.trm.domain.dto.DTFRequestDTO;
import com.aliatic.core.trm.domain.dto.StandardResponseDTO;
import com.aliatic.core.trm.persistence.entities.DTFEntity;
import org.springframework.data.domain.Page;

public interface DTFService {
    DTFEntity save(DTFEntity dtfEntity);
    Page<DTFEntity> findByTasaContaining(String tasa, int numeroPagina);
    DTFEntity convertirReqADTO(DTFRequestDTO requestDTO);
    StandardResponseDTO almacenarDTF(DTFEntity dtfEntity);
    Optional<DTFEntity> findById(Long id);
    StandardResponseDTO updateDTF(Long idDTF, DTFEntity dtfEntity);
}
