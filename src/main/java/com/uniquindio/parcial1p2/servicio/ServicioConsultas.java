package com.uniquindio.parcial1p2.servicio;

import java.time.LocalDate;
import java.util.List;

import com.uniquindio.parcial1p2.modelo.Estudiante;
import com.uniquindio.parcial1p2.modelo.Matricula;

public class ServicioConsultas {

    public Estudiante buscarEstudiantePorTelefono(
            String telefono,
            List<Estudiante> estudiantes) {

        if (telefono == null || estudiantes == null) {
            return null;
        }

        for (Estudiante estudiante : estudiantes) {
            if (estudiante != null
                    && telefono.equals(estudiante.getTelefono())) {
                return estudiante;
            }
        }

        return null;
    }

    public boolean esNumeroPerfecto(int numero) {
        if (numero <= 1) {
            return false;
        }

        int sumaDivisores = 1;

        for (int i = 2; i <= numero / 2; i++) {
            if (numero % i == 0) {
                sumaDivisores += i;
            }
        }

        return sumaDivisores == numero;
    }

    public double calcularIngresosPorPeriodo(
            LocalDate fechaInicio,
            LocalDate fechaFin,
            List<Matricula> matriculas) {

        if (fechaInicio == null || fechaFin == null || matriculas == null) {
            return 0.0;
        }

        if (fechaFin.isBefore(fechaInicio)) {
            throw new IllegalArgumentException(
                    "La fecha de fin no puede ser anterior a la fecha de inicio."
            );
        }

        double totalIngresos = 0.0;

        for (Matricula matricula : matriculas) {
            if (matricula == null || matricula.getFechaInicio() == null) {
                continue;
            }

            LocalDate fechaMatricula = matricula.getFechaInicio();

            if (!fechaMatricula.isBefore(fechaInicio)
                    && !fechaMatricula.isAfter(fechaFin)) {

                totalIngresos += matricula.calcularValorFinal();
            }
        }

        return totalIngresos;
    }
}