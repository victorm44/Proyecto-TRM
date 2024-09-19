package com.aliatic.core.trm.services.impl;

import com.aliatic.core.trm.domain.dto.MetodosExtraccionRequestDTO;
import com.aliatic.core.trm.domain.dto.StandardResponseDTO;
import com.aliatic.core.trm.persistence.entities.MetodosExtracionEntity;
import com.aliatic.core.trm.persistence.repositories.MetodosExtraccionRepository;
import com.aliatic.core.trm.services.MetodosExtraccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import static com.aliatic.core.trm.config.Textos.Es.*;

import java.util.Date;
import java.util.Optional;

@Service
public class MetodosExtraccionServiceImpl implements MetodosExtraccionService {

    @Autowired
    private MetodosExtraccionRepository metodosExtraccionRepository;

    @Override
    public MetodosExtracionEntity save(MetodosExtracionEntity metodosExtracionEntity) {
        return metodosExtraccionRepository.save(metodosExtracionEntity);
    }

    @Override
    public Optional<MetodosExtracionEntity> findById(Long id) {
        return metodosExtraccionRepository.findById(id);
    }

    @Override
    public MetodosExtracionEntity convertirReqADTO(MetodosExtraccionRequestDTO requestDTO) {
        MetodosExtracionEntity entity = new MetodosExtracionEntity();
        entity.setCodigoMetodoExtracion(requestDTO.getCodigoMetodoExtraccion());
        entity.setDenominacionMetodoExtracion(requestDTO.getDenominacionMetodoExtraccion());
        entity.setEstado(requestDTO.getEstado());
        entity.setFechaCreacion(new Date());
        entity.setFechaModificacion(new Date());
        entity.setUsuarioCreacion("usuario");
        entity.setUsuarioModificacion("usuario");
        return entity;
    }

    @Override
    public StandardResponseDTO updateMetodosExtraccion(Long idMetodosExtraccion,
                                                       MetodosExtraccionRequestDTO requestDTO) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String usuario = auth.getPrincipal().toString();
        Optional<MetodosExtracionEntity> optionalEntity = metodosExtraccionRepository
                .findById(idMetodosExtraccion);
        if (optionalEntity.isPresent()) {
            MetodosExtracionEntity existingEntity = optionalEntity.get();
            if (existingEntity.getEstado() == 1) {
                existingEntity.setCodigoMetodoExtracion(requestDTO.getCodigoMetodoExtraccion());
                existingEntity.setDenominacionMetodoExtracion(requestDTO.getDenominacionMetodoExtraccion());
                existingEntity.setEstado(requestDTO.getEstado());
                existingEntity.setFechaModificacion(new Date());
                existingEntity.setUsuarioModificacion(usuario);
                metodosExtraccionRepository.save(existingEntity);

                return new StandardResponseDTO(200,
                        METODO_EXTRACCION_ACTUALIZADO_CORRECTAMENTE.getVal(),
                        new Date(), existingEntity);
            } else {
                return new StandardResponseDTO(404,
                        METODO_EXTRACCION_NO_ENCONTRADO.getVal(), new Date(),
                        null);
            }
        } else {
            return new StandardResponseDTO(404,
                    METODO_EXTRACCION_NO_ENCONTRADO.getVal(),
                    new Date(), null);
        }
    }

    @Override
    public Page<MetodosExtracionEntity> findAll(Pageable pageable) {
        return metodosExtraccionRepository.findAll(pageable);
    }
}
