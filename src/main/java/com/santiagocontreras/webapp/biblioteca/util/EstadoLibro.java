package com.santiagocontreras.webapp.biblioteca.util;

public enum EstadoLibro {
    DISPONIBLE("Disponible"),
    EN_PRESTAMO("En Prestamo");

    private final String message;

    EstadoLibro(String message){
        this.message = message;
    }

    @Override
    public String toString(){
        return message;
    }
}
