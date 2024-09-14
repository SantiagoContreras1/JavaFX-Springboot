package com.santiagocontreras.webapp.biblioteca.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.santiagocontreras.webapp.biblioteca.model.Libro;
import com.santiagocontreras.webapp.biblioteca.model.Prestamo;
import com.santiagocontreras.webapp.biblioteca.repository.PrestamoRepository;
import com.santiagocontreras.webapp.biblioteca.util.EstadoLibro;
import com.santiagocontreras.webapp.biblioteca.util.MethodType;

@Service
public class PrestamoService implements IPrestamosService{

    @Autowired
    PrestamoRepository prestamoRepository;
    @Autowired
    LibroService libroService;

    @Override
    public List<Prestamo> listarPrestamos() {
        return prestamoRepository.findAll();
    }

    @Override
    public Integer guardarPrestamo(Prestamo prestamo, MethodType methodType) {
        List<Libro> libros = prestamo.getLibros();
        Integer token = 0;
       if(methodType == MethodType.POST){
            if(!verificarPrestamoActivo(prestamo)){
                if(!verificarLibro(prestamo)){
                    for (Libro libro : libros) {
                        libro.setDisponibilidad(EstadoLibro.EN_PRESTAMO);
                        libroService.guardarLibro(libro,MethodType.PUT);
                    }
                    prestamoRepository.save(prestamo);
                    token = 1;
                }else{
                    token = 2;
                }
            }
            
       }else if(methodType == MethodType.PUT){
             prestamoRepository.save(prestamo);
       }else{
            
       }
       return token;
    }

    @Override
    public Prestamo buscarPrestamoPorId(Long id) {
        return prestamoRepository.findById(id).orElse(null);
    }

    @Override
    public void eliminarPrestamo(Prestamo prestamo) {
        prestamoRepository.delete(prestamo);
    }

    @Override
    public Boolean verificarPrestamoActivo(Prestamo prestamoNuevo) {
        List<Prestamo> prestamos = listarPrestamos();
        Boolean flag = false;
        for (Prestamo prestamo : prestamos) {
            if(prestamo.getCliente().getDpi().equals(prestamoNuevo.getCliente().getDpi()) && prestamo.getVigencia() == true && !prestamo.getId().equals(prestamoNuevo.getId())){
                flag = true;
            }
        }
        return flag;
    }

    @Override
    public Boolean verificarLibro(Prestamo prestamoNuevo) {
        List<Libro> libros = prestamoNuevo.getLibros();
        Boolean flag = false;
        for (Libro libro : libros) {
            EstadoLibro estado = libro.getDisponibilidad();
            if(estado == EstadoLibro.EN_PRESTAMO){
                flag = true;
            }
        }
        return flag;
    }

    @Override
    public void devolverLibros(Prestamo prestamo){
        if(prestamo != null && prestamo.getVigencia()){
            List<Libro> libros = prestamo.getLibros();
            for(Libro libro : libros){
                libro.setDisponibilidad(EstadoLibro.DISPONIBLE);
                libroService.guardarLibro(libro,MethodType.PUT);
            }
            prestamo.setVigencia(false);
            prestamo.setFechaDeDevolucion(Date.valueOf(LocalDate.now()));
            prestamoRepository.save(prestamo);
        }
    }

}
