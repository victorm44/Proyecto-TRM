package com.aliatic.core.trm.persistence.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aliatic.core.trm.persistence.entities.TiposTasasEntity;

@Repository
public interface TiposTasasRepository extends JpaRepository<TiposTasasEntity, Long> {
	Optional<TiposTasasEntity> findByIdTipoTasa(Long idTipoTasa);
	List<TiposTasasEntity> findByCodigoTipoTasa(String codigoTipoTasa);
}
