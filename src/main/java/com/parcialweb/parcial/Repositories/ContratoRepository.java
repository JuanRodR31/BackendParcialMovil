package com.parcialweb.parcial.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.parcialweb.parcial.Entidades.Contrato;

@Repository
public interface ContratoRepository extends JpaRepository<Contrato, Long> {
}
