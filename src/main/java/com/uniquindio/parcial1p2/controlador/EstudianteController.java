package com.uniquindio.parcial1p2.controlador;

import java.time.LocalDate;

import com.uniquindio.parcial1p2.modelo.Academia;
import com.uniquindio.parcial1p2.modelo.Estudiante;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class EstudianteController {

    @FXML
    private TableView<Estudiante> tablaEstudiantes;

    @FXML
    private TableColumn<Estudiante, String> columnaNombreCompleto;

    @FXML
    private TableColumn<Estudiante, String> columnaDocumentoIdentidad;

    @FXML
    private TableColumn<Estudiante, String> columnaTelefono;

    @FXML
    private TableColumn<Estudiante, String> columnaCorreoElectronico;

    @FXML
    private TableColumn<Estudiante, Integer> columnaEdad;

    @FXML
    private TableColumn<Estudiante, LocalDate> columnaFechaRegistro;

    @FXML
    private TextField txtNombreCompleto;

    @FXML
    private TextField txtDocumento;

    @FXML
    private TextField txtTelefono;

    @FXML
    private TextField txtCorreo;

    @FXML
    private TextField txtEdad;

    @FXML
    private DatePicker dpFechaRegistro;

    private final ObservableList<Estudiante> estudiantes =
            FXCollections.observableArrayList();

    private final Academia academia =
            Academia.getInstancia();

    @FXML
    private void initialize() {

        columnaNombreCompleto.setCellValueFactory(
                new PropertyValueFactory<>("nombreCompleto")
        );

        columnaDocumentoIdentidad.setCellValueFactory(
                new PropertyValueFactory<>("documentoIdentidad")
        );

        columnaTelefono.setCellValueFactory(
                new PropertyValueFactory<>("telefono")
        );

        columnaCorreoElectronico.setCellValueFactory(
                new PropertyValueFactory<>("correoElectronico")
        );

        columnaEdad.setCellValueFactory(
                new PropertyValueFactory<>("edad")
        );

        columnaFechaRegistro.setCellValueFactory(
                new PropertyValueFactory<>("fechaRegistro")
        );

        cargarEstudiantesDesdeAcademia();

        tablaEstudiantes.setItems(estudiantes);

        tablaEstudiantes.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, estudianteAnterior, estudianteSeleccionado) -> {

                    if (estudianteSeleccionado != null) {
                        cargarDatosEstudiante(estudianteSeleccionado);
                    }
                });
    }

    private void cargarEstudiantesDesdeAcademia() {

        estudiantes.setAll(
                academia.obtenerEstudiantes()
        );
    }

    @FXML
    public void agregarEstudiante() {

        if (!validarCampos()) {
            return;
        }

        int edad = Integer.parseInt(
                txtEdad.getText().trim()
        );

        Estudiante estudiante = new Estudiante(
                txtNombreCompleto.getText().trim(),
                txtDocumento.getText().trim(),
                txtTelefono.getText().trim(),
                txtCorreo.getText().trim(),
                edad,
                dpFechaRegistro.getValue()
        );

        academia.registrarEstudiante(estudiante);

        estudiantes.setAll(
                academia.obtenerEstudiantes()
        );

        mostrarInformacion(
                "Estudiante agregado",
                "El estudiante fue agregado correctamente."
        );

        limpiarCampos();
    }

    @FXML
    public void editarEstudiante() {

        Estudiante estudianteSeleccionado =
                tablaEstudiantes.getSelectionModel().getSelectedItem();

        if (estudianteSeleccionado == null) {
            mostrarAdvertencia(
                    "Debe seleccionar un estudiante de la tabla para editarlo."
            );
            return;
        }

        if (!validarCampos()) {
            return;
        }

        int edad = Integer.parseInt(
                txtEdad.getText().trim()
        );

        estudianteSeleccionado.setNombreCompleto(
                txtNombreCompleto.getText().trim()
        );

        estudianteSeleccionado.setDocumentoIdentidad(
                txtDocumento.getText().trim()
        );

        estudianteSeleccionado.setTelefono(
                txtTelefono.getText().trim()
        );

        estudianteSeleccionado.setCorreoElectronico(
                txtCorreo.getText().trim()
        );

        estudianteSeleccionado.setEdad(edad);

        estudianteSeleccionado.setFechaRegistro(
                dpFechaRegistro.getValue()
        );

        tablaEstudiantes.refresh();

        mostrarInformacion(
                "Estudiante actualizado",
                "Los datos del estudiante fueron actualizados correctamente."
        );

        limpiarCampos();
        tablaEstudiantes.getSelectionModel().clearSelection();
    }

    @FXML
    public void eliminarEstudiante() {

        Estudiante estudianteSeleccionado =
                tablaEstudiantes.getSelectionModel().getSelectedItem();

        if (estudianteSeleccionado == null) {
            mostrarAdvertencia(
                    "Debe seleccionar una fila de la tabla primero."
            );
            return;
        }

        academia.eliminarEstudiante(
                estudianteSeleccionado
        );

        estudiantes.setAll(
                academia.obtenerEstudiantes()
        );

        mostrarInformacion(
                "Estudiante eliminado",
                "El estudiante fue eliminado correctamente."
        );

        limpiarCampos();
    }

    @FXML
    public void limpiarCampos() {

        txtNombreCompleto.clear();
        txtDocumento.clear();
        txtTelefono.clear();
        txtCorreo.clear();
        txtEdad.clear();
        dpFechaRegistro.setValue(null);

        tablaEstudiantes.getSelectionModel().clearSelection();
    }

    private boolean validarCampos() {

        if (txtNombreCompleto.getText().trim().isEmpty()) {
            mostrarAdvertencia(
                    "El campo nombre completo no puede estar vacío."
            );
            return false;
        }

        if (txtDocumento.getText().trim().isEmpty()) {
            mostrarAdvertencia(
                    "El campo documento de identidad no puede estar vacío."
            );
            return false;
        }

        if (txtTelefono.getText().trim().isEmpty()) {
            mostrarAdvertencia(
                    "El campo teléfono no puede estar vacío."
            );
            return false;
        }

        if (txtCorreo.getText().trim().isEmpty()) {
            mostrarAdvertencia(
                    "El campo correo electrónico no puede estar vacío."
            );
            return false;
        }

        if (txtEdad.getText().trim().isEmpty()) {
            mostrarAdvertencia(
                    "El campo edad no puede estar vacío."
            );
            return false;
        }

        if (dpFechaRegistro.getValue() == null) {
            mostrarAdvertencia(
                    "Debe seleccionar una fecha de registro."
            );
            return false;
        }

        int edad;

        try {
            edad = Integer.parseInt(
                    txtEdad.getText().trim()
            );
        } catch (NumberFormatException e) {
            mostrarAdvertencia(
                    "La edad debe ser un número entero positivo."
            );
            return false;
        }

        if (edad <= 0) {
            mostrarAdvertencia(
                    "La edad debe ser un número entero positivo."
            );
            return false;
        }

        String correo =
                txtCorreo.getText().trim();

        if (!correo.contains("@")
                || !correo.substring(
                        correo.indexOf("@") + 1
                ).contains(".")) {

            mostrarAdvertencia(
                    "El correo electrónico debe tener un formato válido y un dominio."
            );
            return false;
        }

        return true;
    }

    private void cargarDatosEstudiante(
            Estudiante estudiante) {

        txtNombreCompleto.setText(
                estudiante.getNombreCompleto()
        );

        txtDocumento.setText(
                estudiante.getDocumentoIdentidad()
        );

        txtTelefono.setText(
                estudiante.getTelefono()
        );

        txtCorreo.setText(
                estudiante.getCorreoElectronico()
        );

        txtEdad.setText(
                String.valueOf(
                        estudiante.getEdad()
                )
        );

        dpFechaRegistro.setValue(
                estudiante.getFechaRegistro()
        );
    }

    private void mostrarAdvertencia(
            String mensaje) {

        Alert alerta =
                new Alert(
                        Alert.AlertType.WARNING
                );

        alerta.setTitle("Validación");
        alerta.setHeaderText("Datos no válidos");
        alerta.setContentText(mensaje);

        alerta.showAndWait();
    }

    private void mostrarInformacion(
            String titulo,
            String mensaje) {

        Alert alerta =
                new Alert(
                        Alert.AlertType.INFORMATION
                );

        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);

        alerta.showAndWait();
    }
}