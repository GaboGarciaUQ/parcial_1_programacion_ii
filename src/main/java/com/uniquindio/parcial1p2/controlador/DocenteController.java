package com.uniquindio.parcial1p2.controlador;

import com.uniquindio.parcial1p2.modelo.Academia;
import com.uniquindio.parcial1p2.modelo.Docente;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class DocenteController {

    @FXML
    private TableView<Docente> tablaDocentes;

    @FXML
    private TableColumn<Docente, String> columnaDocumentoIdentidad;

    @FXML
    private TableColumn<Docente, String> columnaNombreCompleto;

    @FXML
    private TableColumn<Docente, String> columnaEspecialidadIdioma;

    @FXML
    private TableColumn<Docente, String> columnaTelefono;

    @FXML
    private TableColumn<Docente, Double> columnaTarifaSesion;

    @FXML
    private TextField txtIdentificacion;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtIdiomaEspecialidad;

    @FXML
    private TextField txtTelefono;

    @FXML
    private TextField txtTarifaPorSesion;

    private final ObservableList<Docente> docentes =
            FXCollections.observableArrayList();

    private final Academia academia =
            Academia.getInstancia();

    @FXML
    private void initialize() {

        columnaDocumentoIdentidad.setCellValueFactory(
                new PropertyValueFactory<>("documentoIdentidad")
        );

        columnaNombreCompleto.setCellValueFactory(
                new PropertyValueFactory<>("nombreCompleto")
        );

        columnaEspecialidadIdioma.setCellValueFactory(
                new PropertyValueFactory<>("especialidadIdioma")
        );

        columnaTelefono.setCellValueFactory(
                new PropertyValueFactory<>("telefono")
        );

        columnaTarifaSesion.setCellValueFactory(
                new PropertyValueFactory<>("tarifaSesion")
        );

        cargarDocentesDesdeAcademia();

        tablaDocentes.setItems(docentes);

        tablaDocentes.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, docenteAnterior, docenteSeleccionado) -> {

                    if (docenteSeleccionado != null) {
                        cargarDatosDocente(docenteSeleccionado);
                    }
                });
    }

    private void cargarDocentesDesdeAcademia() {

        docentes.setAll(
                academia.obtenerDocentes()
        );
    }

    @FXML
    public void agregarDocente() {

        if (!validarCampos()) {
            return;
        }

        double tarifaSesion = Double.parseDouble(
                txtTarifaPorSesion.getText().trim()
        );

        Docente docente = new Docente(
                txtIdentificacion.getText().trim(),
                txtNombre.getText().trim(),
                txtIdiomaEspecialidad.getText().trim(),
                txtTelefono.getText().trim(),
                tarifaSesion
        );

        academia.registrarDocente(docente);

        docentes.setAll(
                academia.obtenerDocentes()
        );

        mostrarInformacion(
                "Docente agregado",
                "El docente fue agregado correctamente."
        );

        limpiarCampos();
    }

    @FXML
    public void editarDocente() {

        Docente docenteSeleccionado =
                tablaDocentes.getSelectionModel().getSelectedItem();

        if (docenteSeleccionado == null) {
            mostrarAdvertencia(
                    "Debe seleccionar un docente de la tabla para editarlo."
            );
            return;
        }

        if (!validarCampos()) {
            return;
        }

        double tarifaSesion = Double.parseDouble(
                txtTarifaPorSesion.getText().trim()
        );

        docenteSeleccionado.setDocumentoIdentidad(
                txtIdentificacion.getText().trim()
        );

        docenteSeleccionado.setNombreCompleto(
                txtNombre.getText().trim()
        );

        docenteSeleccionado.setEspecialidadIdioma(
                txtIdiomaEspecialidad.getText().trim()
        );

        docenteSeleccionado.setTelefono(
                txtTelefono.getText().trim()
        );

        docenteSeleccionado.setTarifaSesion(
                tarifaSesion
        );

        tablaDocentes.refresh();

        mostrarInformacion(
                "Docente actualizado",
                "Los datos del docente fueron actualizados correctamente."
        );

        limpiarCampos();
    }

    @FXML
    public void eliminarDocente() {

        Docente docenteSeleccionado =
                tablaDocentes.getSelectionModel().getSelectedItem();

        if (docenteSeleccionado == null) {
            mostrarAdvertencia(
                    "Debe seleccionar una fila de la tabla primero."
            );
            return;
        }

        academia.eliminarDocente(
                docenteSeleccionado
        );

        docentes.setAll(
                academia.obtenerDocentes()
        );

        mostrarInformacion(
                "Docente eliminado",
                "El docente fue eliminado correctamente."
        );

        limpiarCampos();
    }

    @FXML
    public void limpiarCampos() {

        txtIdentificacion.clear();
        txtNombre.clear();
        txtIdiomaEspecialidad.clear();
        txtTelefono.clear();
        txtTarifaPorSesion.clear();

        tablaDocentes.getSelectionModel().clearSelection();
    }

    private boolean validarCampos() {

        if (txtIdentificacion.getText().trim().isEmpty()) {
            mostrarAdvertencia(
                    "El campo identificación no puede estar vacío."
            );
            return false;
        }

        if (txtNombre.getText().trim().isEmpty()) {
            mostrarAdvertencia(
                    "El campo nombre no puede estar vacío."
            );
            return false;
        }

        if (txtIdiomaEspecialidad.getText().trim().isEmpty()) {
            mostrarAdvertencia(
                    "El campo idioma/especialidad no puede estar vacío."
            );
            return false;
        }

        if (txtTelefono.getText().trim().isEmpty()) {
            mostrarAdvertencia(
                    "El campo teléfono no puede estar vacío."
            );
            return false;
        }

        if (txtTarifaPorSesion.getText().trim().isEmpty()) {
            mostrarAdvertencia(
                    "El campo tarifa por sesión no puede estar vacío."
            );
            return false;
        }

        double tarifaSesion;

        try {
            tarifaSesion = Double.parseDouble(
                    txtTarifaPorSesion.getText().trim()
            );
        } catch (NumberFormatException e) {
            mostrarAdvertencia(
                    "La tarifa por sesión debe ser un valor numérico."
            );
            return false;
        }

        if (tarifaSesion <= 0) {
            mostrarAdvertencia(
                    "La tarifa por sesión debe ser mayor a cero."
            );
            return false;
        }

        return true;
    }

    private void cargarDatosDocente(
            Docente docente) {

        txtIdentificacion.setText(
                docente.getDocumentoIdentidad()
        );

        txtNombre.setText(
                docente.getNombreCompleto()
        );

        txtIdiomaEspecialidad.setText(
                docente.getEspecialidadIdioma()
        );

        txtTelefono.setText(
                docente.getTelefono()
        );

        txtTarifaPorSesion.setText(
                String.valueOf(
                        docente.getTarifaSesion()
                )
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