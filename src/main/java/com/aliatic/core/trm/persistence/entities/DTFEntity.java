package com.aliatic.core.trm.persistence.entities;

import java.util.Date;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper=true)
@Entity
@Table(name="TRM_DTF")
public class DTFEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDDTF")
    private Long idDTF;

    @Column(name = "FECREDTF")
    private Date fecha;

    @Column(name = "CDTPTASA")
    private String tasa;

    @Column(name = "NMVALANT")
    private float valorAnterior;

    @Column(name = "NMVALACT")
    private float valorActual;

    @Column(name = "NMVARIAC")
    private float variacion;
}