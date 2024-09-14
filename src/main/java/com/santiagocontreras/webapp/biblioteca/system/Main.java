package com.santiagocontreras.webapp.biblioteca.system;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import com.santiagocontreras.webapp.biblioteca.BibliotecaApplication;
import com.santiagocontreras.webapp.biblioteca.controller.FXController.CategoriaControllerView;
import com.santiagocontreras.webapp.biblioteca.controller.FXController.ClienteControllerView;
import com.santiagocontreras.webapp.biblioteca.controller.FXController.EmpleadoControllerView;
import com.santiagocontreras.webapp.biblioteca.controller.FXController.IndexController;
import com.santiagocontreras.webapp.biblioteca.controller.FXController.LibroControllerView;
import com.santiagocontreras.webapp.biblioteca.controller.FXController.PrestamoControllerView;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {

    private ConfigurableApplicationContext applicationContext;
    private Stage stage;
    private Scene scene;

    @Override
    public void init(){
        this.applicationContext = new SpringApplicationBuilder(BibliotecaApplication.class).run();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
       this.stage = primaryStage;
       stage.setTitle("Biblioteca Kinal Spring");
       indexView();
       stage.show();
    }

    public Initializable switchScene(String fxmlName, int width, int height) throws IOException{
        Initializable resultado = null;
        FXMLLoader loader = new FXMLLoader();

        loader.setControllerFactory(applicationContext:: getBean);
        InputStream archivo  =  Main.class.getResourceAsStream("/templates/" + fxmlName);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(Main.class.getResource("/templates/" + fxmlName));

        scene = new Scene((AnchorPane) loader.load(archivo),width,height);
        stage.setScene(scene);
        stage.sizeToScene();

        resultado = (Initializable)loader.getController();
        return resultado;
    }

    public void indexView(){
        try {
            IndexController indexView = (IndexController)switchScene("index.fxml", 1024, 650);
            indexView.setStage(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void CategoriaView(){
        try {
            CategoriaControllerView categoriaView = (CategoriaControllerView)switchScene("CategoriaView.fxml", 1024, 650);
            categoriaView.setStage(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ClienteView(){
        try {
            ClienteControllerView clienteView = (ClienteControllerView)switchScene("ClienteView.fxml",1024,650); 
            clienteView.setStage(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void EmpleadoView(){
        try {
            EmpleadoControllerView empleadoView = (EmpleadoControllerView)switchScene("EmpleadoView.fxml", 1024, 650);
            empleadoView.setStage(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void LibroView(){
        try {
            LibroControllerView libroView = (LibroControllerView)switchScene("LibroView.fxml", 1024, 650);
            libroView.setStage(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void PrestamoView(){
        try {
            PrestamoControllerView prestamoView = (PrestamoControllerView)switchScene("PrestamoView.fxml",1024,650);
            prestamoView.setStage(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
