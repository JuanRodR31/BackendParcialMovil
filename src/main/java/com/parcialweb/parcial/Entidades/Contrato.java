package com.parcialweb.parcial.Entidades;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity

@Data
public class Contrato {
    private Long id;
    private String identificador;
    private String nombreCoontratante;
    private String documentoContratante;
    private String nombreContratista;
    private String documentoContratista;
    private Date fechaInicio;
    private Date fechaFin;
    @OneToOne(mappedBy = "contrato")
    private Entidad entidad;
}