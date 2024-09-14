package com.santiagocontreras.webapp.biblioteca.controller.FXController;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.santiagocontreras.webapp.biblioteca.model.Categoria;
import com.santiagocontreras.webapp.biblioteca.model.Libro;
import com.santiagocontreras.webapp.biblioteca.service.CategoriaService;
import com.santiagocontreras.webapp.biblioteca.service.LibroService;
import com.santiagocontreras.webapp.biblioteca.system.Main;
import com.santiagocontreras.webapp.biblioteca.util.EstadoLibro;
import com.santiagocontreras.webapp.biblioteca.util.MethodType;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.Setter;

@Component
public class LibroControllerView implements Initializable {

    @FXML
    TextField tfId, tfIsbn, tfNombre, tfAutor, tfEditorial, tfdisponibilidad, tfEstanteria, tfCluster, tfBuscar;
    @FXML
    TextArea taSipnosis;
    @FXML
    ComboBox<Categoria> cmbCategorias;
    @FXML
    Button btnGuardar, btnLimpiar, btnEliminar, btnBuscar, btnRegresar;
    @FXML
    TableView tablaLibros;
    @FXML
    TableColumn colId, colIsbn, colNombre,colSipnosis, colAutor, colEditorial,colDisponibilidad,colEstanteria,colCluster,colCategoria;

    @Setter
    private Main stage;

    @Autowired
    LibroService libroService;

    @Autowired
    CategoriaService categoriaService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
        ObservableList<Categoria> categorias = FXCollections.observableArrayList(categoriaService.listarCategorias());
        cmbCategorias.setItems(categorias);
    }

    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnGuardar){
            if(tfId.getText().isBlank()){
                agregarLibro();
            }else{
                editarLibro();
            }
        }else if(event.getSource() == btnEliminar){
            eliminarLibro();
        }else if(event.getSource() == btnLimpiar){
            limpiarFormulario();
        }else if(event.getSource() == btnRegresar){
            stage.indexView();
        }else if(event.getSource() == btnBuscar){
            tablaLibros.getItems().clear();
            if(tfBuscar.getText().isBlank()){
                cargarDatos();
            }else{
                tablaLibros.getItems().add(buscarLibro());
                colId.setCellValueFactory(new PropertyValueFactory<Libro, Long>("id"));
                colIsbn.setCellValueFactory(new PropertyValueFactory<Libro,String>("isbn"));
                colNombre.setCellValueFactory(new PropertyValueFactory<Libro,String>("nombre"));
                colSipnosis.setCellValueFactory(new PropertyValueFactory<Libro,String>("sipnosis"));
                colAutor.setCellValueFactory(new PropertyValueFactory<Libro,String>("autor"));
                colEditorial.setCellValueFactory(new PropertyValueFactory<Libro,String>("editorial"));
                colDisponibilidad.setCellValueFactory(new PropertyValueFactory<Libro,String>("disponibilidad"));
                colEstanteria.setCellValueFactory(new PropertyValueFactory<Libro,String>("numeroEstanteria"));
                colCluster.setCellValueFactory(new PropertyValueFactory<Libro,String>("cluster"));
                colCategoria.setCellValueFactory(new PropertyValueFactory<Libro, Categoria>("categoria"));

            }
        }
    }

    public void cargarDatos(){
        tablaLibros.setItems(listarLibros());
        colId.setCellValueFactory(new PropertyValueFactory<Libro, Long>("id"));
        colIsbn.setCellValueFactory(new PropertyValueFactory<Libro,String>("isbn"));
        colNombre.setCellValueFactory(new PropertyValueFactory<Libro,String>("nombre"));
        colSipnosis.setCellValueFactory(new PropertyValueFactory<Libro,String>("sipnosis"));
        colAutor.setCellValueFactory(new PropertyValueFactory<Libro,String>("autor"));
        colEditorial.setCellValueFactory(new PropertyValueFactory<Libro,String>("editorial"));
        colDisponibilidad.setCellValueFactory(new PropertyValueFactory<Libro,String>("disponibilidad"));
        colEstanteria.setCellValueFactory(new PropertyValueFactory<Libro,String>("numeroEstanteria"));
        colCluster.setCellValueFactory(new PropertyValueFactory<Libro,String>("cluster"));
        colCategoria.setCellValueFactory(new PropertyValueFactory<Libro, Categoria>("categoria"));
    }

    public void limpiarFormulario(){
        tfId.clear();
        tfIsbn.clear();
        tfNombre.clear();
        taSipnosis.clear();
        tfAutor.clear();
        tfEditorial.clear();
        tfdisponibilidad.clear();
        tfEstanteria.clear();
        tfCluster.clear();
        cmbCategorias.getSelectionModel().clearSelection();
    }

    public void cargarFormEditar(){
        Libro libro = (Libro)tablaLibros.getSelectionModel().getSelectedItem();
        tfId.setText(Long.toString(libro.getId()));
        tfIsbn.setText(libro.getIsbn());
        tfNombre.setText(libro.getNombre());
        taSipnosis.setText(libro.getSipnosis());
        tfAutor.setText(libro.getAutor());
        tfEditorial.setText(libro.getEditorial());
        EstadoLibro estado = libro.getDisponibilidad();
        tfdisponibilidad.setText(estado.toString());
        tfEstanteria.setText(libro.getNumeroEstanteria());
        tfCluster.setText(libro.getCluster());
        cmbCategorias.getSelectionModel().select(libro.getCategoria());
    }

    public ObservableList<Libro> listarLibros(){
        return FXCollections.observableList(libroService.listarLibros());
    }

    public void agregarLibro(){
        Libro libro = new Libro();
        Categoria categoriaSeleccionada = cmbCategorias.getSelectionModel().getSelectedItem();
        libro.setIsbn(tfIsbn.getText());
        libro.setNombre(tfNombre.getText());
        libro.setSipnosis(taSipnosis.getText());
        libro.setAutor(tfAutor.getText());
        libro.setEditorial(tfEditorial.getText());
        libro.setNumeroEstanteria(tfEstanteria.getText());
        libro.setCluster(tfCluster.getText());
        libro.setCategoria(categoriaSeleccionada);
        libroService.guardarLibro(libro, MethodType.POST);
        cargarDatos();
    }

    public void editarLibro(){
        Libro libro = libroService.buscarLibroPorId(Long.parseLong(tfId.getText()));
        Categoria categoriaSeleccionada = cmbCategorias.getSelectionModel().getSelectedItem();
        libro.setIsbn(tfIsbn.getText());
        libro.setNombre(tfNombre.getText());
        libro.setSipnosis(taSipnosis.getText());
        libro.setAutor(tfAutor.getText());
        libro.setEditorial(tfEditorial.getText());
        libro.setNumeroEstanteria(tfEstanteria.getText());
        libro.setCluster(tfCluster.getText());
        libro.setCategoria(categoriaSeleccionada);
        libroService.guardarLibro(libro, MethodType.PUT);
        cargarDatos();
    }

    public void eliminarLibro(){
        Libro libro = libroService.buscarLibroPorId(Long.parseLong(tfId.getText()));
        libroService.eliminarLibro(libro);
        cargarDatos();
    }
    
    public Libro buscarLibro(){
        return libroService.buscarLibroPorId(Long.parseLong(tfBuscar.getText()));
    }
}
