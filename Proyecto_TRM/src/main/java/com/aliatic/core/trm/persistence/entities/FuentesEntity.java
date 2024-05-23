package com.aliatic.core.trm.persistence.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Entidad que representa la persistencia de Tipos de tasas monetarias
 *
 * @author Arenas Silva, Juan
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name="TRM_FUENTES",     indexes = {
        @Index(columnList = "FKTPTASA"),
        @Index(columnList = "FKMONPRO"),
        @Index(columnList = "FKMONDES"),
        @Index(columnList = "FKMETEXT"),
        @Index(columnList = "FKPROVIN")
})
public class FuentesEntity extends BaseEntity{
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDFUENTE")
    private Long idFuente;
		
	
    @Size(max = 60)
    @Column(length = 60, name = "CDFUENTE")
	private String codigoFuente;
	
	
    @Size(max = 20)
    @Column(length = 20, name = "CDTPCOTI")
	private String tipoCotizacion;
	
	
    @Size(max = 100)
    @Column(length = 20, name = "DSFUENTE")
	private String denominacionTipoFuente;
	
	
    @ManyToOne
    @JoinColumn(name = "FKTPTASA")
	private TiposTasasEntity tipoTasa;
	
	
    @ManyToOne
    @JoinColumn(name = "FKMONPRO")
	private MonedasEntity monedaProcedencia;
	
	
    @ManyToOne
    @JoinColumn(name = "FKMONDES")
	private MonedasEntity monedaDestino;
	
	
    @ManyToOne
    @JoinColumn(name = "FKMETEXT")
	private MetodosExtracionEntity metodoExtracion;

    @ManyToOne
    @JoinColumn(name = "FKPROVIN")
    private ProveedoresEntity proveedoresInfo;
	
	
    @Size(max = 255)
    @Column(length = 255, name = "DSOBSERV")
	private String observaciones;
	
}
