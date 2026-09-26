package com.uniquindio.parcial1p2.modelo;

public class Docente {

    private String documentoIdentidad;
    private String nombreCompleto;
    private String especialidadIdioma;
    private String telefono;
    private double tarifaSesion;

    public Docente(String documentoIdentidad, String nombreCompleto,
                   String especialidadIdioma, String telefono,
                   double tarifaSesion) {

        this.documentoIdentidad = documentoIdentidad;
        this.nombreCompleto = nombreCompleto;
        this.especialidadIdioma = especialidadIdioma;
        this.telefono = telefono;
        this.tarifaSesion = tarifaSesion;
    }

    public void asignarComoTutor(Matricula matricula) {
        if (matricula == null) {
            throw new IllegalArgumentException(
                    "La matrícula no puede ser nula."
            );
        }

        matricula.asignarTutor(this);
    }

    public double calcularValorSesion() {
        return tarifaSesion;
    }

    public String getDocumentoIdentidad() {
        return documentoIdentidad;
    }

    public void setDocumentoIdentidad(String documentoIdentidad) {
        this.documentoIdentidad = documentoIdentidad;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getEspecialidadIdioma() {
        return especialidadIdioma;
    }

    public void setEspecialidadIdioma(String especialidadIdioma) {
        this.especialidadIdioma = especialidadIdioma;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public double getTarifaSesion() {
        return tarifaSesion;
    }

    public void setTarifaSesion(double tarifaSesion) {
        this.tarifaSesion = tarifaSesion;
    }
}