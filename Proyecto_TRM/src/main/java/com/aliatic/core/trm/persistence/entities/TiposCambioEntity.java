package com.aliatic.core.trm.persistence.entities;

import java.util.Date;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Entidad que representa la persistencia de los tipos de cambio
 *
 * @author Arenas Silva, Juan
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper=true)
@Entity
@Table(name="TRM_TIPOSCAMBIO")
public class TiposCambioEntity extends BaseEntity {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDTPCAMB")
    private Long idTipoCambio;
	
	
    @ManyToOne
    @JoinColumn(name = "FKFUENTE")
	private FuentesEntity fuentes;
	
	
	@Column(name = "NMIMPORT")
	private float importe;
	
	
    @Column(name = "FEINIVIG")
	private Date fechaInicioVigencia;
	
	
    @Column(name = "FEFINVIG")
	private Date fechafinVigencia;
	
	
	@Column(name = "NMANSEVI")
	private int anioSemanaVigencia;
}
