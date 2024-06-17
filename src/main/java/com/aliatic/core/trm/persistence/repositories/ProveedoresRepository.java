package com.aliatic.core.trm.persistence.repositories;

import com.aliatic.core.trm.persistence.entities.MetodosExtracionEntity;
import com.aliatic.core.trm.persistence.entities.ProveedoresEntity;
import com.aliatic.core.trm.persistence.entities.TiposTasasEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProveedoresRepository extends JpaRepository<ProveedoresEntity, Long> {

}
