package com.santiagocontreras.webapp.biblioteca.controller.FXController;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.santiagocontreras.webapp.biblioteca.model.Cliente;
import com.santiagocontreras.webapp.biblioteca.service.ClienteService;
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
public class ClienteControllerView implements Initializable {

    @FXML
    TextField tfId, tfNombre, tfApellido, tfTelefono, tfBuscar;
    @FXML
    Button btnGuardar, btnLimpiar, btnEliminar, btnRegresar, btnBuscar;
    @FXML
    TableView tablaClientes;
    @FXML
    TableColumn colId, colNombre, colApellido, colTelefono;

    @Setter
    private Main stage;

    @Autowired
    ClienteService clienteService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
    }

     public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnGuardar){
            Long id = Long.parseLong(tfId.getText());
            if(clienteService.buscarClientePorId(id) != null){
                editarCliente();
            }else{
                agregarCliente();
            }
        }else if(event.getSource() == btnEliminar){
            eliminarCliente();
        }else if(event.getSource() == btnLimpiar){
            limpiarFormulario();
        }else if(event.getSource() == btnRegresar){
            stage.indexView();
        }else if(event.getSource() == btnBuscar){
            tablaClientes.getItems().clear();
            if(tfBuscar.getText().isBlank()){
                cargarDatos();
            }else{
                tablaClientes.getItems().add(buscarCliente());
                colId.setCellValueFactory(new PropertyValueFactory<Cliente,Long>("dpi"));
                colNombre.setCellValueFactory(new PropertyValueFactory<Cliente,String>("nombreCliente"));
                colApellido.setCellValueFactory(new PropertyValueFactory<Cliente,String>("apellidoCliente"));
                colTelefono.setCellValueFactory(new PropertyValueFactory<Cliente,String>("telefonoCliente"));
            }
        }
    }

    public void cargarDatos(){
        tablaClientes.setItems(listarClientes());
        colId.setCellValueFactory(new PropertyValueFactory<Cliente,Long>("dpi"));
        colNombre.setCellValueFactory(new PropertyValueFactory<Cliente,String>("nombreCliente"));
        colApellido.setCellValueFactory(new PropertyValueFactory<Cliente,String>("apellidoCliente"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<Cliente,String>("telefonoCliente"));
    }

    public void cargarFormEditar(){
        Cliente cliente = (Cliente)tablaClientes.getSelectionModel().getSelectedItem();
        tfId.setText(Long.toString(cliente.getDpi()));
        tfNombre.setText(cliente.getNombreCliente());
        tfApellido.setText(cliente.getApellidoCliente());
        tfTelefono.setText(cliente.getTelefonoCliente());
    }

    public void limpiarFormulario(){
        tfId.clear();
        tfNombre.clear();
        tfApellido.clear();
        tfTelefono.clear();
    }

    public ObservableList<Cliente> listarClientes(){
        return FXCollections.observableList(clienteService.listarClieantes());
    }

    public void agregarCliente(){
        Cliente cliente = new Cliente();
        cliente.setDpi(Long.parseLong(tfId.getText()));
        cliente.setNombreCliente(tfNombre.getText());
        cliente.setApellidoCliente(tfApellido.getText());
        cliente.setTelefonoCliente(tfTelefono.getText());
        clienteService.guardarCliente(cliente);
        cargarDatos();
    }

    public void editarCliente(){
        Cliente cliente = clienteService.buscarClientePorId(Long.parseLong(tfId.getText()));
        cliente.setNombreCliente(tfNombre.getText());
        cliente.setApellidoCliente(tfApellido.getText());
        cliente.setTelefonoCliente(tfTelefono.getText());
        clienteService.guardarCliente(cliente);
        cargarDatos();
    }

    public void eliminarCliente(){
        Cliente cliente = clienteService.buscarClientePorId(Long.parseLong(tfId.getText()));
        clienteService.eliminarCliente(cliente);
        cargarDatos();
    }

    public Cliente buscarCliente(){
        return clienteService.buscarClientePorId(Long.parseLong(tfBuscar.getText()));
    }

}
