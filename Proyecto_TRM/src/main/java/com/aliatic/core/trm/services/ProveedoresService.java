package com.aliatic.core.trm.services;

import com.aliatic.core.trm.domain.dto.ProveedoresRequestDTO;
import com.aliatic.core.trm.domain.dto.StandardResponseDTO;
import com.aliatic.core.trm.persistence.entities.ProveedoresEntity;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface ProveedoresService {
    ProveedoresEntity save(ProveedoresEntity proveedoresEntity);
    Optional<ProveedoresEntity> findById(Long id);
    ProveedoresEntity convertirReqADTO(ProveedoresRequestDTO requestDTO);
    StandardResponseDTO updateProveedores(Long idProveedores, ProveedoresEntity proveedoresEntity);
    StandardResponseDTO almacenarProveedores(ProveedoresEntity proveedoresEntity);
    Page<ProveedoresEntity> findAllActivas(int numeroPagina);
}
