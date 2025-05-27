package com.parcialweb.parcial.DTO;

import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntidadDTO {
    private Long id;
    private String nit;
    private String nombre;
    private List<ContratoDTO> contratos;
}

