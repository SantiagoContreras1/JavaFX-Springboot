package com.santiagocontreras.webapp.biblioteca.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.santiagocontreras.webapp.biblioteca.model.Libro;
import com.santiagocontreras.webapp.biblioteca.repository.LibroRepository;
import com.santiagocontreras.webapp.biblioteca.util.EstadoLibro;
import com.santiagocontreras.webapp.biblioteca.util.MethodType;

@Service
public class LibroService implements ILibroService {

    @Autowired
    LibroRepository libroRepository;

    @Override
    public List<Libro> listarLibros() {
        return libroRepository.findAll();
    }

    @Override
    public Libro guardarLibro(Libro libro, MethodType methodType) {
        if(methodType == MethodType.POST){
            libro.setDisponibilidad(EstadoLibro.DISPONIBLE);
            return libroRepository.save(libro);
        }else if(methodType == MethodType.PUT){
            return libroRepository.save(libro);
        }else{
            return null;
        }
       
    }

    @Override
    public Libro buscarLibroPorId(Long id) {
        return libroRepository.findById(id).orElse(null);
    }

    @Override
    public void eliminarLibro(Libro libro) {
       libroRepository.delete(libro);
    }

}
