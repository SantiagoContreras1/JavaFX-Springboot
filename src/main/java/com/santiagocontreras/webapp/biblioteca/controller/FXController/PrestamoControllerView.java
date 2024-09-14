package com.santiagocontreras.webapp.biblioteca.controller.FXController;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.santiagocontreras.webapp.biblioteca.model.Cliente;
import com.santiagocontreras.webapp.biblioteca.model.Empleado;
import com.santiagocontreras.webapp.biblioteca.model.Libro;
import com.santiagocontreras.webapp.biblioteca.model.Prestamo;
import com.santiagocontreras.webapp.biblioteca.service.ClienteService;
import com.santiagocontreras.webapp.biblioteca.service.EmpleadoService;
import com.santiagocontreras.webapp.biblioteca.service.LibroService;
import com.santiagocontreras.webapp.biblioteca.service.PrestamoService;
import com.santiagocontreras.webapp.biblioteca.system.Main;
import com.santiagocontreras.webapp.biblioteca.util.MethodType;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import lombok.Setter;

@Component
public class PrestamoControllerView implements Initializable {

    @FXML
    TextField tfId,tfFechaPrestamo,tfFechaDevolucion, tfVigencia, tfBuscar;
    @FXML
    Button btnGuardar,btnLimpiar,btnEliminar,btnBuscar,btnRegresar, btnDevolver;
    @FXML
    ComboBox <Empleado>cmbEmpleados;
    @FXML
    ComboBox<Cliente>cmbClientes;
    @FXML
    ComboBox<Libro> cmbLibros3, cmbLibros2, cmbLibros1;
    @FXML
    TableView tblPrestamos;
    @FXML
    TableColumn colId, colFechaPrestamo, colFechaDevolucion, colVigencia, colEmpleado, colCliente, colLibros;

    
    @Setter
    private Main stage;

    private List<Libro> librosSeleccionados = new ArrayList<>();

    @Autowired
    LibroService libroService;

    @Autowired
    EmpleadoService empleadoService;

    @Autowired
    ClienteService clienteService;

    @Autowired
    PrestamoService prestamoService;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
        llenarCmbs();
    }

    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnGuardar){
            if(tfId.getText().isBlank()){
                agregarPrestamos();
            }else{
                editarPrestamo();
            }
        }else if(event.getSource() == btnEliminar){
            eliminarPrestamo();
        }else if(event.getSource() == btnLimpiar){
            limpiarFormulario();
        }else if(event.getSource() == btnRegresar){
            stage.indexView();
        }else if(event.getSource() == btnBuscar){
            tblPrestamos.getItems().clear();
            buscarPrestamoYMostar();
        }else if(event.getSource() == btnDevolver){
            devolverLibros();
        }
    }

    public void limpiarFormulario(){
        tfId.clear();
        tfVigencia.clear();
        tfFechaPrestamo.clear();
        tfFechaDevolucion.clear();
        cmbEmpleados.getSelectionModel().clearSelection();
        cmbClientes.getSelectionModel().clearSelection();
        cmbLibros1.getSelectionModel().clearSelection();
        cmbLibros2.getSelectionModel().clearSelection();
        cmbLibros3.getSelectionModel().clearSelection();
    }

    public void cargarDatos(){
        tblPrestamos.setItems(listarPrestamos());
        colId.setCellValueFactory(new PropertyValueFactory<Prestamo,Long>("id"));
        colFechaPrestamo.setCellValueFactory(new PropertyValueFactory<Prestamo,Date>("fechaDePrestamo"));
        colFechaDevolucion.setCellValueFactory(new PropertyValueFactory<Prestamo,Date>("fechaDeDevolucion"));
        colVigencia.setCellValueFactory(new PropertyValueFactory<Prestamo,Boolean>("vigencia"));
        colEmpleado.setCellValueFactory(new PropertyValueFactory<Prestamo,Empleado>("empleado"));
        colCliente.setCellValueFactory(new PropertyValueFactory<Prestamo,Cliente>("cliente"));
        colLibros.setCellValueFactory(new PropertyValueFactory<Prestamo, List<Libro>>("libros"));
        colLibros.setCellFactory(new Callback<TableColumn<Prestamo, List<Libro>>, TableCell<Prestamo, List<Libro>>>() {
            @Override
            public TableCell<Prestamo, List<Libro>> call(TableColumn<Prestamo, List<Libro>> param) {
                return new TableCell<Prestamo, List<Libro>>() {
                    @Override
                    protected void updateItem(List<Libro> libros, boolean empty) {
                        super.updateItem(libros, empty);
                        if (empty || libros == null) {
                            setText(null);
                        } else {
                            setText(librosToString(libros));
                        }
                    }
                };
            }
        });
        
    }

    public void cargarFormEditar(){
        Prestamo prestamo = (Prestamo) tblPrestamos.getSelectionModel().getSelectedItem();
        tfId.setText(Long.toString(prestamo.getId()));
        tfFechaPrestamo.setText(prestamo.getFechaDePrestamo().toString());
        tfFechaDevolucion.setText(prestamo.getFechaDeDevolucion().toString());
        tfVigencia.setText(prestamo.getVigencia().toString());
        cmbEmpleados.getSelectionModel().select(prestamo.getEmpleado());
        cmbClientes.getSelectionModel().select(prestamo.getCliente());
        if(prestamo.getLibros().size() == 1){
            cmbLibros1.getSelectionModel().select(prestamo.getLibros().get(0));
        }else if(prestamo.getLibros().size() == 2){
            cmbLibros1.getSelectionModel().select(prestamo.getLibros().get(0));
            cmbLibros2.getSelectionModel().select(prestamo.getLibros().get(1));
        }else if(prestamo.getLibros().size() == 3){
            cmbLibros1.getSelectionModel().select(prestamo.getLibros().get(0));
            cmbLibros2.getSelectionModel().select(prestamo.getLibros().get(1));
            cmbLibros3.getSelectionModel().select(prestamo.getLibros().get(2));
        }
    }

    public ObservableList<Prestamo> listarPrestamos(){
        return FXCollections.observableList(prestamoService.listarPrestamos());
    }

    public void agregarPrestamos(){
        agregarLibrosSeleccionados();
        Prestamo prestamo = new Prestamo();
        Empleado empleadoSeleccionado = cmbEmpleados.getSelectionModel().getSelectedItem();
        Cliente clienteSeleccionado = cmbClientes.getSelectionModel().getSelectedItem();
        prestamo.setFechaDePrestamo(Date.valueOf(LocalDate.now()));
        prestamo.setFechaDeDevolucion(null);
        prestamo.setVigencia(true);
        prestamo.setEmpleado(empleadoSeleccionado);
        prestamo.setCliente(clienteSeleccionado);
        prestamo.setLibros(librosSeleccionados);
        prestamoService.guardarPrestamo(prestamo, MethodType.POST);
        cargarDatos();
    }

    public void editarPrestamo(){
        Prestamo prestamo = prestamoService.buscarPrestamoPorId(Long.parseLong(tfId.getText()));
        agregarLibrosSeleccionados();
        Empleado empleadoSeleccionado = cmbEmpleados.getSelectionModel().getSelectedItem();
        Cliente clienteSeleccionado = cmbClientes.getSelectionModel().getSelectedItem();
        prestamo.setFechaDePrestamo(Date.valueOf(LocalDate.now()));
        prestamo.setFechaDeDevolucion(null);
        prestamo.setVigencia(true);
        prestamo.setEmpleado(empleadoSeleccionado);
        prestamo.setCliente(clienteSeleccionado);
        prestamo.setLibros(librosSeleccionados);
        prestamoService.guardarPrestamo(prestamo, MethodType.PUT);
        cargarDatos();
    }

    public void eliminarPrestamo(){
        Prestamo prestamo = prestamoService.buscarPrestamoPorId(Long.parseLong(tfId.getText()));
        prestamoService.eliminarPrestamo(prestamo);
        cargarDatos();
    }

    public Prestamo buscarPrestamo() {
        return prestamoService.buscarPrestamoPorId(Long.parseLong(tfBuscar.getText()));
    }

    public void devolverLibros(){
        Prestamo prestamo = prestamoService.buscarPrestamoPorId(Long.parseLong(tfId.getText()));
        prestamoService.devolverLibros(prestamo);
        cargarDatos();
    }

    public void llenarCmbs(){
        ObservableList<Empleado> empleados = FXCollections.observableList(empleadoService.listarEmpleados());
        ObservableList<Cliente> clientes = FXCollections.observableList(clienteService.listarClieantes());
        ObservableList<Libro> libros = FXCollections.observableList(libroService.listarLibros());
        cmbEmpleados.setItems(empleados);
        cmbClientes.setItems(clientes);
        cmbLibros1.setItems(libros);
        cmbLibros2.setItems(libros);
        cmbLibros3.setItems(libros);
    }

    public void agregarLibrosSeleccionados(){
        if (cmbLibros1.getValue() != null) {
            librosSeleccionados.add((Libro) cmbLibros1.getValue());
            System.out.println(librosSeleccionados);
        }
        if (cmbLibros2.getValue() != null) {
            librosSeleccionados.add((Libro) cmbLibros2.getValue());
            System.out.println(librosSeleccionados);
        }
        if (cmbLibros3.getValue() != null) {
            librosSeleccionados.add((Libro) cmbLibros3.getValue());
            System.out.println(librosSeleccionados);
        }
    }

    private String librosToString(List<Libro> libros) {
        return libros.stream()
                     .map(Libro::getNombre)  
                     .collect(Collectors.joining(", "));
    }

    public void buscarPrestamoYMostar(){
        String id = tfBuscar.getText();
        if(id != null && !id.isBlank()){
            try {
                Long IdPrestamo = Long.parseLong(id);
                Prestamo prestamo = prestamoService.buscarPrestamoPorId(IdPrestamo);
                ObservableList<Prestamo> prestamoList = FXCollections.observableArrayList(prestamo);
                tblPrestamos.setItems(prestamoList);
                colId.setCellValueFactory(new PropertyValueFactory<Prestamo,Long>("id"));
                colFechaPrestamo.setCellValueFactory(new PropertyValueFactory<Prestamo,Date>("fechaDePrestamo"));
                colFechaDevolucion.setCellValueFactory(new PropertyValueFactory<Prestamo,Date>("fechaDeDevolucion"));
                colVigencia.setCellValueFactory(new PropertyValueFactory<Prestamo,Boolean>("vigencia"));
                colEmpleado.setCellValueFactory(new PropertyValueFactory<Prestamo,Empleado>("empleado"));
                colCliente.setCellValueFactory(new PropertyValueFactory<Prestamo,Cliente>("cliente"));
                colLibros.setCellValueFactory(new PropertyValueFactory<Prestamo, List<Libro>>("libros"));
                colLibros.setCellFactory(new Callback<TableColumn<Prestamo, List<Libro>>, TableCell<Prestamo, List<Libro>>>() {
                    @Override
                    public TableCell<Prestamo, List<Libro>> call(TableColumn<Prestamo, List<Libro>> param) {
                        return new TableCell<Prestamo, List<Libro>>() {
                            @Override
                            protected void updateItem(List<Libro> libros, boolean empty) {
                                super.updateItem(libros, empty);
                                if (empty || libros == null) {
                                    setText(null);
                                } else {
                                    setText(librosToString(libros));
                                }
                            }
                        };
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
