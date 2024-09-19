package com.aliatic.core.trm.persistence.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Entidad que representa la persistencia de Métodos de extracción de divisas
 *
 * @author Arenas Silva, Juan
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper=true)
@Entity
@Table(name="TRM_METODOSEXTRACCION")
public class MetodosExtracionEntity extends BaseEntity {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDMETEXT")
    private Long idMetodoExtraccion;
	
	
    @Size(max = 60)
    @Column(length = 60, name = "CDMETEXT")
	private String codigoMetodoExtracion;
	
	
    @Size(max = 100)
    @Column(length = 100, name = "DSMETEXT")
	private String denominacionMetodoExtracion;

}
