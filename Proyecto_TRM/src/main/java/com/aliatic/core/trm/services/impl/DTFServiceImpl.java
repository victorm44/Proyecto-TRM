package com.aliatic.core.trm.services.impl;

import com.aliatic.core.trm.domain.dto.DTFRequestDTO;
import com.aliatic.core.trm.domain.dto.StandardResponseDTO;
import com.aliatic.core.trm.persistence.entities.DTFEntity;
import com.aliatic.core.trm.persistence.repositories.DTFRepository;
import com.aliatic.core.trm.services.DTFService;
import jakarta.transaction.Transactional;
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
import static com.aliatic.core.trm.config.Textos.Es.*;

@Service
public class DTFServiceImpl implements DTFService {

    private final DTFRepository dtfRepository;

    public DTFServiceImpl(DTFRepository dtfRepository) {
        this.dtfRepository = dtfRepository;
    }

    @Override
    public DTFEntity save(DTFEntity dtfEntity) {
        return dtfRepository.save(dtfEntity);
    }

    @Override
    public Page<DTFEntity> findByTasaContaining(String tasa, int numeroPagina) {
        final Pageable pageable = PageRequest.of(numeroPagina, MAX_REGS_POR_PAGINA);
        return dtfRepository.findByTasaContainingAndEstado(tasa, 1, pageable);
    }

    @Override
    public DTFEntity convertirReqADTO(DTFRequestDTO requestDTO) {
        DTFEntity dtfEntity = new DTFEntity();
        dtfEntity.setFecha(requestDTO.getFecha());
        dtfEntity.setTasa(requestDTO.getTasa());
        dtfEntity.setValorAnterior(requestDTO.getValorAnterior());
        dtfEntity.setValorActual(requestDTO.getValorActual());
        dtfEntity.setVariacion(requestDTO.getVariacion());
        dtfEntity.setEstado(requestDTO.getEstado());

        return dtfEntity;
    }

    @Override
    public Optional<DTFEntity> findById(Long id) {
        return dtfRepository.findById(id);
    }

    @Override
    public StandardResponseDTO updateDTF(Long idDTF, DTFEntity dtfEntity) {
        return dtfRepository.findById(idDTF).map(dtfExistente -> {
            dtfExistente.setFecha(dtfEntity.getFecha());
            dtfExistente.setTasa(dtfEntity.getTasa());
            dtfExistente.setValorAnterior(dtfEntity.getValorAnterior());
            dtfExistente.setValorActual(dtfEntity.getValorActual());
            dtfExistente.setVariacion(dtfEntity.getVariacion());
            dtfExistente.setEstado(dtfEntity.getEstado());

            dtfRepository.save(dtfExistente);

            StandardResponseDTO response = new StandardResponseDTO();
            response.setCodigoRespuestaInterno(HttpStatus.OK.value());
            response.setMensaje(PARAMETRO_ACTUALIZADO.getVal());
            response.setPayload(dtfExistente);
            return response;
        }).orElseGet(() -> {
            StandardResponseDTO response = new StandardResponseDTO();
            response.setCodigoRespuestaInterno(HttpStatus.NOT_FOUND.value());
            response.setMensaje(PARAMETRO_NO_ENCONTRADO.getVal());
            return response;
        });
    }

    @Override
    public StandardResponseDTO almacenarDTF(DTFEntity dtfEntity) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String usuario = auth.getPrincipal().toString();
        StandardResponseDTO response = new StandardResponseDTO();
        response.setFechaHora(new Date());

        if (dtfEntity.getFecha() == null) {
            response.setCodigoRespuestaInterno(530);
            response.setMensaje(EL_CODIGO_DE_PARAMETRO_NO_VACIO.getVal());
            return response;
        }
        if (dtfEntity.getTasa() == null || dtfEntity.getTasa().isEmpty()) {
            response.setCodigoRespuestaInterno(530);
            response.setMensaje(SISTEMA_O_COMPONENTE_NO_VACIO.getVal());
            return response;
        }

        Optional<DTFEntity> validacion = dtfRepository.findByFecha(dtfEntity.getFecha());
        if (validacion.isPresent() && dtfEntity.getTasa().equals(validacion.get().getTasa())) {
            response.setCodigoRespuestaInterno(530);
            response.setMensaje(PARAMETRO_REGISTRADO_PREVIAMENTE.getVal());
            return response;
        }

        dtfEntity.setFechaCreacion(new Date());
        dtfEntity.setFechaModificacion(new Date());
        dtfEntity.setUsuarioCreacion(usuario);
        dtfEntity.setUsuarioModificacion(usuario);

        DTFEntity dtfNew = dtfRepository.save(dtfEntity);
        response.setCodigoRespuestaInterno(HttpStatus.CREATED.value());
        response.setMensaje(HttpStatus.CREATED.getReasonPhrase());
        response.setPayload(dtfNew);
        return response;
    }
}
