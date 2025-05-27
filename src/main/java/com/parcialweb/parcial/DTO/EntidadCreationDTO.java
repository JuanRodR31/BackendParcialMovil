package com.parcialweb.parcial.DTO;

import lombok.Data;
import java.util.List;

@Data
public class EntidadCreationDTO {
    private String nit;
    private String nombre;
    private List<ContratoCreationDTO> contratos;
}
