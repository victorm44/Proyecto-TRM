package com.aliatic.core.trm.services.impl;

import com.aliatic.core.trm.domain.dto.StandardResponseDTO;
import com.aliatic.core.trm.domain.dto.TiposCambioRequestDTO;
import com.aliatic.core.trm.persistence.entities.TiposCambioEntity;
import com.aliatic.core.trm.persistence.repositories.TiposCambioRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.aliatic.core.trm.services.TiposCambioService;

import java.util.Date;
import java.util.Optional;

import static com.aliatic.core.trm.config.Constants.*;
import static com.aliatic.core.trm.config.Textos.Es.*;



@Service
public class TiposCambioServiceImpl implements TiposCambioService {

    private TiposCambioRepository tiposCambioRepository;


    public TiposCambioServiceImpl(TiposCambioRepository tiposCambioRepository) {
        this.tiposCambioRepository = tiposCambioRepository;
    }

    @Override
    public TiposCambioEntity save(TiposCambioEntity tiposCambioEntity) {
        return tiposCambioRepository.save(tiposCambioEntity);
    }

    @Override
    public Page<TiposCambioEntity> findAll(int numeroPagina) {
        Pageable pageable = PageRequest.of(numeroPagina, MAX_REGS_POR_PAGINA);
        return tiposCambioRepository.findAll(pageable);
    }

    @Override
    public Optional<TiposCambioEntity> findById(Long id) {
        return tiposCambioRepository.findById(id);
    }



    @Override
    public TiposCambioEntity convertirReqADTO(TiposCambioRequestDTO requestDTO) {
        TiposCambioEntity tiposCambioEntity = new TiposCambioEntity();

        tiposCambioEntity.setFuentes(requestDTO.getFuente());
        tiposCambioEntity.setImporte(requestDTO.getImporte());
        tiposCambioEntity.setFechaInicioVigencia(requestDTO.getFechaInicioVigencia());
        tiposCambioEntity.setFechaFinVigencia(requestDTO.getFechaFinVigencia());
        tiposCambioEntity.setAnioSemanaVigencia(requestDTO.getAnioSemanaVigencia());
        return tiposCambioEntity;
    }

    @Override
    public StandardResponseDTO updateTipoCambio(Long idTipoCambio, TiposCambioEntity tiposCambioEntity) {
        return tiposCambioRepository.findById(idTipoCambio).map(tipoCambioExistente -> {

            tipoCambioExistente.setFuentes(tiposCambioEntity.getFuentes());
            tipoCambioExistente.setImporte(tiposCambioEntity.getImporte());
            tipoCambioExistente.setFechaInicioVigencia(tiposCambioEntity.getFechaInicioVigencia());
            tipoCambioExistente.setFechaFinVigencia(tiposCambioEntity.getFechaFinVigencia());
            tipoCambioExistente.setAnioSemanaVigencia(tiposCambioEntity.getAnioSemanaVigencia());

            tiposCambioRepository.save(tipoCambioExistente);
            StandardResponseDTO response = new StandardResponseDTO();
            response.setCodigoRespuestaInterno(HttpStatus.OK.value());
            response.setPayload(tipoCambioExistente);
            response.setMensaje(TIPO_CAMBIO_ACTUALIZADO.getVal());

            return response;
        }).orElseGet(() -> {
            StandardResponseDTO response = new StandardResponseDTO();
            response.setCodigoRespuestaInterno(HttpStatus.NOT_FOUND.value());
            response.setMensaje(TIPO_CAMBIO_NO_ENCONTRADO.getVal());
            return response;
        });
    }

    @Override
    public StandardResponseDTO almacenarTipoCambio(TiposCambioEntity tiposCambioEntity) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String usuario = auth.getPrincipal().toString();
        StandardResponseDTO response = new StandardResponseDTO();
        response.setFechaHora(new Date());

        if (tiposCambioEntity.getFuentes() == null || tiposCambioEntity.getFuentes().getIdFuente() == null) {
            response.setCodigoRespuestaInterno(530);
            response.setMensaje(EL_CODIGO_DE_TIPO_DE_CAMBIO_NO_VACIO.getVal());
            return response;
        }

        float importe = tiposCambioEntity.getImporte();

        if (Math.abs(importe) < EPSILON) {
            response.setCodigoRespuestaInterno(530);
            response.setMensaje(IMPORTE_NO_VACIO.getVal());
            return response;
        }

        tiposCambioEntity.setFechaCreacion(new Date());
        tiposCambioEntity.setFechaModificacion(new Date());
        tiposCambioEntity.setUsuarioCreacion(usuario);
        tiposCambioEntity.setUsuarioModificacion(usuario);

        TiposCambioEntity tiposCambioNew = tiposCambioRepository.save(tiposCambioEntity);
        response.setCodigoRespuestaInterno(HttpStatus.CREATED.value());
        response.setMensaje(HttpStatus.CREATED.getReasonPhrase());
        response.setPayload(tiposCambioNew);
        return response;
    }


    @Override
    public Page<TiposCambioEntity> findByFkFuente(Long fkfuente, int numeroPagina) {
        final Pageable pageable = PageRequest.of(numeroPagina, MAX_REGS_POR_PAGINA);
        return tiposCambioRepository.findByFuentes_IdFuente(fkfuente, pageable); 
    }

}
