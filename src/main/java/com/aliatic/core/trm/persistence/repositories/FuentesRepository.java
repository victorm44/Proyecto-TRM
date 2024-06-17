package com.aliatic.core.trm.persistence.repositories;

import java.util.List;

import com.aliatic.core.trm.persistence.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FuentesRepository extends JpaRepository<FuentesEntity, Long> {	
	
	List<FuentesEntity> findBymonedaProcedenciaAndMonedaDestinoAndMetodoExtracionAndTipoTasaAndProveedoresInfo(
			MonedasEntity monedaProcedencia, MonedasEntity monedaDestino, 
			MetodosExtracionEntity metodoExtracion, TiposTasasEntity tipoTasa, ProveedoresEntity proveedor
	);
}
