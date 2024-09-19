package com.aliatic.core.trm.services;

import com.aliatic.core.trm.domain.dto.StandardResponseDTO;
import com.aliatic.core.trm.persistence.entities.TiposTasasEntity;
import org.springframework.data.domain.Page;
import com.aliatic.core.trm.domain.dto.TiposTasasRequestDTO;


import java.util.Optional;

public interface TiposTasasService {
    TiposTasasEntity save(TiposTasasEntity tiposTasasEntity);
    TiposTasasEntity convertirReqADTO(TiposTasasRequestDTO requestDTO);
    Optional<TiposTasasEntity> findById(Long id);
    StandardResponseDTO updateTipoTasa(Long idTipoTasa, TiposTasasEntity tiposTasasEntity);
    StandardResponseDTO almacenarTipoTasa(TiposTasasEntity tiposTasasEntity);
    Page<TiposTasasEntity> findAll(int numeroPagina);
}