package com.aliatic.core.trm.services;

import com.aliatic.core.trm.domain.dto.StandardResponseDTO;
import com.aliatic.core.trm.domain.dto.TiposCambioRequestDTO;
import com.aliatic.core.trm.persistence.entities.TiposCambioEntity;
import java.util.Optional;
import org.springframework.data.domain.Page;


public interface TiposCambioService {
    TiposCambioEntity save(TiposCambioEntity tiposCambioEntity);
    Page<TiposCambioEntity> findAll(int numeroPagina);
    TiposCambioEntity convertirReqADTO(TiposCambioRequestDTO requestDTO);
    Optional<TiposCambioEntity> findById(Long id);
    StandardResponseDTO updateTipoCambio(Long idTipoCambio, TiposCambioEntity tiposCambioEntity);
    StandardResponseDTO almacenarTipoCambio(TiposCambioEntity tiposCambioEntity);
    Page<TiposCambioEntity> findByFkFuente(Long fkfuente, int numeroPagina);
}
