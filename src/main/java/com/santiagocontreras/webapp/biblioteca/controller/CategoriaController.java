package com.santiagocontreras.webapp.biblioteca.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.santiagocontreras.webapp.biblioteca.model.Categoria;
import com.santiagocontreras.webapp.biblioteca.service.CategoriaService;

@Controller
@RestController
@RequestMapping(value = "")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class CategoriaController {

    @Autowired
    CategoriaService categoriaService;

    @GetMapping("/categorias")
    public List<Categoria> listarCategorias() {
        return categoriaService.listarCategorias();
    }

    @GetMapping("/categoria")
    public ResponseEntity<Categoria> buscarCategoriaPorId(@RequestParam Long id){
        try {
            return ResponseEntity.ok(categoriaService.buscarCategoriaPorId(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/categoria")
    public ResponseEntity<Map<String, Boolean>> agregarCategoria(@RequestBody Categoria categoria) {
        Map<String, Boolean> response = new HashMap<>();
        try {
            if(categoriaService.guardarCategoria(categoria)){
                response.put("Se agrego con Exito!..", Boolean.TRUE);
                return ResponseEntity.ok(response);
            }else{
                response.put("Categoria ya existente", Boolean.FALSE);
                return ResponseEntity.badRequest().body(response);
            }
        } catch (Exception e) {
            response.put("Error al agregar!", Boolean.FALSE);
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PutMapping("/categoria")
    public ResponseEntity<Map<String,String>> editarCategoria(@RequestParam Long id, @RequestBody Categoria nuevaCategoria){
        Map<String, String> response = new HashMap<>();
        try {
            Categoria categoriaOld = categoriaService.buscarCategoriaPorId(id);
            categoriaOld.setNombreCategoria(nuevaCategoria.getNombreCategoria());
            categoriaService.guardarCategoria(categoriaOld);
            response.put("message", "Categoria editada con exito!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message", "La Categoria no se pudo editar!");
            return ResponseEntity.badRequest().body(response);
        }
    }

    @DeleteMapping("/categoria")
    public ResponseEntity<Map<String,String>> eliminarCategoria(@RequestParam Long id){
        Map<String, String> response = new HashMap<>(); 
        try {
        Categoria categoria = categoriaService.buscarCategoriaPorId(id);
        categoriaService.eliminarCategoria(categoria);
        response.put("message", "Categoria eliminada con exito!");
        return ResponseEntity.ok(response);
       } catch (Exception e) {
        response.put("menssage", "Error no se pudo eliminar la categhoria");
        return ResponseEntity.badRequest().body(response);
       }
    }
}
