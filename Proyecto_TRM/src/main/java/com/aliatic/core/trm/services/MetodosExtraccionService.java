package com.aliatic.core.trm.services;

import com.aliatic.core.trm.domain.dto.MetodosExtraccionRequestDTO;
import com.aliatic.core.trm.domain.dto.StandardResponseDTO;
import com.aliatic.core.trm.persistence.entities.MetodosExtracionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface MetodosExtraccionService {

    MetodosExtracionEntity save(MetodosExtracionEntity metodosExtracionEntity);
    Optional<MetodosExtracionEntity> findById(Long id);
    MetodosExtracionEntity convertirReqADTO(MetodosExtraccionRequestDTO requestDTO);
    StandardResponseDTO updateMetodosExtraccion(Long idMetodosExtraccion, MetodosExtraccionRequestDTO requestDTO);
    Page<MetodosExtracionEntity> findAll(Pageable pageable);
}
