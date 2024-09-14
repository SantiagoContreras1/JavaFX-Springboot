package com.santiagocontreras.webapp.biblioteca.controller.FXController;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.santiagocontreras.webapp.biblioteca.model.Categoria;
import com.santiagocontreras.webapp.biblioteca.service.CategoriaService;
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
public class CategoriaControllerView implements Initializable {

    
    @FXML
    TextField tfId, tfNombre, tfBuscar;
    @FXML
    Button btnGuardar,btnLimpiar,btnEliminar, btnRegresar, btnBuscar;
    @FXML
    TableView tableCategorias;
    @FXML
    TableColumn colIdCategoria,colNombreCategoria;

    @Setter
    private Main stage;

    @Autowired
    CategoriaService categoriaService;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
       cargarDatos();
       
    }

    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnGuardar){
            if(tfId.getText().isBlank()){
                agregarCategoria();
            }else{
                editarCategoria();
            }
        }else if(event.getSource() == btnEliminar){
            eliminarCategoria();
        }else if(event.getSource() == btnLimpiar){
            limpiarFormulario();
        }else if(event.getSource() == btnRegresar){
            stage.indexView();
        }else if(event.getSource() == btnBuscar){
            tableCategorias.getItems().clear();
            if(tfBuscar.getText().isBlank()){
                cargarDatos();
            }else{
                tableCategorias.getItems().add(buscarCategoria());
                colIdCategoria.setCellValueFactory(new PropertyValueFactory<Categoria,Long>("id"));
                colNombreCategoria.setCellValueFactory(new PropertyValueFactory<Categoria,String>("nombreCategoria"));
            }
        }
    }

    public ObservableList<Categoria> listarCategorias(){
        return FXCollections.observableList(categoriaService.listarCategorias());
    }

    public void cargarDatos(){
        tableCategorias.setItems(listarCategorias());
        colIdCategoria.setCellValueFactory(new PropertyValueFactory<Categoria,Long>("id"));
        colNombreCategoria.setCellValueFactory(new PropertyValueFactory<Categoria,String>("nombreCategoria"));
    }

    public void cargarFormEditar(){
        Categoria categoria = (Categoria)tableCategorias.getSelectionModel().getSelectedItem();
        tfId.setText(Long.toString(categoria.getId()));
        tfNombre.setText(categoria.getNombreCategoria());
    }

    public void limpiarFormulario(){
        tfId.clear();
        tfNombre.clear();
    }

    public void agregarCategoria(){
        Categoria categoria = null;
        categoria.setNombreCategoria(tfNombre.getText());
        categoriaService.guardarCategoria(categoria);
        cargarDatos();
    }

    public void editarCategoria(){
        Categoria categoria = categoriaService.buscarCategoriaPorId(Long.parseLong(tfId.getText()));
        categoria.setNombreCategoria(tfNombre.getText());
        categoriaService.guardarCategoria(categoria);
        cargarDatos();
    }

    public void eliminarCategoria(){
        Categoria categoria = categoriaService.buscarCategoriaPorId(Long.parseLong(tfId.getText()));
        categoriaService.eliminarCategoria(categoria);
        cargarDatos();
    }

    public Categoria buscarCategoria(){
        return categoriaService.buscarCategoriaPorId(Long.parseLong(tfBuscar.getText()));
    }
}
