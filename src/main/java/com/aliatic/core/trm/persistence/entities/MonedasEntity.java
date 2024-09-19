package com.aliatic.core.trm.persistence.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Entidad que representa la persistencia de los maestros de monedas
 *
 * @author Arenas Silva, Juan
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name="TRM_MONEDAS", indexes = @Index(columnList = "CDMONEDA"))
public class MonedasEntity extends BaseEntity {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDMONEDA")
    private Long idMoneda;
	
	
    @Size(max = 60)
    @Column(length = 60, name = "CDMONEDA")
	private String codigoMoneda;
	
	
    @Size(max = 60)
    @Column(length = 60, name = "CDISOMON")
	private String codigoISO;
	

    @Size(max = 60)
    @Column(length = 60, name = "CDALTMON")
	private String codigoAlternativo;
	
}
