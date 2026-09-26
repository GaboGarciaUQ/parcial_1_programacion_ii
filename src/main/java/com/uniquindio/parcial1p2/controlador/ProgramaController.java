package com.uniquindio.parcial1p2.controlador;

import java.util.ArrayList;
import java.util.List;

import com.uniquindio.parcial1p2.modelo.Academia;
import com.uniquindio.parcial1p2.modelo.beneficios.AccesoPlataformaVirtual;
import com.uniquindio.parcial1p2.modelo.beneficios.AcompanamientoTutor;
import com.uniquindio.parcial1p2.modelo.beneficios.ClubConversacion;
import com.uniquindio.parcial1p2.modelo.beneficios.IBeneficiable;
import com.uniquindio.parcial1p2.modelo.enums.EstadoPrograma;
import com.uniquindio.parcial1p2.modelo.enums.Modalidad;
import com.uniquindio.parcial1p2.modelo.programa.ProgramaBasico;
import com.uniquindio.parcial1p2.modelo.programa.ProgramaFormacion;
import com.uniquindio.parcial1p2.modelo.programa.ProgramaIntensivo;
import com.uniquindio.parcial1p2.modelo.programa.ProgramaPersonalizado;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

public class ProgramaController {

    @FXML
    private TableView<ProgramaFormacion> tablaProgramas;

    @FXML
    private TableColumn<ProgramaFormacion, String> columnaCodigo;

    @FXML
    private TableColumn<ProgramaFormacion, String> columnaNombre;

    @FXML
    private TableColumn<ProgramaFormacion, String> columnaIdioma;

    @FXML
    private TableColumn<ProgramaFormacion, Integer> columnaDuracionMeses;

    @FXML
    private TableColumn<ProgramaFormacion, Double> columnaValorMensual;

    @FXML
    private TableColumn<ProgramaFormacion, EstadoPrograma> columnaEstado;

    @FXML
    private TableColumn<ProgramaFormacion, Modalidad> columnaModalidad;

    @FXML
    private TableColumn<ProgramaFormacion, String> columnaTipo;

    @FXML
    private TextField txtCodigo;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtIdioma;

    @FXML
    private TextField txtDescripcion;

    @FXML
    private TextField txtDuracionMeses;

    @FXML
    private TextField txtValorMensual;

    @FXML
    private ComboBox<EstadoPrograma> cmbEstado;

    @FXML
    private ComboBox<Modalidad> cmbModalidad;

    @FXML
    private ComboBox<String> cmbTipoPrograma;

    @FXML
    private VBox panelPersonalizado;

    @FXML
    private TextField txtCantidadSesiones;

    @FXML
    private TextField txtNivelIdioma;

    @FXML
    private TextArea txtObjetivos;

    @FXML
    private CheckBox chkAccesoPlataforma;

    @FXML
    private CheckBox chkClubConversacion;

    @FXML
    private CheckBox chkAcompanamientoTutor;

    private final ObservableList<ProgramaFormacion> programas =
            FXCollections.observableArrayList();

    private final Academia academia =
            Academia.getInstancia();

    @FXML
    private void initialize() {

        columnaCodigo.setCellValueFactory(
                new PropertyValueFactory<>("codigo")
        );

        columnaNombre.setCellValueFactory(
                new PropertyValueFactory<>("nombre")
        );

        columnaIdioma.setCellValueFactory(
                new PropertyValueFactory<>("idioma")
        );

        columnaDuracionMeses.setCellValueFactory(
                new PropertyValueFactory<>("duracionMeses")
        );

        columnaValorMensual.setCellValueFactory(
                new PropertyValueFactory<>("valorMensual")
        );

        columnaEstado.setCellValueFactory(
                new PropertyValueFactory<>("estado")
        );

        columnaModalidad.setCellValueFactory(
                new PropertyValueFactory<>("modalidad")
        );

        columnaTipo.setCellValueFactory(
                celda -> new javafx.beans.property.SimpleStringProperty(
                        obtenerTipoPrograma(celda.getValue())
                )
        );

        cargarProgramasDesdeAcademia();

        tablaProgramas.setItems(programas);

        cmbEstado.setItems(
                FXCollections.observableArrayList(
                        EstadoPrograma.values()
                )
        );

        cmbModalidad.setItems(
                FXCollections.observableArrayList(
                        Modalidad.values()
                )
        );

        cmbTipoPrograma.setItems(
                FXCollections.observableArrayList(
                        "Básico",
                        "Intensivo",
                        "Personalizado"
                )
        );

        panelPersonalizado.setVisible(false);
        panelPersonalizado.setManaged(false);

        tablaProgramas.getSelectionModel()
                .selectedItemProperty()
                .addListener(
                        (observable, programaAnterior, programaSeleccionado) -> {

                            if (programaSeleccionado != null) {
                                cargarDatosPrograma(programaSeleccionado);
                            }
                        }
                );
    }

    private void cargarProgramasDesdeAcademia() {

        programas.setAll(
                academia.obtenerProgramas()
        );
    }

    @FXML
    public void cambiarTipoPrograma() {

        boolean esPersonalizado =
                "Personalizado".equals(cmbTipoPrograma.getValue());

        panelPersonalizado.setVisible(esPersonalizado);
        panelPersonalizado.setManaged(esPersonalizado);
    }

    @FXML
    public void agregarPrograma() {

        if (!validarCampos()) {
            return;
        }

        try {

            ProgramaFormacion programa =
                    crearPrograma();

            academia.registrarPrograma(programa);

            programas.setAll(
                    academia.obtenerProgramas()
            );

            mostrarInformacion(
                    "Programa agregado",
                    "El programa fue agregado correctamente."
            );

            limpiarCampos();

        } catch (IllegalArgumentException e) {

            mostrarAdvertencia(
                    e.getMessage()
            );
        }
    }

    @FXML
    public void editarPrograma() {

        ProgramaFormacion programaSeleccionado =
                tablaProgramas.getSelectionModel()
                        .getSelectedItem();

        if (programaSeleccionado == null) {
            mostrarAdvertencia(
                    "Debe seleccionar un programa de la tabla para editarlo."
            );
            return;
        }

        if (!validarCampos()) {
            return;
        }

        try {

            ProgramaFormacion programaActualizado =
                    crearPrograma();

            actualizarPrograma(
                    programaSeleccionado,
                    programaActualizado
            );

            programas.setAll(
                    academia.obtenerProgramas()
            );

            mostrarInformacion(
                    "Programa actualizado",
                    "Los datos del programa fueron actualizados correctamente."
            );

            limpiarCampos();

        } catch (IllegalArgumentException e) {

            mostrarAdvertencia(
                    e.getMessage()
            );
        }
    }

    private void actualizarPrograma(
            ProgramaFormacion original,
            ProgramaFormacion actualizado) {

        original.setCodigo(
                actualizado.getCodigo()
        );

        original.setNombre(
                actualizado.getNombre()
        );

        original.setIdioma(
                actualizado.getIdioma()
        );

        original.setDescripcion(
                actualizado.getDescripcion()
        );

        original.setDuracionMeses(
                actualizado.getDuracionMeses()
        );

        original.setValorMensual(
                actualizado.getValorMensual()
        );

        original.setEstado(
                actualizado.getEstado()
        );

        original.setModalidad(
                actualizado.getModalidad()
        );

        original.setBeneficios(
                new ArrayList<>(
                        actualizado.getBeneficios()
                )
        );

        if (original instanceof ProgramaPersonalizado
                && actualizado instanceof ProgramaPersonalizado) {

            ProgramaPersonalizado originalPersonalizado =
                    (ProgramaPersonalizado) original;

            ProgramaPersonalizado actualizadoPersonalizado =
                    (ProgramaPersonalizado) actualizado;

            originalPersonalizado.setNumeroSesionesTutor(
                    actualizadoPersonalizado
                            .getNumeroSesionesTutor()
            );

            originalPersonalizado.setNivelIdiomaRequerido(
                    actualizadoPersonalizado
                            .getNivelIdiomaRequerido()
            );

            originalPersonalizado.setObjetivosEstudiante(
                    actualizadoPersonalizado
                            .getObjetivosEstudiante()
            );
        }
    }

    @FXML
    public void eliminarPrograma() {

        ProgramaFormacion programaSeleccionado =
                tablaProgramas.getSelectionModel()
                        .getSelectedItem();

        if (programaSeleccionado == null) {
            mostrarAdvertencia(
                    "Debe seleccionar una fila de la tabla primero."
            );
            return;
        }

        academia.eliminarPrograma(
                programaSeleccionado
        );

        programas.setAll(
                academia.obtenerProgramas()
        );

        mostrarInformacion(
                "Programa eliminado",
                "El programa fue eliminado correctamente."
        );

        limpiarCampos();
    }

    @FXML
    public void limpiarCampos() {

        txtCodigo.clear();
        txtNombre.clear();
        txtIdioma.clear();
        txtDescripcion.clear();
        txtDuracionMeses.clear();
        txtValorMensual.clear();

        cmbEstado.setValue(null);
        cmbModalidad.setValue(null);
        cmbTipoPrograma.setValue(null);

        txtCantidadSesiones.clear();
        txtNivelIdioma.clear();
        txtObjetivos.clear();

        chkAccesoPlataforma.setSelected(false);
        chkClubConversacion.setSelected(false);
        chkAcompanamientoTutor.setSelected(false);

        panelPersonalizado.setVisible(false);
        panelPersonalizado.setManaged(false);

        tablaProgramas.getSelectionModel().clearSelection();
    }

    private boolean validarCampos() {

        if (txtCodigo.getText().trim().isEmpty()) {
            mostrarAdvertencia(
                    "El código del programa no puede estar vacío."
            );
            return false;
        }

        if (txtNombre.getText().trim().isEmpty()) {
            mostrarAdvertencia(
                    "El nombre del programa no puede estar vacío."
            );
            return false;
        }

        if (txtIdioma.getText().trim().isEmpty()) {
            mostrarAdvertencia(
                    "El idioma no puede estar vacío."
            );
            return false;
        }

        if (txtDescripcion.getText().trim().isEmpty()) {
            mostrarAdvertencia(
                    "La descripción no puede estar vacía."
            );
            return false;
        }

        if (txtDuracionMeses.getText().trim().isEmpty()) {
            mostrarAdvertencia(
                    "La duración en meses no puede estar vacía."
            );
            return false;
        }

        if (txtValorMensual.getText().trim().isEmpty()) {
            mostrarAdvertencia(
                    "El valor mensual no puede estar vacío."
            );
            return false;
        }

        if (cmbEstado.getValue() == null) {
            mostrarAdvertencia(
                    "Debe seleccionar un estado para el programa."
            );
            return false;
        }

        if (cmbModalidad.getValue() == null) {
            mostrarAdvertencia(
                    "Debe seleccionar una modalidad para el programa."
            );
            return false;
        }

        if (cmbTipoPrograma.getValue() == null) {
            mostrarAdvertencia(
                    "Debe seleccionar un tipo de programa."
            );
            return false;
        }

        int duracionMeses;

        try {

            duracionMeses = Integer.parseInt(
                    txtDuracionMeses.getText().trim()
            );

        } catch (NumberFormatException e) {

            mostrarAdvertencia(
                    "La duración en meses debe ser un número entero positivo."
            );

            return false;
        }

        if (duracionMeses <= 0) {

            mostrarAdvertencia(
                    "La duración en meses debe ser mayor a cero."
            );

            return false;
        }

        double valorMensual;

        try {

            valorMensual = Double.parseDouble(
                    txtValorMensual.getText().trim()
            );

        } catch (NumberFormatException e) {

            mostrarAdvertencia(
                    "El valor mensual debe ser un número positivo."
            );

            return false;
        }

        if (valorMensual <= 0) {

            mostrarAdvertencia(
                    "El valor mensual debe ser mayor a cero."
            );

            return false;
        }

        if ("Personalizado".equals(cmbTipoPrograma.getValue())) {

            if (txtCantidadSesiones.getText().trim().isEmpty()) {
                mostrarAdvertencia(
                        "La cantidad de sesiones no puede estar vacía."
                );
                return false;
            }

            if (txtNivelIdioma.getText().trim().isEmpty()) {
                mostrarAdvertencia(
                        "El nivel de idioma no puede estar vacío."
                );
                return false;
            }

            if (txtObjetivos.getText().trim().isEmpty()) {
                mostrarAdvertencia(
                        "Los objetivos del estudiante no pueden estar vacíos."
                );
                return false;
            }

            int cantidadSesiones;

            try {

                cantidadSesiones = Integer.parseInt(
                        txtCantidadSesiones.getText().trim()
                );

            } catch (NumberFormatException e) {

                mostrarAdvertencia(
                        "La cantidad de sesiones debe ser un número entero positivo."
                );

                return false;
            }

            if (cantidadSesiones <= 0) {

                mostrarAdvertencia(
                        "La cantidad de sesiones debe ser mayor a cero."
                );

                return false;
            }
        }

        return true;
    }

    private ProgramaFormacion crearPrograma() {

        String codigo =
                txtCodigo.getText().trim();

        String nombre =
                txtNombre.getText().trim();

        String idioma =
                txtIdioma.getText().trim();

        String descripcion =
                txtDescripcion.getText().trim();

        int duracionMeses =
                Integer.parseInt(
                        txtDuracionMeses.getText().trim()
                );

        double valorMensual =
                Double.parseDouble(
                        txtValorMensual.getText().trim()
                );

        EstadoPrograma estado =
                cmbEstado.getValue();

        Modalidad modalidad =
                cmbModalidad.getValue();

        List<IBeneficiable> beneficios =
                obtenerBeneficiosSeleccionados();

        String tipo =
                cmbTipoPrograma.getValue();

        if ("Básico".equals(tipo)) {

            return new ProgramaBasico(
                    codigo,
                    nombre,
                    idioma,
                    descripcion,
                    duracionMeses,
                    valorMensual,
                    estado,
                    modalidad,
                    beneficios
            );
        }

        if ("Intensivo".equals(tipo)) {

            return new ProgramaIntensivo(
                    codigo,
                    nombre,
                    idioma,
                    descripcion,
                    duracionMeses,
                    valorMensual,
                    estado,
                    modalidad,
                    beneficios
            );
        }

        int cantidadSesiones =
                Integer.parseInt(
                        txtCantidadSesiones.getText().trim()
                );

        String nivelIdioma =
                txtNivelIdioma.getText().trim();

        String objetivos =
                txtObjetivos.getText().trim();

        return new ProgramaPersonalizado(
                codigo,
                nombre,
                idioma,
                descripcion,
                duracionMeses,
                valorMensual,
                estado,
                modalidad,
                beneficios,
                cantidadSesiones,
                nivelIdioma,
                objetivos
        );
    }

    private List<IBeneficiable> obtenerBeneficiosSeleccionados() {

        List<IBeneficiable> beneficios =
                new ArrayList<>();

        if (chkAccesoPlataforma.isSelected()) {
            beneficios.add(
                    new AccesoPlataformaVirtual()
            );
        }

        if (chkClubConversacion.isSelected()) {
            beneficios.add(
                    new ClubConversacion()
            );
        }

        if (chkAcompanamientoTutor.isSelected()) {
            beneficios.add(
                    new AcompanamientoTutor()
            );
        }

        return beneficios;
    }

    private void cargarDatosPrograma(
            ProgramaFormacion programa) {

        txtCodigo.setText(
                programa.getCodigo()
        );

        txtNombre.setText(
                programa.getNombre()
        );

        txtIdioma.setText(
                programa.getIdioma()
        );

        txtDescripcion.setText(
                programa.getDescripcion()
        );

        txtDuracionMeses.setText(
                String.valueOf(
                        programa.getDuracionMeses()
                )
        );

        txtValorMensual.setText(
                String.valueOf(
                        programa.getValorMensual()
                )
        );

        cmbEstado.setValue(
                programa.getEstado()
        );

        cmbModalidad.setValue(
                programa.getModalidad()
        );

        cmbTipoPrograma.setValue(
                obtenerTipoPrograma(programa)
        );

        cargarBeneficios(programa);

        if (programa instanceof ProgramaPersonalizado) {

            ProgramaPersonalizado personalizado =
                    (ProgramaPersonalizado) programa;

            txtCantidadSesiones.setText(
                    String.valueOf(
                            personalizado
                                    .getNumeroSesionesTutor()
                    )
            );

            txtNivelIdioma.setText(
                    personalizado
                            .getNivelIdiomaRequerido()
            );

            txtObjetivos.setText(
                    personalizado
                            .getObjetivosEstudiante()
            );

            panelPersonalizado.setVisible(true);
            panelPersonalizado.setManaged(true);

        } else {

            txtCantidadSesiones.clear();
            txtNivelIdioma.clear();
            txtObjetivos.clear();

            panelPersonalizado.setVisible(false);
            panelPersonalizado.setManaged(false);
        }
    }

    private void cargarBeneficios(
            ProgramaFormacion programa) {

        chkAccesoPlataforma.setSelected(false);
        chkClubConversacion.setSelected(false);
        chkAcompanamientoTutor.setSelected(false);

        for (IBeneficiable beneficio :
                programa.getBeneficios()) {

            if (beneficio instanceof AccesoPlataformaVirtual) {

                chkAccesoPlataforma.setSelected(true);

            } else if (beneficio instanceof ClubConversacion) {

                chkClubConversacion.setSelected(true);

            } else if (beneficio instanceof AcompanamientoTutor) {

                chkAcompanamientoTutor.setSelected(true);
            }
        }
    }

    private String obtenerTipoPrograma(
            ProgramaFormacion programa) {

        if (programa instanceof ProgramaBasico) {
            return "Básico";
        }

        if (programa instanceof ProgramaIntensivo) {
            return "Intensivo";
        }

        if (programa instanceof ProgramaPersonalizado) {
            return "Personalizado";
        }

        return "Desconocido";
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