package com.parcialweb.parcial.Entidades;

import java.util.List;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Table;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Entidad")
public class Entidad {
    private Long id;
    private String nit;
    private String nombre;
    private List<Contrato> contratos;
}
