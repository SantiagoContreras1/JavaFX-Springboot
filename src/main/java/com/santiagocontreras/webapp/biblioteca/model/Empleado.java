package com.santiagocontreras.webapp.biblioteca.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
@ToString
@Table(name = "Empleados")
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long empleadoId;
    private String nombre;
    private String apellido;
    private String telefono;
    @Column(columnDefinition = "TEXT")
    private String direccion;
    private String dpi;    

    @Override
    public String toString(){
        return empleadoId + " " + nombre + " " + apellido;
    }
}
