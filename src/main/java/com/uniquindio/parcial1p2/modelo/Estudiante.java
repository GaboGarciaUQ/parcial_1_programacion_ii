package com.uniquindio.parcial1p2.modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Estudiante {

    private String nombreCompleto;
    private String documentoIdentidad;
    private String telefono;
    private String correoElectronico;
    private int edad;
    private LocalDate fechaRegistro;

    private List<Matricula> matriculas;

    public Estudiante(String nombreCompleto,
                      String documentoIdentidad,
                      String telefono,
                      String correoElectronico,
                      int edad,
                      LocalDate fechaRegistro) {

        this.nombreCompleto = nombreCompleto;
        this.documentoIdentidad = documentoIdentidad;
        this.telefono = telefono;
        this.correoElectronico = correoElectronico;
        this.edad = edad;
        this.fechaRegistro = fechaRegistro;
        this.matriculas = new ArrayList<>();
    }

    public void adquirirPrograma(Matricula matricula) {
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

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getDocumentoIdentidad() {
        return documentoIdentidad;
    }

    public void setDocumentoIdentidad(String documentoIdentidad) {
        this.documentoIdentidad = documentoIdentidad;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
}