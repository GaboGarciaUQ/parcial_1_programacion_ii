package com.uniquindio.parcial1p2.controlador;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Locale;

import com.uniquindio.parcial1p2.modelo.Academia;
import com.uniquindio.parcial1p2.modelo.Estudiante;
import com.uniquindio.parcial1p2.modelo.PeriodoAcademico;
import com.uniquindio.parcial1p2.servicio.ServicioConsultas;
import com.uniquindio.parcial1p2.servicio.ValidadorNumericos;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class ConsultasController {

    @FXML
    private TextField txtTelefonoBuscar;

    @FXML
    private VBox panelResultadoEstudiante;

    @FXML
    private Label lblNombreCompleto;

    @FXML
    private Label lblDocumentoIdentidad;

    @FXML
    private Label lblCorreoElectronico;

    @FXML
    private Label lblEdad;

    @FXML
    private Label lblEsNumeroPerfecto;

    @FXML
    private ComboBox<PeriodoAcademico> cmbPeriodo;

    @FXML
    private Label lblIngresosTotal;

    private final ServicioConsultas servicioConsultas =
            new ServicioConsultas();

    private final ValidadorNumericos validadorNumericos =
            new ValidadorNumericos();

    private final Academia academia =
            Academia.getInstancia();

    @FXML
    private void initialize() {

        cmbPeriodo.setItems(
                FXCollections.observableArrayList(
                        academia.obtenerPeriodosAcademicos()
                )
        );

        configurarPresentacionPeriodos();

        limpiarResultadoEstudiante();
    }

    private void configurarPresentacionPeriodos() {

        cmbPeriodo.setCellFactory(
                lista -> new javafx.scene.control.ListCell<PeriodoAcademico>() {

                    @Override
                    protected void updateItem(
                            PeriodoAcademico periodo,
                            boolean vacio) {

                        super.updateItem(
                                periodo,
                                vacio
                        );

                        if (vacio || periodo == null) {

                            setText(null);

                        } else {

                            setText(
                                    periodo.getFechaInicio()
                                            + " - "
                                            + periodo.getFechaFin()
                            );
                        }
                    }
                }
        );

        cmbPeriodo.setButtonCell(
                new javafx.scene.control.ListCell<PeriodoAcademico>() {

                    @Override
                    protected void updateItem(
                            PeriodoAcademico periodo,
                            boolean vacio) {

                        super.updateItem(
                                periodo,
                                vacio
                        );

                        if (vacio || periodo == null) {

                            setText(null);

                        } else {

                            setText(
                                    periodo.getFechaInicio()
                                            + " - "
                                            + periodo.getFechaFin()
                            );
                        }
                    }
                }
        );
    }

    @FXML
    private void buscarEstudiantePorTelefono() {

        String telefono =
                txtTelefonoBuscar.getText().trim();

        if (telefono.isEmpty()) {

            mostrarAdvertencia(
                    "Debe ingresar un número de teléfono."
            );

            return;
        }

        actualizarNumeroPerfecto(telefono);

        Estudiante estudiante;

        try {

            estudiante =
                    servicioConsultas.buscarEstudiantePorTelefono(
                            telefono,
                            academia.obtenerEstudiantes()
                    );

        } catch (Exception e) {

            limpiarResultadoEstudiante();

            mostrarError(
                    "No se pudo realizar la búsqueda.",
                    e.getMessage()
            );

            return;
        }

        if (estudiante == null) {

            limpiarResultadoEstudiante();

            mostrarInformacion(
                    "Estudiante no encontrado",
                    "No se encontró ningún estudiante "
                            + "con el teléfono ingresado."
            );

            return;
        }

        mostrarDatosEstudiante(estudiante);
    }

    private void actualizarNumeroPerfecto(
            String telefono) {

        try {

            int numero =
                    Integer.parseInt(telefono);

            boolean esPerfecto =
                    validadorNumericos
                            .esNumeroPerfecto(numero);

            if (esPerfecto) {

                lblEsNumeroPerfecto.setText(
                        "Sí es número perfecto"
                );

            } else {

                lblEsNumeroPerfecto.setText(
                        "No es número perfecto"
                );
            }

        } catch (NumberFormatException e) {

            lblEsNumeroPerfecto.setText(
                    "No es número perfecto"
            );
        }
    }

    private void mostrarDatosEstudiante(
            Estudiante estudiante) {

        lblNombreCompleto.setText(
                estudiante.getNombreCompleto()
        );

        lblDocumentoIdentidad.setText(
                estudiante.getDocumentoIdentidad()
        );

        lblCorreoElectronico.setText(
                estudiante.getCorreoElectronico()
        );

        lblEdad.setText(
                String.valueOf(
                        estudiante.getEdad()
                )
        );

        panelResultadoEstudiante.setVisible(true);
        panelResultadoEstudiante.setManaged(true);
    }

    @FXML
    private void calcularIngresosPeriodo() {

        PeriodoAcademico periodo =
                cmbPeriodo.getValue();

        if (periodo == null) {

            mostrarAdvertencia(
                    "Debe seleccionar un periodo académico."
            );

            return;
        }

        try {

            LocalDate fechaInicio =
                    periodo.getFechaInicio();

            LocalDate fechaFin =
                    periodo.getFechaFin();

            double ingresos =
                    servicioConsultas.calcularIngresosPorPeriodo(
                            fechaInicio,
                            fechaFin,
                            academia.obtenerMatriculas()
                    );

            NumberFormat formatoMoneda = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("es-CO"));

            formatoMoneda.setMinimumFractionDigits(2);
            formatoMoneda.setMaximumFractionDigits(2);

            lblIngresosTotal.setText(
                    formatoMoneda.format(ingresos)
            );

        } catch (Exception e) {

            mostrarError(
                    "No se pudieron calcular los ingresos.",
                    e.getMessage()
            );
        }
    }

    private void limpiarResultadoEstudiante() {

        lblNombreCompleto.setText("");
        lblDocumentoIdentidad.setText("");
        lblCorreoElectronico.setText("");
        lblEdad.setText("");

        panelResultadoEstudiante.setVisible(false);
        panelResultadoEstudiante.setManaged(false);

        lblEsNumeroPerfecto.setText("");
    }

    private void mostrarAdvertencia(
            String mensaje) {

        Alert alerta =
                new Alert(
                        Alert.AlertType.WARNING
                );

        alerta.setTitle("Advertencia");
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

    private void mostrarError(
            String titulo,
            String mensaje) {

        Alert alerta =
                new Alert(
                        Alert.AlertType.ERROR
                );

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