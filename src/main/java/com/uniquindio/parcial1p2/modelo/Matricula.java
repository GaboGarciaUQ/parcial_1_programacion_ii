package com.uniquindio.parcial1p2.modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.uniquindio.parcial1p2.modelo.comprobante.IExportable;
import com.uniquindio.parcial1p2.modelo.entrega.IEntregable;
import com.uniquindio.parcial1p2.modelo.fabricas.IFabricaComprobante;
import com.uniquindio.parcial1p2.modelo.fabricas.IFabricaModalidad;
import com.uniquindio.parcial1p2.modelo.identificacion.IIdentificable;
import com.uniquindio.parcial1p2.modelo.programa.ProgramaFormacion;

public class Matricula {

    private int numeroMatricula;
    private Estudiante estudiante;
    private ProgramaFormacion programa;
    private LocalDate fechaInicio;
    private Docente docenteTutor;
    private List<ServicioAdicional> serviciosAdicionales;
    private double descuento;
    private String observaciones;
    private IExportable comprobante;
    private IIdentificable carnet;
    private IEntregable entregable;

    public Matricula(int numeroMatricula,
                     Estudiante estudiante,
                     ProgramaFormacion programa,
                     LocalDate fechaInicio) {

        if (numeroMatricula <= 0) {
            throw new IllegalArgumentException(
                    "El número de matrícula debe ser mayor que cero."
            );
        }

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

        this.numeroMatricula = numeroMatricula;
        this.estudiante = estudiante;
        this.programa = programa;
        this.fechaInicio = fechaInicio;
        this.docenteTutor = null;
        this.serviciosAdicionales = new ArrayList<>();
        this.descuento = 0.0;
        this.observaciones = "";
        this.comprobante = null;
        this.carnet = null;
        this.entregable = null;
    }

    public void asignarTutor(Docente docente) {
        this.docenteTutor = docente;
    }

    public void agregarServicio(ServicioAdicional servicio) {

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
    }

    public void eliminarServicio(ServicioAdicional servicio) {
        serviciosAdicionales.remove(servicio);
    }

    public double calcularValorFinal() {

        double valorPrograma = programa.calcularValorFinal();
        double valorServicios = obtenerValorServicios();

        double subtotal = valorPrograma + valorServicios;

        double valorDescuento = subtotal * (descuento / 100.0);

        return subtotal - valorDescuento;
    }

    public IExportable generarComprobante(
            IFabricaComprobante fabrica) {

        if (fabrica == null) {
            throw new IllegalArgumentException(
                    "La fábrica de comprobante no puede ser nula."
            );
        }

        this.comprobante = fabrica.crearComprobante();

        return this.comprobante;
    }

    public void configurarModalidad(IFabricaModalidad fabrica) {

        if (fabrica == null) {
            throw new IllegalArgumentException(
                    "La fábrica de modalidad no puede ser nula."
            );
        }

        this.carnet = fabrica.crearCarnet();
        this.entregable = fabrica.crearEntregable();
    }

    public boolean validarDescuento() {
        return descuento >= 0.0 && descuento <= 30.0;
    }

    public double obtenerValorServicios() {

        double total = 0.0;

        for (ServicioAdicional servicio : serviciosAdicionales) {
            total += servicio.obtenerPrecio();
        }

        return total;
    }

    public int getNumeroMatricula() {
        return numeroMatricula;
    }



    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {

        if (estudiante == null) {
            throw new IllegalArgumentException(
                    "El estudiante es obligatorio."
            );
        }

        this.estudiante = estudiante;
    }

    public ProgramaFormacion getPrograma() {
        return programa;
    }

    public void setPrograma(ProgramaFormacion programa) {

        if (programa == null) {
            throw new IllegalArgumentException(
                    "El programa es obligatorio."
            );
        }

        this.programa = programa;
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

    public Docente getDocenteTutor() {
        return docenteTutor;
    }

    public void setDocenteTutor(Docente docenteTutor) {
        this.docenteTutor = docenteTutor;
    }

    public List<ServicioAdicional> getServiciosAdicionales() {
        return new ArrayList<>(serviciosAdicionales);
    }

    public void setServiciosAdicionales(
            List<ServicioAdicional> serviciosAdicionales) {

        if (serviciosAdicionales == null) {
            throw new IllegalArgumentException(
                    "La lista de servicios no puede ser nula."
            );
        }

        this.serviciosAdicionales =
                new ArrayList<>(serviciosAdicionales);
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {

        if (descuento < 0.0 || descuento > 30.0) {
            throw new IllegalArgumentException(
                    "El descuento debe estar entre 0% y 30%."
            );
        }

        this.descuento = descuento;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public IExportable getComprobante() {
        return comprobante;
    }

    public void setComprobante(IExportable comprobante) {
        this.comprobante = comprobante;
    }

    public IIdentificable getCarnet() {
        return carnet;
    }

    public void setCarnet(IIdentificable carnet) {
        this.carnet = carnet;
    }

    public IEntregable getEntregable() {
        return entregable;
    }

    public void setEntregable(IEntregable entregable) {
        this.entregable = entregable;
    }
}