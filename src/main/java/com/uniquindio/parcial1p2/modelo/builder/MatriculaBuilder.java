package com.uniquindio.parcial1p2.modelo.builder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.uniquindio.parcial1p2.modelo.ContadorMatricula;
import com.uniquindio.parcial1p2.modelo.Docente;
import com.uniquindio.parcial1p2.modelo.Estudiante;
import com.uniquindio.parcial1p2.modelo.Matricula;
import com.uniquindio.parcial1p2.modelo.ServicioAdicional;
import com.uniquindio.parcial1p2.modelo.programa.ProgramaFormacion;

public class MatriculaBuilder {

    private int numeroMatricula;
    private Estudiante estudiante;
    private ProgramaFormacion programa;
    private LocalDate fechaInicio;
    private Docente docenteTutor;
    private List<ServicioAdicional> serviciosAdicionales;
    private double descuento;
    private String observaciones;

    public MatriculaBuilder(Estudiante estudiante,
                            ProgramaFormacion programa,
                            LocalDate fechaInicio) {

        if (estudiante == null) {
            throw new IllegalArgumentException(
                    "El estudiante es obligatorio."
            );
        }

        if (programa == null) {
            throw new IllegalArgumentException(
                    "El programa es obligatorio."
            );
        }

        if (fechaInicio == null) {
            throw new IllegalArgumentException(
                    "La fecha de inicio es obligatoria."
            );
        }

        this.numeroMatricula =
                ContadorMatricula.getInstancia().obtenerSiguienteNumero();

        this.estudiante = estudiante;
        this.programa = programa;
        this.fechaInicio = fechaInicio;
        this.docenteTutor = null;
        this.serviciosAdicionales = new ArrayList<>();
        this.descuento = 0.0;
        this.observaciones = "";
    }

    public MatriculaBuilder asignarTutor(Docente docente) {

        this.docenteTutor = docente;

        return this;
    }

    public MatriculaBuilder agregarServicio(
            ServicioAdicional servicio) {

        if (servicio == null) {
            throw new IllegalArgumentException(
                    "El servicio adicional no puede ser nulo."
            );
        }

        if (!servicio.estaDisponible()) {
            throw new IllegalArgumentException(
                    "El servicio adicional no está disponible."
            );
        }

        if (!serviciosAdicionales.contains(servicio)) {
            serviciosAdicionales.add(servicio);
        }

        return this;
    }

    public MatriculaBuilder establecerDescuento(
            double descuento) {

        if (descuento < 0.0 || descuento > 30.0) {
            throw new IllegalArgumentException(
                    "El descuento debe estar entre 0% y 30%."
            );
        }

        this.descuento = descuento;

        return this;
    }

    public MatriculaBuilder establecerObservaciones(
            String observaciones) {

        this.observaciones = observaciones;

        return this;
    }

    public Matricula construir() {

        Matricula matricula = new Matricula(
                numeroMatricula,
                estudiante,
                programa,
                fechaInicio
        );

        matricula.setDocenteTutor(docenteTutor);
        matricula.setServiciosAdicionales(
                new ArrayList<>(serviciosAdicionales)
        );
        matricula.setDescuento(descuento);
        matricula.setObservaciones(observaciones);

        return matricula;
    }
}