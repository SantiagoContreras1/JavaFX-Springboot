package com.santiagocontreras.webapp.biblioteca.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.santiagocontreras.webapp.biblioteca.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    
}
