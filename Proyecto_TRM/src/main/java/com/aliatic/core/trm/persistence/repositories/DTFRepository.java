package com.aliatic.core.trm.persistence.repositories;

import com.aliatic.core.trm.persistence.entities.DTFEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.Optional;

public interface DTFRepository extends JpaRepository<DTFEntity, Long>  {
    Optional<DTFEntity> findByFecha(Date fecha);
    Optional<DTFEntity> findById(Long id);
    Page<DTFEntity> findByTasaContainingAndEstado(String tasa, Integer estado, Pageable pageable);
}

