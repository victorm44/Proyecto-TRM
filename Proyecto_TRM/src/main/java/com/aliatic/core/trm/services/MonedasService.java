package com.aliatic.core.trm.services;

import com.aliatic.core.trm.domain.dto.MonedasRequestDTO;
import com.aliatic.core.trm.domain.dto.StandardResponseDTO;
import com.aliatic.core.trm.persistence.entities.MonedasEntity;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface MonedasService {
    MonedasEntity save(MonedasEntity monedasEntity);
    MonedasEntity convertirReqADTO(MonedasRequestDTO requestDTO);
    Optional<MonedasEntity> findById(Long id);
    StandardResponseDTO updateMoneda(Long idMoneda, MonedasEntity monedasEntity);
    StandardResponseDTO almacenarMoneda(MonedasEntity monedasEntity);
    Page<MonedasEntity> findAll(int numeroPagina);
    Page<MonedasEntity> findAllActivas(int numeroPagina);
}
