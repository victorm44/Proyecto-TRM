package com.aliatic.core.trm.persistence.repositories;

import com.aliatic.core.trm.persistence.entities.ProveedoresEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProveedoresRepository extends JpaRepository<ProveedoresEntity, Long> {
    Page<ProveedoresEntity> findByEstado(int estado, Pageable pageable);
}
