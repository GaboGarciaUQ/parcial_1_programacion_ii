package com.uniquindio.parcial1p2.modelo.programa;

import java.util.List;

import com.uniquindio.parcial1p2.modelo.beneficios.IBeneficiable;
import com.uniquindio.parcial1p2.modelo.enums.EstadoPrograma;
import com.uniquindio.parcial1p2.modelo.enums.Modalidad;

public class ProgramaPersonalizado extends ProgramaFormacion {

    private int numeroSesionesTutor;
    private String nivelIdiomaRequerido;
    private String objetivosEstudiante;

    public ProgramaPersonalizado(String codigo, String nombre, String idioma,
            String descripcion, int duracionMeses, double valorMensual,
            EstadoPrograma estado, Modalidad modalidad,
            List<IBeneficiable> beneficios, int numeroSesionesTutor,
            String nivelIdiomaRequerido, String objetivosEstudiante) {

        super(codigo, nombre, idioma, descripcion, duracionMeses,
                valorMensual, estado, modalidad, beneficios);

        if (numeroSesionesTutor <= 0) {
            throw new IllegalArgumentException(
                    "El número de sesiones del tutor debe ser mayor que cero."
            );
        }

        if (nivelIdiomaRequerido == null
                || nivelIdiomaRequerido.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "El nivel de idioma requerido es obligatorio."
            );
        }

        if (objetivosEstudiante == null
                || objetivosEstudiante.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Los objetivos del estudiante son obligatorios."
            );
        }

        this.numeroSesionesTutor = numeroSesionesTutor;
        this.nivelIdiomaRequerido = nivelIdiomaRequerido;
        this.objetivosEstudiante = objetivosEstudiante;
    }

    @Override
    public double calcularValorPrograma() {

        if (numeroSesionesTutor <= 0) {
            throw new IllegalStateException(
                    "Debe tener una cantidad válida de sesiones de tutor."
            );
        }

        if (nivelIdiomaRequerido == null
                || nivelIdiomaRequerido.trim().isEmpty()) {
            throw new IllegalStateException(
                    "Debe especificar el nivel de idioma requerido."
            );
        }

        if (objetivosEstudiante == null
                || objetivosEstudiante.trim().isEmpty()) {
            throw new IllegalStateException(
                    "Debe especificar los objetivos del estudiante."
            );
        }

        double valorPrograma = getValorMensual() * getDuracionMeses();

        return valorPrograma;
    }

    public int getNumeroSesionesTutor() {
        return numeroSesionesTutor;
    }

    public void setNumeroSesionesTutor(int numeroSesionesTutor) {
        if (numeroSesionesTutor <= 0) {
            throw new IllegalArgumentException(
                    "El número de sesiones del tutor debe ser mayor que cero."
            );
        }

        this.numeroSesionesTutor = numeroSesionesTutor;
    }

    public String getNivelIdiomaRequerido() {
        return nivelIdiomaRequerido;
    }

    public void setNivelIdiomaRequerido(String nivelIdiomaRequerido) {
        if (nivelIdiomaRequerido == null
                || nivelIdiomaRequerido.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "El nivel de idioma requerido es obligatorio."
            );
        }

        this.nivelIdiomaRequerido = nivelIdiomaRequerido;
    }

    public String getObjetivosEstudiante() {
        return objetivosEstudiante;
    }

    public void setObjetivosEstudiante(String objetivosEstudiante) {
        if (objetivosEstudiante == null
                || objetivosEstudiante.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Los objetivos del estudiante son obligatorios."
            );
        }

        this.objetivosEstudiante = objetivosEstudiante;
    }
}