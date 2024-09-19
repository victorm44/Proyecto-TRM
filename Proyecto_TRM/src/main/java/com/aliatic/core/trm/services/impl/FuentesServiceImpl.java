package com.aliatic.core.trm.services.impl;

import com.aliatic.core.trm.domain.dto.FuentesRequestDTO;
import com.aliatic.core.trm.domain.dto.StandardResponseDTO;
import com.aliatic.core.trm.persistence.entities.FuentesEntity;
import com.aliatic.core.trm.persistence.repositories.FuentesRepository;

import com.aliatic.core.trm.services.FuentesService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

import static com.aliatic.core.trm.config.Constants.MAX_REGS_POR_PAGINA;
import static com.aliatic.core.trm.config.Constants.VACIO;

@Service
public class FuentesServiceImpl implements FuentesService {

    private FuentesRepository fuenteRepository;

    public FuentesServiceImpl(FuentesRepository fuenteRepository) {
        this.fuenteRepository = fuenteRepository;
    }

    @Override
    public FuentesEntity save(FuentesEntity fuenteEntity) {
        return fuenteRepository.save(fuenteEntity);
    }

    @Override
    public Page<FuentesEntity> findByCodigoFuenteContaining(String codigoFuente, int numeroPagina) {
        final Pageable pageable = PageRequest.of(numeroPagina, MAX_REGS_POR_PAGINA);
        return fuenteRepository.findByCodigoFuenteContainingAndEstado(codigoFuente, 1, pageable);
    }

    @Override
    public FuentesEntity convertirReqADTO(FuentesRequestDTO requestDTO) {
        FuentesEntity fuentesEntity = new FuentesEntity();
        fuentesEntity.setCodigoFuente(requestDTO.getCodigoFuente());
        fuentesEntity.setTipoCotizacion(requestDTO.getTipoCotizacion());
        fuentesEntity.setDenominacionTipoFuente(requestDTO.getDenominacionTipoFuente());
        fuentesEntity.setObservaciones(requestDTO.getObservaciones());
        fuentesEntity.setEstado(requestDTO.getEstado());
        return fuentesEntity;
    }

    @Override
    public Optional<FuentesEntity> findById(Long id) {
        return fuenteRepository.findById(id);
    }

    @Override
    public StandardResponseDTO updateFuente(Long idFuente, FuentesEntity fuenteEntity) {
        return fuenteRepository.findById(idFuente).map(fuenteExistente -> {
            fuenteExistente.setTipoCotizacion(fuenteEntity.getTipoCotizacion());
            fuenteExistente.setDenominacionTipoFuente(fuenteEntity.getDenominacionTipoFuente());
            fuenteExistente.setObservaciones(fuenteEntity.getObservaciones());
            fuenteExistente.setEstado(fuenteEntity.getEstado());
            // Asignar las relaciones
            fuenteRepository.save(fuenteExistente);

            StandardResponseDTO response = new StandardResponseDTO();
            response.setCodigoRespuestaInterno(HttpStatus.OK.value());
            response.setMensaje("Fuente actualizada correctamente");
            response.setPayload(fuenteExistente);
            return response;
        }).orElseGet(() -> {
            StandardResponseDTO response = new StandardResponseDTO();
            response.setCodigoRespuestaInterno(HttpStatus.NOT_FOUND.value());
            response.setMensaje("Fuente no encontrada");
            return response;
        });
    }

    @Override
    public StandardResponseDTO almacenarFuente(FuentesEntity fuenteEntity) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String usuario = auth.getPrincipal().toString();
        StandardResponseDTO response = new StandardResponseDTO();
        response.setFechaHora(new Date());

        if (fuenteEntity.getCodigoFuente() == null || VACIO.equals(fuenteEntity.getCodigoFuente())) {
            response.setCodigoRespuestaInterno(530);
            response.setMensaje("El código de fuente no puede estar vacío");
            return response;
        }

        Optional<FuentesEntity> validacion = fuenteRepository.findByCodigoFuente(fuenteEntity.getCodigoFuente());
        if (validacion.isPresent()) {
            response.setCodigoRespuestaInterno(530);
            response.setMensaje("Fuente registrada previamente");
            return response;
        }

        fuenteEntity.setFechaCreacion(new Date());
        fuenteEntity.setFechaModificacion(new Date());
        fuenteEntity.setUsuarioCreacion(usuario);
        fuenteEntity.setUsuarioModificacion(usuario);

        FuentesEntity fuenteNew = fuenteRepository.save(fuenteEntity);
        response.setCodigoRespuestaInterno(HttpStatus.CREATED.value());
        response.setMensaje(HttpStatus.CREATED.getReasonPhrase());
        response.setPayload(fuenteNew);
        return response;
    }
}
