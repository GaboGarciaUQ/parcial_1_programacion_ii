package com.uniquindio.parcial1p2.modelo;

import java.util.ArrayList;
import java.util.List;

public class OfertaAcademica implements Cloneable {

    private List<ProgramaFormacion> programas;

    public OfertaAcademica(List<ProgramaFormacion> programas) {
        if (programas == null) {
            throw new IllegalArgumentException(
                    "La lista de programas no puede ser nula."
            );
        }

        this.programas = new ArrayList<>(programas);
    }

    public void agregarPrograma(ProgramaFormacion programa) {
        if (programa == null) {
            throw new IllegalArgumentException(
                    "El programa no puede ser nulo."
            );
        }

        if (!programas.contains(programa)) {
            programas.add(programa);
        }
    }

    public void eliminarPrograma(ProgramaFormacion programa) {
        programas.remove(programa);
    }

    public List<ProgramaFormacion> obtenerProgramas() {
        return new ArrayList<>(programas);
    }

    public OfertaAcademica clonarOferta() {
        return clonar();
    }

    @Override
    public OfertaAcademica clonar() {
        try {
            OfertaAcademica copia = (OfertaAcademica) super.clone();

            copia.programas = new ArrayList<>(this.programas);

            return copia;

        } catch (CloneNotSupportedException e) {
            throw new IllegalStateException(
                    "No se pudo clonar la oferta académica.", e
            );
        }
    }
}