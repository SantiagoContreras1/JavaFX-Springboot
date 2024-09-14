package com.santiagocontreras.webapp.biblioteca.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.santiagocontreras.webapp.biblioteca.model.Empleado;
import com.santiagocontreras.webapp.biblioteca.repository.EmpleadoRepository;

@Service
public class EmpleadoService implements IEmpleadoService {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Override
    public List<Empleado> listarEmpleados() {
       return empleadoRepository.findAll();
    }

    @Override
    public Boolean guardarEmpleado(Empleado empleado) {
        if(!verificarDpiDupicado(empleado)){
            empleadoRepository.save(empleado);
            return true;
        }
         return false;
        
    }

    @Override
    public Empleado buscarEmpleadoPorId(Long id) {
        return empleadoRepository.findById(id).orElse(null);
    }

    @Override
    public void eliminarEmpleado(Empleado empleado) {
        empleadoRepository.delete(empleado);
    }

    @Override
    public Boolean verificarDpiDupicado(Empleado empleadoNuevo) {
        List<Empleado> empleados = listarEmpleados();
        Boolean flag = false;
        for (Empleado empleado : empleados) {
            if(empleado.getDpi().equals(empleadoNuevo.getDpi()) && !empleado.getEmpleadoId().equals(empleadoNuevo.getEmpleadoId())){
                flag = true;
            }
        }
        return flag;
    }

}
