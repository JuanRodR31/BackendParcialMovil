package com.parcialweb.parcial.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.parcialweb.parcial.Entidades.Entidad;

@Repository
public interface EntidadRepository extends JpaRepository<Entidad, Long> {
}
