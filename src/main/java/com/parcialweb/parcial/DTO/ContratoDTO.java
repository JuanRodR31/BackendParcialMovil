package com.parcialweb.parcial.DTO;

import lombok.Data;
import java.util.Date;

@Data
public class ContratoDTO {
    private Long id;
    private String identificador;
    private String nombreContratante;
    private String documentoContratante;
    private String nombreContratista;
    private String documentoContratista;
    private Date fechaInicio;
    private Date fechaFin;
    private Long entidadId;
}
