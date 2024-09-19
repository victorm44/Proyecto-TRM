package com.aliatic.core.trm.persistence.repositories;

import com.aliatic.core.trm.persistence.entities.FuentesEntity;
import com.aliatic.core.trm.persistence.entities.MonedasEntity;
import com.aliatic.core.trm.persistence.entities.MetodosExtracionEntity;
import com.aliatic.core.trm.persistence.entities.TiposTasasEntity;
import com.aliatic.core.trm.persistence.entities.ProveedoresEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FuentesRepository extends JpaRepository<FuentesEntity, Long> {
	Page<FuentesEntity> findByCodigoFuenteContainingAndEstado(String codigoFuente, int estado, Pageable pageable);
	Optional<FuentesEntity> findByCodigoFuente(String codigoFuente);

	List<FuentesEntity> findByMonedaProcedenciaAndMonedaDestinoAndMetodoExtracionAndTipoTasaAndProveedoresInfo(
			MonedasEntity monedaProcedencia, MonedasEntity monedaDestino,
			MetodosExtracionEntity metodoExtracion, TiposTasasEntity tipoTasa, ProveedoresEntity proveedor
	);
}
