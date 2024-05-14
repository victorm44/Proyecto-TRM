package com.aliatic.core.trm.persistence.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aliatic.core.trm.persistence.entities.FuentesEntity;
import com.aliatic.core.trm.persistence.entities.MetodosExtracionEntity;
import com.aliatic.core.trm.persistence.entities.MonedasEntity;
import com.aliatic.core.trm.persistence.entities.TiposTasasEntity;

@Repository
public interface FuentesRepository extends JpaRepository<FuentesEntity, Long> {	
	
	List<FuentesEntity> findBymonedaProcedenciaAndMonedaDestinoAndMetodoExtracionAndTipoTasa(
			MonedasEntity monedaProcedencia, MonedasEntity monedaDestino, 
			MetodosExtracionEntity metodoExtracion, TiposTasasEntity tipoTasa
	);
}
