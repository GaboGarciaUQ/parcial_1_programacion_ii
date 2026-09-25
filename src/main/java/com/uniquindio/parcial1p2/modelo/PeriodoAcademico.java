package com.uniquindio.parcial1p2.modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PeriodoAcademico {

    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private OfertaAcademica ofertaAcademica;
    private List<Matricula> matriculas;

    public PeriodoAcademico(LocalDate fechaInicio,
                            LocalDate fechaFin,
                            OfertaAcademica ofertaAcademica) {

        if (fechaInicio == null || fechaFin == null) {
            throw new IllegalArgumentException(
                    "Las fechas del periodo son obligatorias."
            );
        }

        if (fechaFin.isBefore(fechaInicio)) {
            throw new IllegalArgumentException(
                    "La fecha de fin no puede ser anterior a la fecha de inicio."
            );
        }

        if (ofertaAcademica == null) {
            throw new IllegalArgumentException(
                    "La oferta académica es obligatoria."
            );
        }

        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.ofertaAcademica = ofertaAcademica;
        this.matriculas = new ArrayList<>();
    }

    public void registrarMatricula(Matricula matricula) {
        if (matricula == null) {
            throw new IllegalArgumentException(
                    "La matrícula no puede ser nula."
            );
        }

        if (!matriculas.contains(matricula)) {
            matriculas.add(matricula);
        }
    }

    public List<Matricula> obtenerMatriculas() {
        return new ArrayList<>(matriculas);
    }

    public OfertaAcademica obtenerOfertaAcademica() {
        return ofertaAcademica;
    }

    public boolean estaActivo() {
        LocalDate hoy = LocalDate.now();

        return !hoy.isBefore(fechaInicio)
                && !hoy.isAfter(fechaFin);
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        if (fechaInicio == null) {
            throw new IllegalArgumentException(
                    "La fecha de inicio es obligatoria."
            );
        }

        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        if (fechaFin == null) {
            throw new IllegalArgumentException(
                    "La fecha de fin es obligatoria."
            );
        }

        this.fechaFin = fechaFin;
    }

    public void setOfertaAcademica(OfertaAcademica ofertaAcademica) {
        if (ofertaAcademica == null) {
            throw new IllegalArgumentException(
                    "La oferta académica es obligatoria."
            );
        }

        this.ofertaAcademica = ofertaAcademica;
    }

    public void setMatriculas(List<Matricula> matriculas) {
        if (matriculas == null) {
            throw new IllegalArgumentException(
                    "La lista de matrículas no puede ser nula."
            );
        }

        this.matriculas = new ArrayList<>(matriculas);
    }
}