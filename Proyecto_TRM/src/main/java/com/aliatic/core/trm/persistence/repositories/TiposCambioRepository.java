package com.aliatic.core.trm.persistence.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.aliatic.core.trm.persistence.entities.TiposCambioEntity;

@Repository
public interface TiposCambioRepository extends JpaRepository<TiposCambioEntity, Long>{
	
}
