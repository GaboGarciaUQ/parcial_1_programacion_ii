package com.uniquindio.parcial1p2.controlador;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;

import com.uniquindio.parcial1p2.modelo.Academia;
import com.uniquindio.parcial1p2.modelo.OfertaAcademica;
import com.uniquindio.parcial1p2.modelo.PeriodoAcademico;
import com.uniquindio.parcial1p2.modelo.programa.ProgramaFormacion;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class PeriodoAcademicoController {

    @FXML
    private TableView<PeriodoAcademico> tablaPeriodos;

    @FXML
    private TableColumn<PeriodoAcademico, LocalDate> columnaFechaInicio;

    @FXML
    private TableColumn<PeriodoAcademico, LocalDate> columnaFechaFin;

    @FXML
    private TableColumn<PeriodoAcademico, Number> columnaCantidadProgramasOferta;

    @FXML
    private TableColumn<PeriodoAcademico, Number> columnaCantidadMatriculas;

    @FXML
    private TableColumn<PeriodoAcademico, String> columnaEstado;

    @FXML
    private DatePicker dpFechaInicio;

    @FXML
    private DatePicker dpFechaFin;

    @FXML
    private TableView<ProgramaFormacion> tablaOfertaDetalle;

    @FXML
    private TableColumn<ProgramaFormacion, String> columnaProgramaDetalle;

    private final ObservableList<PeriodoAcademico> periodos =
            FXCollections.observableArrayList();

    private final ObservableList<ProgramaFormacion> programasOferta =
            FXCollections.observableArrayList();

    @FXML
    private void initialize() {

        configurarColumnas();

        configurarTablaOfertaDetalle();

        tablaPeriodos.setItems(periodos);

        tablaOfertaDetalle.setItems(
                programasOferta
        );

        cargarPeriodos();

        configurarSeleccionTabla();

        // TODO: La oferta base de programas para un periodo nuevo
        // debería tomarse de la lista centralizada de programas
        // en Academia (Singleton).
    }

    private void configurarColumnas() {

        columnaFechaInicio.setCellValueFactory(
                datos -> new SimpleObjectProperty<>(
                        datos.getValue().getFechaInicio()
                )
        );

        columnaFechaFin.setCellValueFactory(
                datos -> new SimpleObjectProperty<>(
                        datos.getValue().getFechaFin()
                )
        );

        columnaCantidadProgramasOferta.setCellValueFactory(
                datos -> new SimpleIntegerProperty(
                        obtenerCantidadProgramas(
                                datos.getValue()
                        )
                )
        );

        columnaCantidadMatriculas.setCellValueFactory(
                datos -> new SimpleIntegerProperty(
                        obtenerCantidadMatriculas(
                                datos.getValue()
                        )
                )
        );

        columnaEstado.setCellValueFactory(
                datos -> new SimpleStringProperty(
                        datos.getValue().estaActivo()
                                ? "Activo"
                                : "Inactivo"
                )
        );

        columnaEstado.setCellFactory(
                columna -> new TableCell<PeriodoAcademico, String>() {

                    @Override
                    protected void updateItem(
                            String estado,
                            boolean vacio) {

                        super.updateItem(
                                estado,
                                vacio
                        );

                        if (vacio || estado == null) {

                            setText(null);

                        } else {

                            setText(estado);
                        }
                    }
                }
        );
    }

    private void configurarTablaOfertaDetalle() {

        columnaProgramaDetalle.setCellValueFactory(
                datos -> {

                    ProgramaFormacion programa =
                            datos.getValue();

                    if (programa == null) {
                        return new SimpleStringProperty(
                                "Sin programa"
                        );
                    }

                    return new SimpleStringProperty(
                            programa.getNombre()
                                    + " - "
                                    + programa.getCodigo()
                    );
                }
        );
    }

    private void configurarSeleccionTabla() {

        tablaPeriodos
                .getSelectionModel()
                .selectedItemProperty()
                .addListener(
                        (observable, anterior, seleccionado) -> {

                            actualizarOfertaDetalle(
                                    seleccionado
                            );
                        }
                );
    }

    private void cargarPeriodos() {

        Academia academia =
                Academia.getInstancia();

        periodos.setAll(
                academia.obtenerPeriodosAcademicos()
        );
    }

    @FXML
    private void crearPeriodoClonado() {

        if (periodos.isEmpty()) {

            mostrarAdvertencia(
                    "No existen periodos anteriores para clonar.\n\n"
                            + "Utilice el botón "
                            + "\"Crear periodo con oferta nueva\" "
                            + "para crear el primer periodo."
            );

            return;
        }

        if (!validarFechas()) {
            return;
        }

        PeriodoAcademico periodoAnterior =
                periodos.stream()
                        .max(
                                Comparator.comparing(
                                        PeriodoAcademico::getFechaInicio
                                )
                        )
                        .orElse(null);

        if (periodoAnterior == null) {

            mostrarAdvertencia(
                    "No fue posible encontrar un periodo anterior."
            );

            return;
        }

        OfertaAcademica ofertaAnterior =
                periodoAnterior.obtenerOfertaAcademica();

        if (ofertaAnterior == null) {

            mostrarAdvertencia(
                    "El periodo anterior no tiene una oferta académica "
                            + "para clonar."
            );

            return;
        }

        try {

            OfertaAcademica ofertaClonada =
                    ofertaAnterior.clonarOferta();

            PeriodoAcademico nuevoPeriodo =
                    new PeriodoAcademico(
                            dpFechaInicio.getValue(),
                            dpFechaFin.getValue(),
                            ofertaClonada
                    );

            periodos.add(nuevoPeriodo);

            Academia.getInstancia()
                    .registrarPeriodo(
                            nuevoPeriodo
                    );

            tablaPeriodos
                    .getSelectionModel()
                    .select(nuevoPeriodo);

            mostrarInformacion(
                    "Periodo creado",
                    "El nuevo periodo fue creado correctamente "
                            + "clonando la oferta académica anterior.\n\n"
                            + "Programas clonados: "
                            + ofertaClonada
                                    .obtenerProgramas()
                                    .size()
            );

            limpiarFechas();

        } catch (Exception e) {

            mostrarError(
                    "No se pudo crear el periodo clonado.",
                    e.getMessage()
            );
        }
    }

    @FXML
    private void crearPeriodoNuevo() {

        if (!validarFechas()) {
            return;
        }

        try {

            OfertaAcademica ofertaNueva =
                    new OfertaAcademica(
                            new ArrayList<>()
                    );

            PeriodoAcademico nuevoPeriodo =
                    new PeriodoAcademico(
                            dpFechaInicio.getValue(),
                            dpFechaFin.getValue(),
                            ofertaNueva
                    );

            periodos.add(nuevoPeriodo);

            Academia.getInstancia()
                    .registrarPeriodo(
                            nuevoPeriodo
                    );

            tablaPeriodos
                    .getSelectionModel()
                    .select(nuevoPeriodo);

            mostrarInformacion(
                    "Periodo creado",
                    "El nuevo periodo fue creado correctamente "
                            + "con una oferta académica vacía."
            );

            limpiarFechas();

        } catch (Exception e) {

            mostrarError(
                    "No se pudo crear el periodo.",
                    e.getMessage()
            );
        }
    }

    @FXML
    private void eliminarPeriodo() {

        PeriodoAcademico seleccionado =
                tablaPeriodos
                        .getSelectionModel()
                        .getSelectedItem();

        if (seleccionado == null) {

            mostrarAdvertencia(
                    "Debe seleccionar un periodo antes de eliminarlo."
            );

            return;
        }

        Alert confirmacion =
                new Alert(
                        Alert.AlertType.CONFIRMATION
                );

        confirmacion.setTitle(
                "Eliminar periodo"
        );

        confirmacion.setHeaderText(
                "¿Desea eliminar el periodo seleccionado?"
        );

        confirmacion.setContentText(
                "Periodo: "
                        + seleccionado.getFechaInicio()
                        + " hasta "
                        + seleccionado.getFechaFin()
        );

        var respuesta =
                confirmacion.showAndWait();

        if (respuesta.isPresent()
                && respuesta.get() == ButtonType.OK) {

            periodos.remove(
                    seleccionado
            );

            programasOferta.clear();

            mostrarInformacion(
                    "Periodo eliminado",
                    "El periodo fue eliminado correctamente."
            );
        }
    }

    private boolean validarFechas() {

        LocalDate fechaInicio =
                dpFechaInicio.getValue();

        LocalDate fechaFin =
                dpFechaFin.getValue();

        if (fechaInicio == null) {

            mostrarAdvertencia(
                    "Debe seleccionar la fecha de inicio."
            );

            return false;
        }

        if (fechaFin == null) {

            mostrarAdvertencia(
                    "Debe seleccionar la fecha de fin."
            );

            return false;
        }

        if (!fechaFin.isAfter(fechaInicio)) {

            mostrarAdvertencia(
                    "La fecha de fin debe ser posterior "
                            + "a la fecha de inicio."
            );

            return false;
        }

        return true;
    }

    private void actualizarOfertaDetalle(
            PeriodoAcademico periodoSeleccionado) {

        programasOferta.clear();

        if (periodoSeleccionado == null) {
            return;
        }

        OfertaAcademica oferta =
                periodoSeleccionado
                        .obtenerOfertaAcademica();

        if (oferta == null) {
            return;
        }

        if (oferta.obtenerProgramas() != null) {

            programasOferta.setAll(
                    oferta.obtenerProgramas()
            );
        }
    }

    private int obtenerCantidadProgramas(
            PeriodoAcademico periodo) {

        if (periodo == null
                || periodo.obtenerOfertaAcademica() == null
                || periodo.obtenerOfertaAcademica()
                        .obtenerProgramas() == null) {

            return 0;
        }

        return periodo.obtenerOfertaAcademica()
                .obtenerProgramas()
                .size();
    }

    private int obtenerCantidadMatriculas(
            PeriodoAcademico periodo) {

        if (periodo == null
                || periodo.obtenerMatriculas() == null) {

            return 0;
        }

        return periodo.obtenerMatriculas()
                .size();
    }

    private void limpiarFechas() {

        dpFechaInicio.setValue(null);
        dpFechaFin.setValue(null);
    }

    private void mostrarAdvertencia(
            String mensaje) {

        Alert alerta =
                new Alert(
                        Alert.AlertType.WARNING
                );

        alerta.setTitle(
                "Advertencia"
        );

        alerta.setHeaderText(
                "Datos no válidos"
        );

        alerta.setContentText(
                mensaje
        );

        alerta.showAndWait();
    }

    private void mostrarInformacion(
            String titulo,
            String mensaje) {

        Alert alerta =
                new Alert(
                        Alert.AlertType.INFORMATION
                );

        alerta.setTitle(
                titulo
        );

        alerta.setHeaderText(null);

        alerta.setContentText(
                mensaje
        );

        alerta.showAndWait();
    }

    private void mostrarError(
            String titulo,
            String mensaje) {

        Alert alerta =
                new Alert(
                        Alert.AlertType.ERROR
                );

        alerta.setTitle(
                titulo
        );

        alerta.setHeaderText(
                "Se produjo un error"
        );

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