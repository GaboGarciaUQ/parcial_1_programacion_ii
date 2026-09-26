package com.uniquindio.parcial1p2;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) {

        try {

            FXMLLoader loader = new FXMLLoader(
                    App.class.getResource("/com/uniquindio/parcial1p2/main.fxml")
            );

            Parent root = loader.load();

            Scene scene = new Scene(root, 1100, 700);

            var archivoCSS = App.class.getResource(
                    "/com/uniquindio/parcial1p2/styles.css"
            );

            if (archivoCSS != null) {
                scene.getStylesheets().add(archivoCSS.toExternalForm());
            }

            stage.setTitle("LinguaPlus - Sistema de Gestión Académica");
            stage.setScene(scene);
            stage.setWidth(1100);
            stage.setHeight(700);
            stage.setResizable(true);
            stage.show();

        } catch (IOException e) {

            System.err.println(
                    "ERROR: No se pudo cargar la vista principal main.fxml."
            );

            System.err.println(
                    "Verifique que el archivo exista en:"
                    + " src/main/resources/com/uniquindio/parcial1p2/main.fxml"
            );

            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}