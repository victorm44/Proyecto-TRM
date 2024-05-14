package com.aliatic.core.trm.consumer;

import java.util.List;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aliatic.core.trm.config.RabbitMQConfig;
import com.aliatic.core.trm.domain.dto.MensajeEntranteWebScrapingDTO;
import com.aliatic.core.trm.persistence.entities.TiposCambioEntity;
import com.aliatic.core.trm.services.AlmacenarTRMService;


@Component
public class TRMConsumer {
	
	@Autowired
	AlmacenarTRMService almacenarService;
	
	@RabbitListener(queues = RabbitMQConfig.QUEUE)
    public void consumeMessageFromQueue(List<MensajeEntranteWebScrapingDTO> informacion) {
		
		List<TiposCambioEntity> tiposCambio = almacenarService.convertirAInterno(informacion);
		
		if (!tiposCambio.isEmpty()) {
			almacenarService.almacenarImportes(tiposCambio);
		}
		
    }

}
