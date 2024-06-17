package com.aliatic.core.trm.persistence.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aliatic.core.trm.persistence.entities.ParametrosEntity;

@Repository
public interface ParametrosRepository extends JpaRepository<ParametrosEntity, Long>{
	
	Page<ParametrosEntity> findByParametroContaining(String parametro, Pageable pageable);
	Optional<ParametrosEntity> findByParametro(String parametro);
	Optional<ParametrosEntity> findById(Long id);
}
