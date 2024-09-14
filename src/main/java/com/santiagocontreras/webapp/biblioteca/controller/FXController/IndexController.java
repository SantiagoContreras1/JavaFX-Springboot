package com.santiagocontreras.webapp.biblioteca.controller.FXController;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.stereotype.Component;

import com.santiagocontreras.webapp.biblioteca.model.Categoria;
import com.santiagocontreras.webapp.biblioteca.service.CategoriaService;
import com.santiagocontreras.webapp.biblioteca.system.Main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import lombok.Setter;

@Component
public class IndexController implements Initializable {

    @Setter
    private Main stage;

    @FXML
    MenuItem btnCategorias,btnClientes,btnLibros,btnEmpleados,btnPrestamos;

    @Override
    public void initialize(URL url, ResourceBundle resources) {

    }

    @FXML
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnCategorias){
            stage.CategoriaView();
        }else if(event.getSource() == btnClientes){
            stage.ClienteView();
        }else if(event.getSource() == btnEmpleados){
            stage.EmpleadoView();
        }else if(event.getSource() == btnLibros){
            stage.LibroView();
        }else if(event.getSource() == btnPrestamos){
            stage.PrestamoView();
        }
    }

    
}
