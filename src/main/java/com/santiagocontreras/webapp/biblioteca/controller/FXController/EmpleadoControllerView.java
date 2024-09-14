package com.santiagocontreras.webapp.biblioteca.controller.FXController;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.santiagocontreras.webapp.biblioteca.model.Empleado;
import com.santiagocontreras.webapp.biblioteca.service.EmpleadoService;
import com.santiagocontreras.webapp.biblioteca.system.Main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.Setter;

@Component
public class EmpleadoControllerView implements Initializable {

    @FXML
    TextField tFId, tfNombre, tfApellido,tfTelefono, tfDireccion, tfDpi, tfBuscar;
    @FXML
    Button btnGuardar, btnLimpiar, btnEliminar, btnRegresar, btnBuscar;
    @FXML
    TableView tablaEmpleados;
    @FXML
    TableColumn colId, colNombre, colApellido, colTelefono, colDireccion, colDpi;

    @Setter
    private Main stage;

    @Autowired
    EmpleadoService empleadoService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
    }

    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnGuardar){
            if(tFId.getText().isBlank()){
                agregarEmpleado();
            }else{
                editarEmpleado();
            }
        }else if(event.getSource() == btnEliminar){
            eliminarEmpleado();
        }else if(event.getSource() == btnLimpiar){
            limpiarFormulario();
        }else if(event.getSource() == btnRegresar){
            stage.indexView();
        }else if(event.getSource() == btnBuscar){
            tablaEmpleados.getItems().clear();
            if(tfBuscar.getText().isBlank()){
                cargarDatos();
            }else{
                tablaEmpleados.getItems().add(buscarEmpleado());
                colId.setCellValueFactory(new PropertyValueFactory<Empleado,Long>("empleadoId"));
                colNombre.setCellValueFactory(new PropertyValueFactory<Empleado,String>("nombre"));
                colApellido.setCellValueFactory(new PropertyValueFactory<Empleado,String>("apellido"));
                colTelefono.setCellValueFactory(new PropertyValueFactory<Empleado,String>("telefono"));
                colDireccion.setCellValueFactory(new PropertyValueFactory<Empleado,String>("direccion"));
                colDpi.setCellValueFactory(new PropertyValueFactory<Empleado,String>("dpi"));
            }
        }
    }

    public void cargarDatos(){
        tablaEmpleados.setItems(listarEmpleados());
        colId.setCellValueFactory(new PropertyValueFactory<Empleado,Long>("empleadoId"));
        colNombre.setCellValueFactory(new PropertyValueFactory<Empleado,String>("nombre"));
        colApellido.setCellValueFactory(new PropertyValueFactory<Empleado,String>("apellido"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<Empleado,String>("telefono"));
        colDireccion.setCellValueFactory(new PropertyValueFactory<Empleado,String>("direccion"));
        colDpi.setCellValueFactory(new PropertyValueFactory<Empleado,String>("dpi"));
    }

    public void cargarFormEditar(){
        Empleado empleado = (Empleado)tablaEmpleados.getSelectionModel().getSelectedItem();
        tFId.setText(Long.toString(empleado.getEmpleadoId()));
        tfNombre.setText(empleado.getNombre());
        tfApellido.setText(empleado.getApellido());
        tfTelefono.setText(empleado.getTelefono());
        tfDireccion.setText(empleado.getDireccion());
        tfDpi.setText(empleado.getDpi());
    }

    public void limpiarFormulario(){
        tFId.clear();
        tfNombre.clear();
        tfApellido.clear();
        tfTelefono.clear();
        tfDireccion.clear();
        tfDpi.clear();
    }

    public ObservableList<Empleado> listarEmpleados(){
        return FXCollections.observableList(empleadoService.listarEmpleados());
    }

    public void agregarEmpleado(){
        Empleado empleado = new Empleado();
        empleado.setNombre(tfNombre.getText());
        empleado.setApellido(tfApellido.getText());
        empleado.setTelefono(tfTelefono.getText());
        empleado.setDireccion(tfDireccion.getText());
        empleado.setDpi(tfDpi.getText());
        empleadoService.guardarEmpleado(empleado);
        cargarDatos();
    }

    public void editarEmpleado(){
        Empleado empleado = empleadoService.buscarEmpleadoPorId(Long.parseLong(tFId.getText()));
        empleado.setNombre(tfNombre.getText());
        empleado.setApellido(tfApellido.getText());
        empleado.setTelefono(tfTelefono.getText());
        empleado.setDireccion(tfDireccion.getText());
        empleado.setDpi(tfDpi.getText());
        empleadoService.guardarEmpleado(empleado);
        cargarDatos();
    }

    public void eliminarEmpleado(){
        Empleado empleado = empleadoService.buscarEmpleadoPorId(Long.parseLong(tFId.getText()));
        empleadoService.eliminarEmpleado(empleado);
        cargarDatos();
    }

    public Empleado buscarEmpleado(){
        return empleadoService.buscarEmpleadoPorId(Long.parseLong(tfBuscar.getText()));
    }
}
