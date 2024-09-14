package com.santiagocontreras.webapp.biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.santiagocontreras.webapp.biblioteca.model.Empleado;

public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {

}
