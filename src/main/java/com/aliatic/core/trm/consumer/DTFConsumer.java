package com.aliatic.core.trm.consumer;

import java.util.List;

import com.aliatic.core.trm.domain.dto.DTFEntranteDTO;
import com.aliatic.core.trm.persistence.entities.DTFEntity;
import com.aliatic.core.trm.services.AlmacenarDTFService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aliatic.core.trm.config.RabbitMQConfig;


@Component
public class DTFConsumer {

    @Autowired
    AlmacenarDTFService almacenarDTFService;

    @RabbitListener(queues = RabbitMQConfig.QUEUE_DTF)
    public void consumeMessageFromQueue(List<DTFEntranteDTO> informacion) {
        List<DTFEntity> dtfEntities = almacenarDTFService.convertirAInterno(informacion);

        if (!dtfEntities.isEmpty()) {
            almacenarDTFService.almacenarImportes(dtfEntities);
        }
    }
}
