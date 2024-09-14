package com.santiagocontreras.webapp.biblioteca.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
@ToString
@Table(name = "Clientes")
public class Cliente {

    @Id
    private Long dpi;
    @NotNull(message = "NombreCliente no puede ser nulo")
    private String nombreCliente;
    @NotNull(message = "ApellidoCliente no puede ser nulo")
    private String apellidoCliente;
    @NotNull(message = "TelefonoCliente no puede ser nulo")
    private String telefonoCliente;
    
    @Override
    public String toString(){
        return dpi + " " + nombreCliente + " " + apellidoCliente;
    }
}