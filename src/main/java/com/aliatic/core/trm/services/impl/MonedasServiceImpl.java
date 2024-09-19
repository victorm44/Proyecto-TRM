package com.aliatic.core.trm.services.impl;

import com.aliatic.core.trm.domain.dto.StandardResponseDTO;
import com.aliatic.core.trm.domain.dto.MonedasRequestDTO;
import com.aliatic.core.trm.persistence.entities.MonedasEntity;
import com.aliatic.core.trm.persistence.repositories.MonedasRepository;
import com.aliatic.core.trm.services.MonedasService;
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

import static com.aliatic.core.trm.config.Textos.*;

@Service
public class MonedasServiceImpl implements MonedasService {

    private static final int MAX_REGS_POR_PAGINA = 20;
    private final MonedasRepository monedasRepository;

    @Autowired
    public MonedasServiceImpl(MonedasRepository monedasRepository) {
        this.monedasRepository = monedasRepository;
    }

    @Override
    public MonedasEntity save(MonedasEntity monedasEntity) {
        return monedasRepository.save(monedasEntity);
    }

    @Override
    public Optional<MonedasEntity> findById(Long id) {
        return monedasRepository.findById(id);
    }

    @Override
    public MonedasEntity convertirReqADTO(MonedasRequestDTO requestDTO) {
        MonedasEntity monedasEntity = new MonedasEntity();
        monedasEntity.setCodigoMoneda(requestDTO.getCodigoMoneda());
        monedasEntity.setCodigoISO(requestDTO.getCodigoISO());
        monedasEntity.setCodigoAlternativo(requestDTO.getCodigoAlternativo());
        monedasEntity.setEstado(requestDTO.getEstado());
        return monedasEntity;
    }

    @Override
    public StandardResponseDTO updateMoneda(Long idMoneda, MonedasEntity monedasEntity) {
        return monedasRepository.findById(idMoneda).map(monedaExistente -> {
            monedaExistente.setEstado(monedasEntity.getEstado());
            if (monedasEntity.getCodigoMoneda() != null && !monedasEntity.getCodigoMoneda().isEmpty()) {
                monedaExistente.setCodigoMoneda(monedasEntity.getCodigoMoneda());
            }
            if (monedasEntity.getCodigoISO() != null && !monedasEntity.getCodigoISO().isEmpty()) {
                monedaExistente.setCodigoISO(monedasEntity.getCodigoISO());
            }
            if (monedasEntity.getCodigoAlternativo() != null && !monedasEntity.getCodigoAlternativo().isEmpty()) {
                monedaExistente.setCodigoAlternativo(monedasEntity.getCodigoAlternativo());
            }
            monedasRepository.save(monedaExistente);

            StandardResponseDTO response = new StandardResponseDTO();
            response.setCodigoRespuestaInterno(HttpStatus.OK.value());
            response.setPayload(monedaExistente);
            response.setMensaje(MONEDA_ACTUALIZADA_CORRECTAMENTE.getVal());

            return response;
        }).orElseGet(() -> {
            StandardResponseDTO response = new StandardResponseDTO();
            response.setCodigoRespuestaInterno(HttpStatus.NOT_FOUND.value());
            response.setMensaje(MONEDA_NO_ENCONTRADA.getVal());
            return response;
        });
    }

    @Override
    public StandardResponseDTO almacenarMoneda(MonedasEntity monedasEntity) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String usuario = auth.getPrincipal().toString();
        StandardResponseDTO response = new StandardResponseDTO();
        response.setFechaHora(new Date());

        if (monedasEntity.getCodigoMoneda() == null || monedasEntity.getCodigoMoneda().isEmpty()) {
            response.setCodigoRespuestaInterno(530);
            response.setMensaje(CODIGO_MONEDA_VACIO.getVal());
            return response;
        }

        if (monedasEntity.getCodigoISO() == null || monedasEntity.getCodigoISO().isEmpty()) {
            response.setCodigoRespuestaInterno(530);
            response.setMensaje(CODIGO_ISO_VACIO.getVal());
            return response;
        }

        monedasEntity.setFechaCreacion(new Date());
        monedasEntity.setFechaModificacion(new Date());
        monedasEntity.setUsuarioCreacion(usuario);
        monedasEntity.setUsuarioModificacion(usuario);

        MonedasEntity monedasNew = monedasRepository.save(monedasEntity);
        response.setCodigoRespuestaInterno(HttpStatus.CREATED.value());
        response.setMensaje(HttpStatus.CREATED.getReasonPhrase());
        response.setPayload(monedasNew);
        return response;
    }

    @Override
    public Page<MonedasEntity> findAll(int numeroPagina) {
        final Pageable pageable = PageRequest.of(numeroPagina, MAX_REGS_POR_PAGINA);
        return monedasRepository.findAll(pageable);
    }

    @Override
    public Page<MonedasEntity> findAllActivas(int numeroPagina) {
        final Pageable pageable = PageRequest.of(numeroPagina, MAX_REGS_POR_PAGINA);
        return monedasRepository.findByEstado(1, pageable);
    }
}
