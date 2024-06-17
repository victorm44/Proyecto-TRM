package com.aliatic.core.trm.persistence.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aliatic.core.trm.persistence.entities.MetodosExtracionEntity;

@Repository
public interface MetodosExtraccionRepository extends JpaRepository<MetodosExtracionEntity, Long>{
	
	List<MetodosExtracionEntity> findByCodigoMetodoExtracion(String codigoMetodoExtracion);

}
