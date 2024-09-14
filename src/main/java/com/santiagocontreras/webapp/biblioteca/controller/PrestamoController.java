package com.santiagocontreras.webapp.biblioteca.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.santiagocontreras.webapp.biblioteca.model.Prestamo;
import com.santiagocontreras.webapp.biblioteca.service.PrestamoService;
import com.santiagocontreras.webapp.biblioteca.util.MethodType;

@Controller
@RestController
@RequestMapping(value =  "")
public class PrestamoController {

    @Autowired
    PrestamoService prestamoService;

    @GetMapping("/prestamos")
    public ResponseEntity<List<Prestamo>> listarPrestamos(){
        try {
            return ResponseEntity.ok(prestamoService.listarPrestamos());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/prestamo")
    public ResponseEntity<Prestamo> buscarPrestamo(@RequestParam Long id){
        try {
            return ResponseEntity.ok(prestamoService.buscarPrestamoPorId(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/prestamo")
    public ResponseEntity<Map<String, String>> agregarPrestamo(@RequestBody Prestamo prestamo){
        Map<String, String> response = new HashMap<>();
        try {
            if(prestamoService.guardarPrestamo(prestamo, MethodType.POST ).equals(1)){
                response.put("message","Prestamo creado con exito");
                return ResponseEntity.ok(response);
            }else if(prestamoService.guardarPrestamo(prestamo, MethodType.POST).equals(2)){
                response.put("Message", "Un libro seleccionado no esta disponible");
                return ResponseEntity.badRequest().body(response);
            }else{
                response.put("Message", "El Usuario tiene un prestamo vigente");
                return ResponseEntity.badRequest().body(response);
            }
        } catch (Exception e) {
            response.put("message", "Error");
            response.put("err", "Hubo error al crear el Prestamo");
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PutMapping("/prestamo")
    public ResponseEntity<Map<String, String>> editarPrestamo(@RequestParam Long id, @RequestBody Prestamo nuevoPrestamo){
        Map<String, String> response = new HashMap<>();
        try {
            Prestamo prestamo = prestamoService.buscarPrestamoPorId(id);
            prestamo.setFechaDePrestamo(nuevoPrestamo.getFechaDePrestamo());
            prestamo.setFechaDeDevolucion(nuevoPrestamo.getFechaDeDevolucion());
            prestamo.setVigencia(nuevoPrestamo.getVigencia());
            prestamo.setEmpleado(nuevoPrestamo.getEmpleado());
            prestamo.setCliente(nuevoPrestamo.getCliente());
            prestamo.setLibros(nuevoPrestamo.getLibros());
            prestamoService.guardarPrestamo(prestamo, MethodType.PUT);
            response.put("messge", "Prestamo editado con exito!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message","Error");
            response.put("err", "Hubo un error al editar el prestamo");
            return ResponseEntity.badRequest().body(response);
        }
    }

    @DeleteMapping("/prestamo")
    public ResponseEntity<Map<String, String>> eliminarPrstamo(@RequestParam Long id){
        Map<String, String> response = new HashMap<>();
        try {
            Prestamo prestamo = prestamoService.buscarPrestamoPorId(id);
            prestamoService.eliminarPrestamo(prestamo);
            response.put("message","Prestamo eliminado con exito!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message", "Error");
            response.put("err", "Hubo un error al eliminar el prestamo");
            return ResponseEntity.badRequest().body(response);
        }
   }
    
}
