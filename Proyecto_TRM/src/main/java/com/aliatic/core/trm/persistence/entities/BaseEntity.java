package com.aliatic.core.trm.persistence.entities;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Entidad primaria con los campos administrativos
 *
 * @author Arenas Silva, Juan
 */

@Data
@MappedSuperclass
public class BaseEntity {
	
	
    @Size(max = 60)
    @Column(length = 60, name = "CDUSCREA")
	private String usuarioCreacion;
	
	
    @Column(name = "FECREACI")
	private Date fechaCreacion = new Date();
	
	
    @Size(max = 60)
    @Column(length = 60, name = "CDUSMODI")
	private String usuarioModificacion;
	
	
    @Column(name = "FEMODIFI")
	private Date fechaModificacion = new Date();
	
	
	@Column(name = "CDESTADO")
    private int estado = 1;

}
