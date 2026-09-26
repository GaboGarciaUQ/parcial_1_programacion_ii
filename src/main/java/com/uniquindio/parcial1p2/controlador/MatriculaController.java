package com.uniquindio.parcial1p2.controlador;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.uniquindio.parcial1p2.modelo.Academia;
import com.uniquindio.parcial1p2.modelo.Docente;
import com.uniquindio.parcial1p2.modelo.Estudiante;
import com.uniquindio.parcial1p2.modelo.Matricula;
import com.uniquindio.parcial1p2.modelo.ServicioAdicional;
import com.uniquindio.parcial1p2.modelo.builder.MatriculaBuilder;
import com.uniquindio.parcial1p2.modelo.comprobante.IExportable;
import com.uniquindio.parcial1p2.modelo.fabricas.FabricaComprobanteExcel;
import com.uniquindio.parcial1p2.modelo.fabricas.FabricaComprobantePDF;
import com.uniquindio.parcial1p2.modelo.fabricas.IFabricaComprobante;
import com.uniquindio.parcial1p2.modelo.programa.ProgramaFormacion;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class MatriculaController {


@FXML
private TableView<Matricula> tablaMatriculas;

@FXML
private TableColumn<Matricula, Number> columnaNumeroMatricula;

@FXML
private TableColumn<Matricula, String> columnaEstudiante;

@FXML
private TableColumn<Matricula, String> columnaPrograma;

@FXML
private TableColumn<Matricula, LocalDate> columnaFechaInicio;

@FXML
private TableColumn<Matricula, String> columnaDocenteTutor;

@FXML
private TableColumn<Matricula, Number> columnaDescuento;

@FXML
private TableColumn<Matricula, Number> columnaValorFinal;

@FXML
private ComboBox<Estudiante> cmbEstudiante;

@FXML
private ComboBox<ProgramaFormacion> cmbPrograma;

@FXML
private DatePicker dpFechaInicio;

@FXML
private ComboBox<Docente> cmbDocenteTutor;

@FXML
private ListView<ServicioAdicional> listServiciosAdicionales;

@FXML
private TextField txtDescuento;

@FXML
private TextArea txtObservaciones;

@FXML
private Label lblNumeroMatricula;

@FXML
private ComboBox<String> cmbFormatoComprobante;

private final ObservableList<Matricula> matriculas =
        FXCollections.observableArrayList();

private final ObservableList<Estudiante> estudiantes =
        FXCollections.observableArrayList();

private final ObservableList<ProgramaFormacion> programas =
        FXCollections.observableArrayList();

private final ObservableList<Docente> docentes =
        FXCollections.observableArrayList();

private final ObservableList<ServicioAdicional> serviciosAdicionales =
        FXCollections.observableArrayList();

private int numeroMatriculaMostrado = 1;

@FXML
private void initialize() {

    configurarColumnas();
    
    tablaMatriculas.setItems(matriculas);
    tablaMatriculas.setColumnResizePolicy(
        TableView.UNCONSTRAINED_RESIZE_POLICY
        );
    configurarSeleccionTabla();

    configurarListaServicios();

    configurarFormatoComprobante();

    cargarDatosAcademia();

    cargarMatriculasAcademia();

    actualizarNumeroMatricula();
}

private void configurarColumnas() {

    columnaNumeroMatricula.setCellValueFactory(
            datos -> new SimpleIntegerProperty(
                    datos.getValue().getNumeroMatricula()
            )
    );

    columnaEstudiante.setCellValueFactory(
            datos -> new SimpleStringProperty(
                    obtenerNombreEstudiante(
                            datos.getValue().getEstudiante()
                    )
            )
    );

    columnaPrograma.setCellValueFactory(
            datos -> new SimpleStringProperty(
                    obtenerNombrePrograma(
                            datos.getValue().getPrograma()
                    )
            )
    );

    columnaFechaInicio.setCellValueFactory(
            datos -> new SimpleObjectProperty<>(
                    datos.getValue().getFechaInicio()
            )
    );

    columnaDocenteTutor.setCellValueFactory(
            datos -> new SimpleStringProperty(
                    obtenerNombreDocente(
                            datos.getValue().getDocenteTutor()
                    )
            )
    );

    columnaDescuento.setCellValueFactory(
            datos -> new SimpleDoubleProperty(
                    datos.getValue().getDescuento()
            )
    );

    columnaValorFinal.setCellValueFactory(
            datos -> new SimpleDoubleProperty(
                    datos.getValue().calcularValorFinal()
            )
    );
}

private void configurarSeleccionTabla() {

    tablaMatriculas.getSelectionModel()
            .selectedItemProperty()
            .addListener(
                    (observable, anterior, seleccionada) -> {

                        if (seleccionada != null) {
                            mostrarDatosMatricula(seleccionada);
                        }
                    }
            );
}

private void configurarListaServicios() {

    listServiciosAdicionales.getSelectionModel()
            .setSelectionMode(SelectionMode.MULTIPLE);

    listServiciosAdicionales.setItems(serviciosAdicionales);

    listServiciosAdicionales.setCellFactory(
            lista -> new ListCell<ServicioAdicional>() {

                @Override
                protected void updateItem(
                        ServicioAdicional servicio,
                        boolean vacio) {

                    super.updateItem(servicio, vacio);

                    if (vacio || servicio == null) {

                        setText(null);

                    } else {

                        setText(
                                servicio.getNombre()
                                        + " - $"
                                        + String.format(
                                                "%.2f",
                                                servicio.obtenerPrecio()
                                        )
                        );
                    }
                }
            }
    );
}

private void configurarFormatoComprobante() {

    cmbFormatoComprobante.setItems(
            FXCollections.observableArrayList(
                    "PDF",
                    "Excel"
            )
    );

    cmbFormatoComprobante
            .getSelectionModel()
            .selectFirst();
}

private void cargarDatosAcademia() {

    Academia academia = Academia.getInstancia();

    estudiantes.setAll(
            academia.obtenerEstudiantes()
    );

    programas.setAll(
            academia.obtenerProgramas()
    );

    docentes.setAll(
            academia.obtenerDocentes()
    );

    serviciosAdicionales.setAll(
            academia.obtenerServiciosAdicionales()
    );

    cmbEstudiante.setItems(estudiantes);

    cmbPrograma.setItems(programas);

    ObservableList<Docente> docentesCombo =
            FXCollections.observableArrayList();

    docentesCombo.add(null);
    docentesCombo.addAll(docentes);

    cmbDocenteTutor.setItems(docentesCombo);

    configurarPresentacionCombos();
}

private void cargarMatriculasAcademia() {

    Academia academia = Academia.getInstancia();

    matriculas.setAll(
            academia.obtenerMatriculas()
    );

    if (!matriculas.isEmpty()) {

        int ultimoNumero =
                matriculas.stream()
                        .mapToInt(Matricula::getNumeroMatricula)
                        .max()
                        .orElse(0);

        numeroMatriculaMostrado =
                ultimoNumero + 1;
    }
}

private void configurarPresentacionCombos() {

    cmbEstudiante.setCellFactory(
            lista -> new ListCell<Estudiante>() {

                @Override
                protected void updateItem(
                        Estudiante estudiante,
                        boolean vacio) {

                    super.updateItem(estudiante, vacio);

                    if (vacio || estudiante == null) {
                        setText(null);
                    } else {
                        setText(
                                estudiante.getNombreCompleto()
                                        + " - "
                                        + estudiante.getDocumentoIdentidad()
                        );
                    }
                }
            }
    );

    cmbEstudiante.setButtonCell(
            new ListCell<Estudiante>() {

                @Override
                protected void updateItem(
                        Estudiante estudiante,
                        boolean vacio) {

                    super.updateItem(estudiante, vacio);

                    if (vacio || estudiante == null) {
                        setText(null);
                    } else {
                        setText(
                                estudiante.getNombreCompleto()
                        );
                    }
                }
            }
    );

    cmbPrograma.setCellFactory(
            lista -> new ListCell<ProgramaFormacion>() {

                @Override
                protected void updateItem(
                        ProgramaFormacion programa,
                        boolean vacio) {

                    super.updateItem(programa, vacio);

                    if (vacio || programa == null) {
                        setText(null);
                    } else {
                        setText(
                                programa.getNombre()
                                        + " - "
                                        + programa.getCodigo()
                        );
                    }
                }
            }
    );

    cmbPrograma.setButtonCell(
            new ListCell<ProgramaFormacion>() {

                @Override
                protected void updateItem(
                        ProgramaFormacion programa,
                        boolean vacio) {

                    super.updateItem(programa, vacio);

                    if (vacio || programa == null) {
                        setText(null);
                    } else {
                        setText(
                                programa.getNombre()
                        );
                    }
                }
            }
    );

    cmbDocenteTutor.setCellFactory(
            lista -> new ListCell<Docente>() {

                @Override
                protected void updateItem(
                        Docente docente,
                        boolean vacio) {

                    super.updateItem(docente, vacio);

                    if (vacio) {

                        setText(null);

                    } else if (docente == null) {

                        setText("Ninguno");

                    } else {

                        setText(
                                docente.getNombreCompleto()
                                        + " - "
                                        + docente.getEspecialidadIdioma()
                        );
                    }
                }
            }
    );

    cmbDocenteTutor.setButtonCell(
            new ListCell<Docente>() {

                @Override
                protected void updateItem(
                        Docente docente,
                        boolean vacio) {

                    super.updateItem(docente, vacio);

                    if (vacio) {

                        setText(null);

                    } else if (docente == null) {

                        setText("Ninguno");

                    } else {

                        setText(
                                docente.getNombreCompleto()
                        );
                    }
                }
            }
    );
}

@FXML
private void registrarMatricula() {

    Estudiante estudiante =
            cmbEstudiante.getValue();

    ProgramaFormacion programa =
            cmbPrograma.getValue();

    LocalDate fechaInicio =
            dpFechaInicio.getValue();

    if (estudiante == null) {

        mostrarAdvertencia(
                "Debe seleccionar un estudiante."
        );

        return;
    }

    if (programa == null) {

        mostrarAdvertencia(
                "Debe seleccionar un programa."
        );

        return;
    }

    if (fechaInicio == null) {

        mostrarAdvertencia(
                "Debe seleccionar la fecha de inicio."
        );

        return;
    }

    double descuento;

    try {

        String textoDescuento =
                txtDescuento.getText().trim();

        if (textoDescuento.isEmpty()) {

            descuento = 0.0;

        } else {

            descuento =
                    Double.parseDouble(
                            textoDescuento.replace(",", ".")
                    );
        }

    } catch (NumberFormatException e) {

        mostrarAdvertencia(
                "El descuento debe ser un número válido."
        );

        return;
    }

    if (descuento < 0 || descuento > 30) {

        mostrarAdvertencia(
                "El descuento debe estar entre 0% y 30%."
        );

        return;
    }

    MatriculaBuilder builder;

    try {

        builder = new MatriculaBuilder(
                estudiante,
                programa,
                fechaInicio
        );

        Docente docenteTutor =
                cmbDocenteTutor.getValue();

        if (docenteTutor != null) {

            builder.asignarTutor(
                    docenteTutor
            );
        }

        List<ServicioAdicional> serviciosSeleccionados =
                new ArrayList<>(
                        listServiciosAdicionales
                                .getSelectionModel()
                                .getSelectedItems()
                );

        for (ServicioAdicional servicio :
                serviciosSeleccionados) {

            builder.agregarServicio(
                    servicio
            );
        }

        if (descuento > 0) {

            builder.establecerDescuento(
                    descuento
            );
        }

        String observaciones =
                txtObservaciones.getText().trim();

        builder.establecerObservaciones(
                observaciones
        );

        Matricula matricula =
                builder.construir();

        Academia.getInstancia()
                .registrarMatricula(
                        matricula
                );

        matriculas.add(
                matricula
        );

        numeroMatriculaMostrado =
                matricula.getNumeroMatricula() + 1;

        actualizarNumeroMatricula();

        mostrarInformacion(
                "Matrícula registrada",
                "La matrícula se registró correctamente.\n\n"
                        + "Número de matrícula: "
                        + matricula.getNumeroMatricula()
        );

        limpiarCamposSinMensaje();

    } catch (IllegalArgumentException e) {

        mostrarAdvertencia(
                e.getMessage()
        );

    } catch (Exception e) {

        mostrarError(
                "No se pudo registrar la matrícula.",
                e.getMessage()
        );
    }
}

@FXML
private void eliminarMatricula() {

    Matricula seleccionada =
            tablaMatriculas
                    .getSelectionModel()
                    .getSelectedItem();

    if (seleccionada == null) {

        mostrarAdvertencia(
                "Seleccione una matrícula para eliminar."
        );

        return;
    }

    Alert confirmacion =
            new Alert(
                    Alert.AlertType.CONFIRMATION
            );

    confirmacion.setTitle(
            "Eliminar matrícula"
    );

    confirmacion.setHeaderText(
            "¿Desea eliminar la matrícula seleccionada?"
    );

    confirmacion.setContentText(
            "Matrícula número: "
                    + seleccionada.getNumeroMatricula()
    );

    var respuesta =
            confirmacion.showAndWait();

    if (respuesta.isPresent()
            && respuesta.get()
            == javafx.scene.control.ButtonType.OK) {

        matriculas.remove(
                seleccionada
        );

        mostrarInformacion(
                "Matrícula eliminada",
                "La matrícula fue eliminada de la vista."
        );

        limpiarCamposSinMensaje();
    }
}

@FXML
private void limpiarCampos() {

    limpiarCamposSinMensaje();

    mostrarInformacion(
            "Campos limpiados",
            "Los campos del formulario fueron limpiados."
    );
}

private void limpiarCamposSinMensaje() {

    cmbEstudiante.getSelectionModel()
            .clearSelection();

    cmbPrograma.getSelectionModel()
            .clearSelection();

    cmbDocenteTutor.getSelectionModel()
            .selectFirst();

    dpFechaInicio.setValue(null);

    listServiciosAdicionales
            .getSelectionModel()
            .clearSelection();

    txtDescuento.clear();

    txtObservaciones.clear();

    tablaMatriculas
            .getSelectionModel()
            .clearSelection();

    actualizarNumeroMatricula();
}

@FXML
private void generarComprobante() {

    Matricula matriculaSeleccionada =
            tablaMatriculas
                    .getSelectionModel()
                    .getSelectedItem();

    if (matriculaSeleccionada == null) {

        mostrarAdvertencia(
                "Seleccione una matrícula para generar el comprobante."
        );

        return;
    }

    String formato =
            cmbFormatoComprobante.getValue();

    if (formato == null || formato.isBlank()) {

        mostrarAdvertencia(
                "Seleccione el formato del comprobante."
        );

        return;
    }

    IFabricaComprobante fabrica;

    if (formato.equals("PDF")) {

        fabrica =
                new FabricaComprobantePDF();

    } else {

        fabrica =
                new FabricaComprobanteExcel();
    }

    try {

        IExportable comprobante =
                matriculaSeleccionada
                        .generarComprobante(
                                fabrica
                        );

        mostrarInformacion(
                "Comprobante generado",
                "Formato: "
                        + formato
                        + "\n\n"
                        + "Matrícula: "
                        + matriculaSeleccionada
                                .getNumeroMatricula()
                        + "\n\n"
                        + "Resultado: "
                        + comprobante
        );

    } catch (Exception e) {

        mostrarError(
                "No se pudo generar el comprobante.",
                e.getMessage()
        );
    }
}

private void mostrarDatosMatricula(
        Matricula matricula) {

    cmbEstudiante.setValue(
            matricula.getEstudiante()
    );

    cmbPrograma.setValue(
            matricula.getPrograma()
    );

    dpFechaInicio.setValue(
            matricula.getFechaInicio()
    );

    cmbDocenteTutor.setValue(
            matricula.getDocenteTutor()
    );

    txtDescuento.setText(
            String.valueOf(
                    matricula.getDescuento()
            )
    );

    txtObservaciones.setText(
            matricula.getObservaciones()
    );

    listServiciosAdicionales
            .getSelectionModel()
            .clearSelection();

    if (matricula.getServiciosAdicionales() != null) {

        for (ServicioAdicional servicio :
                matricula.getServiciosAdicionales()) {

            int indice =
                    serviciosAdicionales
                            .indexOf(servicio);

            if (indice >= 0) {

                listServiciosAdicionales
                        .getSelectionModel()
                        .select(indice);
            }
        }
    }

    lblNumeroMatricula.setText(
            String.valueOf(
                    matricula.getNumeroMatricula()
            )
    );
}

private void actualizarNumeroMatricula() {

    lblNumeroMatricula.setText(
            String.valueOf(
                    numeroMatriculaMostrado
            )
    );
}

private String obtenerNombreEstudiante(
        Estudiante estudiante) {

    if (estudiante == null) {

        return "Sin estudiante";
    }

    return estudiante.getNombreCompleto();
}

private String obtenerNombrePrograma(
        ProgramaFormacion programa) {

    if (programa == null) {

        return "Sin programa";
    }

    return programa.getNombre();
}

private String obtenerNombreDocente(
        Docente docente) {

    if (docente == null) {

        return "Sin asignar";
    }

    return docente.getNombreCompleto();
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