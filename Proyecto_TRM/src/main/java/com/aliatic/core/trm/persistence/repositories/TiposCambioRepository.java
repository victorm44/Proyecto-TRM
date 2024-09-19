package com.aliatic.core.trm.persistence.repositories;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.aliatic.core.trm.persistence.entities.TiposCambioEntity;

@Repository
public interface TiposCambioRepository extends JpaRepository<TiposCambioEntity, Long>{

    Optional<TiposCambioEntity> findById(Long id);
    Page<TiposCambioEntity> findByFuentes_IdFuente(Long idFuente, Pageable pageable);
}
