package com.aliatic.core.trm.persistence.entities;

import java.time.LocalDateTime;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


/**
 * Entidad que representa la persistencia de parámetros generales de la aplicación
 *
 * @author Arenas Silva, Juan
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name="TRM_PARAMETROS",     indexes = {
        @Index(columnList = "CDPARAME"),
        @Index(columnList = "CDCOMPON")
})
public class ParametrosEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDPARAME")
    private Long idParametro;

    @Size(max = 60)
    @Column(length = 60, name = "CDPARAME")
    private String parametro;

    @Size(max = 10)
    @Column(length = 10, name = "CDCOMPON")
    private String sistemaOComponente;
    
    @Size(max = 100)
    @Column(length = 100, name = "DSTEXTO1")
    private String texto1;
    
    @Size(max = 100)
    @Column(length = 100, name = "DSTEXTO2")
    private String texto2;
    
    @Column(name = "FEFECHA1")
    private LocalDateTime fecha1;
    
    @Column(name = "FEFECHA2")
    private LocalDateTime  fecha2;
    
    @Column(name = "NMNUMER1")
    private Double numero1;
    
    @Column(name = "NMNUMER2")
    private Double numero2;
    
}