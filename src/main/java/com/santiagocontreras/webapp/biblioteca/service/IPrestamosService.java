package com.santiagocontreras.webapp.biblioteca.service;


import java.util.List;

import com.santiagocontreras.webapp.biblioteca.model.Prestamo;
import com.santiagocontreras.webapp.biblioteca.util.MethodType;

public interface IPrestamosService {
    public List<Prestamo> listarPrestamos();

    public Integer guardarPrestamo(Prestamo prestamo, MethodType methodType);

    public Prestamo buscarPrestamoPorId(Long id);

    public void eliminarPrestamo(Prestamo prestamo);

    public Boolean verificarPrestamoActivo(Prestamo prestamoNuevo);

    public Boolean verificarLibro(Prestamo prestamoNuevo);

    public void devolverLibros(Prestamo Prestamo);
}
