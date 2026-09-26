package com.uniquindio.parcial1p2.controlador;

import com.uniquindio.parcial1p2.modelo.Academia;
import com.uniquindio.parcial1p2.modelo.ServicioAdicional;
import com.uniquindio.parcial1p2.modelo.enums.TipoServicio;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class ServicioAdicionalController {

    @FXML
    private TableView<ServicioAdicional> tablaServicios;

    @FXML
    private TableColumn<ServicioAdicional, String> columnaCodigo;

    @FXML
    private TableColumn<ServicioAdicional, String> columnaNombre;

    @FXML
    private TableColumn<ServicioAdicional, String> columnaDescripcion;

    @FXML
    private TableColumn<ServicioAdicional, Number> columnaPrecio;

    @FXML
    private TableColumn<ServicioAdicional, Boolean> columnaDisponibilidad;

    @FXML
    private TableColumn<ServicioAdicional, String> columnaTipoServicio;

    @FXML
    private TextField txtCodigo;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtDescripcion;

    @FXML
    private TextField txtPrecio;

    @FXML
    private ComboBox<TipoServicio> cmbTipoServicio;

    @FXML
    private CheckBox chkDisponibilidad;

    private final ObservableList<ServicioAdicional> servicios =
            FXCollections.observableArrayList();

    @FXML
    private void initialize() {

        configurarColumnas();

        tablaServicios.setItems(servicios);

        cmbTipoServicio.setItems(
                FXCollections.observableArrayList(
                        TipoServicio.values()
                )
        );

        configurarSeleccionTabla();

        cargarServiciosAcademia();
    }

    private void configurarColumnas() {

        columnaCodigo.setCellValueFactory(
                datos -> new SimpleStringProperty(
                        datos.getValue().getCodigo()
                )
        );

        columnaNombre.setCellValueFactory(
                datos -> new SimpleStringProperty(
                        datos.getValue().getNombre()
                )
        );

        columnaDescripcion.setCellValueFactory(
                datos -> new SimpleStringProperty(
                        datos.getValue().getDescripcion()
                )
        );

        columnaPrecio.setCellValueFactory(
                datos -> new SimpleDoubleProperty(
                        datos.getValue().obtenerPrecio()
                )
        );

        columnaDisponibilidad.setCellValueFactory(
                datos -> new SimpleBooleanProperty(
                        datos.getValue().estaDisponible()
                )
        );

        columnaTipoServicio.setCellValueFactory(
                datos -> new SimpleStringProperty(
                        datos.getValue().getTipo() == null
                                ? "Sin tipo"
                                : datos.getValue().getTipo().toString()
                )
        );
    }

    private void configurarSeleccionTabla() {

        tablaServicios.getSelectionModel()
                .selectedItemProperty()
                .addListener(
                        (observable, anterior, seleccionado) -> {

                            if (seleccionado != null) {
                                cargarServicioEnFormulario(
                                        seleccionado
                                );
                            }
                        }
                );
    }

    private void cargarServiciosAcademia() {

        Academia academia =
                Academia.getInstancia();

        servicios.setAll(
                academia.obtenerServiciosAdicionales()
        );
    }

    @FXML
    private void agregarServicio() {

        if (!validarCampos()) {
            return;
        }

        try {

            ServicioAdicional servicio =
                    crearServicioDesdeFormulario();

            Academia.getInstancia()
                    .registrarServicio(
                            servicio
                    );

            servicios.add(servicio);

            mostrarInformacion(
                    "Servicio agregado",
                    "El servicio adicional fue agregado correctamente."
            );

            limpiarCamposSinMensaje();

        } catch (IllegalArgumentException e) {

            mostrarAdvertencia(
                    e.getMessage()
            );

        } catch (Exception e) {

            mostrarError(
                    "No se pudo agregar el servicio.",
                    e.getMessage()
            );
        }
    }

    @FXML
    private void editarServicio() {

        ServicioAdicional seleccionado =
                tablaServicios
                        .getSelectionModel()
                        .getSelectedItem();

        if (seleccionado == null) {

            mostrarAdvertencia(
                    "Debe seleccionar un servicio para editar."
            );

            return;
        }

        if (!validarCampos()) {
            return;
        }

        try {

            seleccionado.setCodigo(
                    txtCodigo.getText().trim()
            );

            seleccionado.setNombre(
                    txtNombre.getText().trim()
            );

            seleccionado.setDescripcion(
                    txtDescripcion.getText().trim()
            );

            seleccionado.setPrecio(
                    obtenerPrecio()
            );

            seleccionado.setTipo(
                    cmbTipoServicio.getValue()
            );

            seleccionado.actualizarDisponibilidad(
                    chkDisponibilidad.isSelected()
            );

            tablaServicios.refresh();

            mostrarInformacion(
                    "Servicio actualizado",
                    "El servicio adicional fue actualizado correctamente."
            );

        } catch (IllegalArgumentException e) {

            mostrarAdvertencia(
                    e.getMessage()
            );

        } catch (Exception e) {

            mostrarError(
                    "No se pudo editar el servicio.",
                    e.getMessage()
            );
        }
    }

    @FXML
    private void eliminarServicio() {

        ServicioAdicional seleccionado =
                tablaServicios
                        .getSelectionModel()
                        .getSelectedItem();

        if (seleccionado == null) {

            mostrarAdvertencia(
                    "Debe seleccionar una fila antes de eliminar."
            );

            return;
        }

        Academia.getInstancia()
        .eliminarServicio(seleccionado);

        servicios.remove(seleccionado);

        servicios.remove(
                seleccionado
        );

        mostrarInformacion(
                "Servicio eliminado",
                "El servicio adicional fue eliminado correctamente."
        );

        limpiarCamposSinMensaje();
    }

    @FXML
    private void limpiarCampos() {

        limpiarCamposSinMensaje();

        mostrarInformacion(
                "Campos limpiados",
                "Los campos del formulario fueron limpiados."
        );
    }

    private boolean validarCampos() {

        String codigo =
                txtCodigo.getText().trim();

        String nombre =
                txtNombre.getText().trim();

        String descripcion =
                txtDescripcion.getText().trim();

        if (codigo.isEmpty()) {

            mostrarAdvertencia(
                    "El código del servicio es obligatorio."
            );

            return false;
        }

        if (nombre.isEmpty()) {

            mostrarAdvertencia(
                    "El nombre del servicio es obligatorio."
            );

            return false;
        }

        if (descripcion.isEmpty()) {

            mostrarAdvertencia(
                    "La descripción del servicio es obligatoria."
            );

            return false;
        }

        if (txtPrecio.getText().trim().isEmpty()) {

            mostrarAdvertencia(
                    "El precio del servicio es obligatorio."
            );

            return false;
        }

        double precio;

        try {

            precio = Double.parseDouble(
                    txtPrecio.getText()
                            .trim()
                            .replace(",", ".")
            );

        } catch (NumberFormatException e) {

            mostrarAdvertencia(
                    "El precio debe ser un valor numérico válido."
            );

            return false;
        }

        if (precio <= 0) {

            mostrarAdvertencia(
                    "El precio debe ser mayor a cero."
            );

            return false;
        }

        if (cmbTipoServicio.getValue() == null) {

            mostrarAdvertencia(
                    "Debe seleccionar un tipo de servicio."
            );

            return false;
        }

        return true;
    }

    private double obtenerPrecio() {

        return Double.parseDouble(
                txtPrecio.getText()
                        .trim()
                        .replace(",", ".")
        );
    }

    private ServicioAdicional crearServicioDesdeFormulario() {

        return new ServicioAdicional(
                txtCodigo.getText().trim(),
                txtNombre.getText().trim(),
                txtDescripcion.getText().trim(),
                obtenerPrecio(),
                chkDisponibilidad.isSelected(),
                cmbTipoServicio.getValue()
        );
    }

    private void cargarServicioEnFormulario(
            ServicioAdicional servicio) {

        txtCodigo.setText(
                servicio.getCodigo()
        );

        txtNombre.setText(
                servicio.getNombre()
        );

        txtDescripcion.setText(
                servicio.getDescripcion()
        );

        txtPrecio.setText(
                String.valueOf(
                        servicio.obtenerPrecio()
                )
        );

        cmbTipoServicio.setValue(
                servicio.getTipo()
        );

        chkDisponibilidad.setSelected(
                servicio.estaDisponible()
        );
    }

    private void limpiarCamposSinMensaje() {

        txtCodigo.clear();

        txtNombre.clear();

        txtDescripcion.clear();

        txtPrecio.clear();

        cmbTipoServicio.getSelectionModel()
                .clearSelection();

        chkDisponibilidad.setSelected(false);

        tablaServicios.getSelectionModel()
                .clearSelection();
    }

    private void mostrarAdvertencia(String mensaje) {

        Alert alerta =
                new Alert(Alert.AlertType.WARNING);

        alerta.setTitle("Advertencia");
        alerta.setHeaderText("Datos no válidos");
        alerta.setContentText(mensaje);

        alerta.showAndWait();
    }

    private void mostrarInformacion(
            String titulo,
            String mensaje) {

        Alert alerta =
                new Alert(Alert.AlertType.INFORMATION);

        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);

        alerta.showAndWait();
    }

    private void mostrarError(
            String titulo,
            String mensaje) {

        Alert alerta =
                new Alert(Alert.AlertType.ERROR);

        alerta.setTitle(titulo);
        alerta.setHeaderText("Se produjo un error");

        alerta.setContentText(
                mensaje == null
                        ? "No se proporcionó información adicional."
                        : mensaje
        );

        alerta.showAndWait();

        System.err.println(
                titulo + ": " + mensaje
        );
    }
}