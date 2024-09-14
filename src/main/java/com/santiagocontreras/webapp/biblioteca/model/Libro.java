package com.santiagocontreras.webapp.biblioteca.model;

import com.santiagocontreras.webapp.biblioteca.util.EstadoLibro;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
@ToString
@Table(name = "Libros")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String isbn;
    private String nombre;
    @Column(columnDefinition = "TEXT")
    private String sipnosis;
    private String autor;
    private String editorial;
    @Enumerated(EnumType.STRING)
    private EstadoLibro disponibilidad;
    private String numeroEstanteria;
    private String cluster;
    @ManyToOne(fetch = FetchType.EAGER)
    private Categoria categoria;

    @Override
    public String toString(){
        return  id + " " + "ISBN" + isbn + " " + nombre;
    }

}
