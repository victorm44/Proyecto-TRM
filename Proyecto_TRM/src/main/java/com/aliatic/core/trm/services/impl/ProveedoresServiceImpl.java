package com.aliatic.core.trm.services.impl;

import com.aliatic.core.trm.domain.dto.ProveedoresRequestDTO;
import com.aliatic.core.trm.domain.dto.StandardResponseDTO;
import com.aliatic.core.trm.persistence.entities.ProveedoresEntity;
import com.aliatic.core.trm.persistence.repositories.ProveedoresRepository;
import com.aliatic.core.trm.services.ProveedoresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class ProveedoresServiceImpl implements ProveedoresService {

    private static final int MAX_REGS_POR_PAGINA = 20;
    private final ProveedoresRepository proveedoresRepository;

    @Autowired
    public ProveedoresServiceImpl(ProveedoresRepository proveedoresRepository) {
        this.proveedoresRepository = proveedoresRepository;
    }

    @Override
    public ProveedoresEntity save(ProveedoresEntity proveedoresEntity) {
        return proveedoresRepository.save(proveedoresEntity);
    }

    @Override
    public Optional<ProveedoresEntity> findById(Long id) {
        return proveedoresRepository.findById(id);
    }

    @Override
    public ProveedoresEntity convertirReqADTO(ProveedoresRequestDTO requestDTO) {
        ProveedoresEntity proveedoresEntity = new ProveedoresEntity();
        proveedoresEntity.setCodigoProveedores(requestDTO.getCodigoProveedores());
        proveedoresEntity.setDenominacionProveedores(requestDTO.getDenominacionProveedores());
        proveedoresEntity.setUrlProveedores(requestDTO.getUrlProveedores());
        proveedoresEntity.setEstado(requestDTO.getEstado());
        return proveedoresEntity;
    }

    @Override
    public StandardResponseDTO updateProveedores(Long idProveedores, ProveedoresEntity proveedoresEntity) {
        return proveedoresRepository.findById(idProveedores).map(proveedorExistente -> {
            proveedorExistente.setDenominacionProveedores(proveedoresEntity.getDenominacionProveedores());
            proveedorExistente.setUrlProveedores(proveedoresEntity.getUrlProveedores());
            proveedorExistente.setEstado(proveedoresEntity.getEstado());
            proveedoresRepository.save(proveedorExistente);

            StandardResponseDTO response = new StandardResponseDTO();
            response.setCodigoRespuestaInterno(HttpStatus.OK.value());
            response.setPayload(proveedorExistente);
            response.setMensaje("Proveedor actualizado correctamente");

            return response;
        }).orElseGet(() -> {
            StandardResponseDTO response = new StandardResponseDTO();
            response.setCodigoRespuestaInterno(HttpStatus.NOT_FOUND.value());
            response.setMensaje("Proveedor no encontrado");
            return response;
        });
    }

    @Override
    public StandardResponseDTO almacenarProveedores(ProveedoresEntity proveedoresEntity) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String usuario = auth.getPrincipal().toString();
        StandardResponseDTO response = new StandardResponseDTO();
        response.setFechaHora(new Date());

        if (proveedoresEntity.getCodigoProveedores() == null || proveedoresEntity.getCodigoProveedores().isEmpty()) {
            response.setCodigoRespuestaInterno(530);
            response.setMensaje("El código del proveedor no puede ser vacío");
            return response;
        }

        if (proveedoresEntity.getDenominacionProveedores() == null || proveedoresEntity.getDenominacionProveedores().isEmpty()) {
            response.setCodigoRespuestaInterno(530);
            response.setMensaje("La denominación del proveedor no puede ser vacía");
            return response;
        }

        proveedoresEntity.setFechaCreacion(new Date());
        proveedoresEntity.setFechaModificacion(new Date());
        proveedoresEntity.setUsuarioCreacion(usuario);
        proveedoresEntity.setUsuarioModificacion(usuario);

        ProveedoresEntity proveedoresNew = proveedoresRepository.save(proveedoresEntity);
        response.setCodigoRespuestaInterno(HttpStatus.CREATED.value());
        response.setMensaje(HttpStatus.CREATED.getReasonPhrase());
        response.setPayload(proveedoresNew);
        return response;
    }

    @Override
    public Page<ProveedoresEntity> findAllActivas(int numeroPagina) {
        final Pageable pageable = PageRequest.of(numeroPagina, MAX_REGS_POR_PAGINA);
        return proveedoresRepository.findByEstado(1, pageable);
    }
}
