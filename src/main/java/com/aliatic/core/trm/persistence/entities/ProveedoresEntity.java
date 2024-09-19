package com.aliatic.core.trm.persistence.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper= true)
@Entity
@Table(name="TRM_PROVEEDORES", indexes = @Index(columnList = "CDPROVIN"))
public class ProveedoresEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDPROVIN")
    private Long idProveedoresInfo;


    @Size(max = 500)
    @Column(length = 500, name = "URLPROVIN")
    private String urlProveedores;

    @Size(max = 100)
    @Column(length = 100, name = "CDPROVIN")
    private String codigoProveedores;

    @Size(max = 100)
    @Column(length = 100, name = "DSPROVIN")
    private String denominacionProveedores;
}
