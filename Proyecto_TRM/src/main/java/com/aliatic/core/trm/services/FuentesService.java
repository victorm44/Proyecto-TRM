package com.aliatic.core.trm.services;

import com.aliatic.core.trm.domain.dto.FuentesRequestDTO;
import com.aliatic.core.trm.domain.dto.StandardResponseDTO;
import com.aliatic.core.trm.persistence.entities.FuentesEntity;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface FuentesService {
    FuentesEntity save(FuentesEntity fuenteEntity);
    Page<FuentesEntity> findByCodigoFuenteContaining(String codigoFuente, int numeroPagina);
    FuentesEntity convertirReqADTO(FuentesRequestDTO requestDTO);
    Optional<FuentesEntity> findById(Long id);
    StandardResponseDTO updateFuente(Long idFuente, FuentesEntity fuenteEntity);
    StandardResponseDTO almacenarFuente(FuentesEntity fuenteEntity);
}
