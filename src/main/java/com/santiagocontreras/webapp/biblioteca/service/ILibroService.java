package com.santiagocontreras.webapp.biblioteca.service;

import java.util.List;

import com.santiagocontreras.webapp.biblioteca.model.Libro;
import com.santiagocontreras.webapp.biblioteca.util.MethodType;

public interface ILibroService {
    public List<Libro> listarLibros();

    public Libro guardarLibro(Libro libro, MethodType methodType);

    public Libro buscarLibroPorId(Long id);

    public void eliminarLibro(Libro libro);
    
}
