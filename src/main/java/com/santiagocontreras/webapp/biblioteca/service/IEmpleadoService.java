package com.santiagocontreras.webapp.biblioteca.service;

import java.util.List;

import com.santiagocontreras.webapp.biblioteca.model.Empleado;

public interface IEmpleadoService {
    public List<Empleado> listarEmpleados();

    public Boolean guardarEmpleado(Empleado empleado);

    public Empleado buscarEmpleadoPorId(Long id);

    public void eliminarEmpleado(Empleado empleado);

    public Boolean verificarDpiDupicado(Empleado empleadoNuevo);
}
