package com.aliatic.core.trm.services.impl;

import com.aliatic.core.trm.domain.dto.StandardResponseDTO;
import com.aliatic.core.trm.domain.dto.TiposTasasRequestDTO;
import com.aliatic.core.trm.persistence.entities.TiposTasasEntity;
import com.aliatic.core.trm.persistence.repositories.TiposTasasRepository;
import com.aliatic.core.trm.services.TiposTasasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import static com.aliatic.core.trm.config.Textos.Es.*;

import java.util.Date;
import java.util.Optional;


@Service
public class TiposTasasServiceImpl implements TiposTasasService {

    private static final int MAX_REGS_POR_PAGINA = 20;
    private final TiposTasasRepository tiposTasasRepository;

    @Autowired
    public TiposTasasServiceImpl(TiposTasasRepository tiposTasasRepository) {
        this.tiposTasasRepository = tiposTasasRepository;
    }

    @Override
    public TiposTasasEntity save(TiposTasasEntity tiposTasasEntity) {
        return tiposTasasRepository.save(tiposTasasEntity);
    }

    @Override
    public Optional<TiposTasasEntity> findById(Long id) {
        return tiposTasasRepository.findById(id);
    }

    @Override
    public TiposTasasEntity convertirReqADTO(TiposTasasRequestDTO requestDTO) {
        TiposTasasEntity tiposTasasEntity = new TiposTasasEntity();
        tiposTasasEntity.setCodigoTipoTasa(requestDTO.getCodigoTipoTasa());
        tiposTasasEntity.setDenominacionTipoTasa(requestDTO.getDenominacionTipoTasa());
        tiposTasasEntity.setEstado(requestDTO.getEstado());
        return tiposTasasEntity;
    }

    @Override
    public StandardResponseDTO updateTipoTasa(Long idTipoTasa, TiposTasasEntity tiposTasasEntity) {
        return tiposTasasRepository.findById(idTipoTasa).map(tipoTasaExistente -> {
            tipoTasaExistente.setCodigoTipoTasa(tiposTasasEntity.getCodigoTipoTasa());
            tipoTasaExistente.setDenominacionTipoTasa(tiposTasasEntity.getDenominacionTipoTasa());
            tipoTasaExistente.setEstado(tiposTasasEntity.getEstado());
            tiposTasasRepository.save(tipoTasaExistente);

            StandardResponseDTO response = new StandardResponseDTO();
            response.setCodigoRespuestaInterno(HttpStatus.OK.value());
            response.setPayload(tipoTasaExistente);
            response.setMensaje(TIPO_TASA_ACTUALIZADO_CORRECTAMENTE.getVal());

            return response;
        }).orElseGet(() -> {
            StandardResponseDTO response = new StandardResponseDTO();
            response.setCodigoRespuestaInterno(HttpStatus.NOT_FOUND.value());
            response.setMensaje(TIPO_TASA_NO_ENCONTRADO.getVal());
            return response;
        });
    }

    @Override
    public StandardResponseDTO almacenarTipoTasa(TiposTasasEntity tiposTasasEntity) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String usuario = auth.getPrincipal().toString();
        StandardResponseDTO response = new StandardResponseDTO();
        response.setFechaHora(new Date());

        if (tiposTasasEntity.getCodigoTipoTasa() == null || tiposTasasEntity.getCodigoTipoTasa().isEmpty()) {
            response.setCodigoRespuestaInterno(530);
            response.setMensaje(CODIGO_TIPO_TASA_VACIO.getVal());
            return response;
        }

        if (tiposTasasEntity.getDenominacionTipoTasa() == null || tiposTasasEntity.getDenominacionTipoTasa().isEmpty()) {
            response.setCodigoRespuestaInterno(530);
            response.setMensaje(DENOMINACION_TIPO_TASA_VACIA.getVal());
            return response;
        }

        tiposTasasEntity.setFechaCreacion(new Date());
        tiposTasasEntity.setFechaModificacion(new Date());
        tiposTasasEntity.setUsuarioCreacion(usuario);
        tiposTasasEntity.setUsuarioModificacion(usuario);

        TiposTasasEntity tiposTasasNew = tiposTasasRepository.save(tiposTasasEntity);
        response.setCodigoRespuestaInterno(HttpStatus.CREATED.value());
        response.setMensaje(HttpStatus.CREATED.getReasonPhrase());
        response.setPayload(tiposTasasNew);
        return response;
    }

    @Override
    public Page<TiposTasasEntity> findAll(int numeroPagina) {
        final Pageable pageable = PageRequest.of(numeroPagina, MAX_REGS_POR_PAGINA);
        return tiposTasasRepository.findAll(pageable);
    }
}
