package com.aliatic.core.trm.services.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;

import com.aliatic.core.trm.config.AliaticLogger;
import com.aliatic.core.trm.domain.dto.DTFEntranteDTO;
import org.springframework.stereotype.Service;
import com.aliatic.core.trm.persistence.entities.DTFEntity;
import com.aliatic.core.trm.persistence.repositories.DTFRepository;
import com.aliatic.core.trm.services.AlmacenarDTFService;
import org.springframework.transaction.annotation.Transactional;

import static com.aliatic.core.trm.config.Constants.ALIATIC_BACKGROUND;
import static com.aliatic.core.trm.config.Constants.FORMAT_YYYY_MM_DD;
import static com.aliatic.core.trm.config.Textos.Es.*;

@Service
public class AlmacenarDTFServiceImpl implements AlmacenarDTFService {

    private final DTFRepository dtfRepository;
    private final String nombreClase;


    public AlmacenarDTFServiceImpl(DTFRepository dtfRepository) {
        this.dtfRepository = dtfRepository;
        this.nombreClase = this.getClass().getName();
    }

    @Override
    public List<DTFEntity> convertirAInterno(List<DTFEntranteDTO> mensajeEntrante) {
        List<DTFEntity> listaSalida = new ArrayList<>();
        String nombreMetodo = Thread.currentThread().getStackTrace()[1].getMethodName();
        try {
            for (DTFEntranteDTO mensaje : mensajeEntrante) {
                DTFEntity dtfEntity = new DTFEntity();

                dtfEntity.setFechaCreacion(new Date());
                dtfEntity.setFechaModificacion(new Date());
                dtfEntity.setUsuarioCreacion(ALIATIC_BACKGROUND);
                dtfEntity.setUsuarioModificacion(ALIATIC_BACKGROUND);
                AliaticLogger.info(INICIO_TRANSFORMACION_COLA_RABBIT.getVal(), nombreClase, nombreMetodo);

                try {
                    dtfEntity.setFecha(new SimpleDateFormat(FORMAT_YYYY_MM_DD).parse(mensaje.getFecha()));
                } catch (ParseException e) {
                    AliaticLogger.error(HUBO_UN_ERROR_AL_TRANSORMAR_ALGUNA_FECHA.getVal(), nombreClase, nombreMetodo,
                            e.getMessage());
                }
                dtfEntity.setTasa(mensaje.getTasa());
                dtfEntity.setValorAnterior(Float.parseFloat(mensaje.getAnterior()));
                dtfEntity.setValorActual(Float.parseFloat(mensaje.getActual()));
                dtfEntity.setVariacion(Float.parseFloat(mensaje.getVariacion()));

                listaSalida.add(dtfEntity);
            }
            AliaticLogger.info(FIN_TRANSFORMACION_COLA_RABBIT.getVal(), nombreClase, nombreMetodo);
            return listaSalida;

        }catch (Exception e) {
            AliaticLogger.error(ERROR_AL_TRANSOFRMAR_INFO_COLA_RABBIT.getVal(), nombreClase, nombreMetodo,
                    e.getMessage());
        }
        return Collections.emptyList();
    }

    @Override
    @Transactional
    public void almacenarImportes(List<DTFEntity> dtfEntities) {
        dtfEntities.forEach(dtfEntity -> {
            if (dtfEntity != null) {
                dtfRepository.save(dtfEntity);
            }
        });
    }

}
