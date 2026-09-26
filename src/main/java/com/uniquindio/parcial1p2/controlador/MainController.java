package com.uniquindio.parcial1p2.controlador;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.layout.StackPane;

public class MainController {

    @FXML
    private StackPane panelCentral;

    @FXML
    private void initialize() {
        cargarVista("estudiante");
    }

    private void cargarVista(String nombreFxml) {

        try {

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "/com/uniquindio/parcial1p2/"
                                    + nombreFxml
                                    + ".fxml"
                    )
            );

            Parent vista = loader.load();

            panelCentral.getChildren().clear();
            panelCentral.getChildren().add(vista);

        } catch (IOException | NullPointerException e) {

            Alert alerta = new Alert(Alert.AlertType.ERROR);

            alerta.setTitle("Error al cargar la vista");
            alerta.setHeaderText("No se pudo cargar el módulo");
            alerta.setContentText(
                    "No fue posible cargar la vista: "
                            + nombreFxml
                            + ".fxml"
            );

            alerta.showAndWait();

            System.err.println(
                    "ERROR: No se pudo cargar la vista "
                            + nombreFxml
                            + ".fxml"
            );

            e.printStackTrace();
        }
    }

    @FXML
    public void mostrarEstudiantes() {
        cargarVista("estudiante");
    }

    @FXML
    public void mostrarDocentes() {
        cargarVista("docente");
    }

    @FXML
    public void mostrarProgramas() {
        cargarVista("programa");
    }

    @FXML
    public void mostrarMatriculas() {
        cargarVista("matricula");
    }

    @FXML
    public void mostrarServiciosAdicionales() {
        cargarVista("servicioAdicional");
    }

    @FXML
    public void mostrarPeriodosAcademicos() {
        cargarVista("periodoAcademico");
    }

    @FXML
    public void mostrarConsultas() {
        cargarVista("consultas");
    }
}