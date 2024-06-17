package com.aliatic.core.trm.persistence.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aliatic.core.trm.persistence.entities.MonedasEntity;

@Repository
public interface MonedasRepository extends JpaRepository<MonedasEntity, Long>{
	
	List<MonedasEntity> findByCodigoMoneda(String codigoMoneda);

}
