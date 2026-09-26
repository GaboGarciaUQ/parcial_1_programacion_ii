package com.uniquindio.parcial1p2.modelo.programa;

import java.util.List;

import com.uniquindio.parcial1p2.modelo.beneficios.IBeneficiable;
import com.uniquindio.parcial1p2.modelo.enums.EstadoPrograma;
import com.uniquindio.parcial1p2.modelo.enums.Modalidad;

public abstract class ProgramaFormacion {

    private String codigo;
    private String nombre;
    private String idioma;
    private String descripcion;
    private int duracionMeses;
    private double valorMensual;
    private EstadoPrograma estado;
    private Modalidad modalidad;
    private List<IBeneficiable> beneficios;

    public ProgramaFormacion(String codigo, String nombre, String idioma,
            String descripcion, int duracionMeses, double valorMensual,
            EstadoPrograma estado, Modalidad modalidad,
            List<IBeneficiable> beneficios) {

        if (duracionMeses < 0) {
            throw new IllegalArgumentException(
                    "La duración en meses no puede ser negativa."
            );
        }

        if (valorMensual < 0) {
            throw new IllegalArgumentException(
                    "El valor mensual no puede ser negativo."
            );
        }

        if (estado == null) {
            throw new IllegalArgumentException(
                    "El estado del programa no puede ser nulo."
            );
        }

        this.codigo = codigo;
        this.nombre = nombre;
        this.idioma = idioma;
        this.descripcion = descripcion;
        this.duracionMeses = duracionMeses;
        this.valorMensual = valorMensual;
        this.estado = estado;
        this.modalidad = modalidad;
        this.beneficios = beneficios;
    }

    public double calcularValorFinal() {
        double valorFinal = 0;
        valorFinal += duracionMeses*valorMensual;
        for(IBeneficiable beneficio : beneficios){
            valorFinal += beneficio.calcularBeneficio();
        }
        return valorFinal;
    }

    public void agregarBeneficio(IBeneficiable beneficio) {
        this.beneficios.add(beneficio);
    }

    public void elminarBeneficio(IBeneficiable beneficio) {
        this.beneficios.remove(beneficio);
    }

    public List<IBeneficiable> obtenerBeneficios() {
        return beneficios;
    }

    public double obtenerValorTotal(){
        double valorTotal = this.calcularValorFinal();
        return valorTotal;
    }

    public abstract double calcularValorPrograma();

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getDuracionMeses() {
        return duracionMeses;
    }

    public void setDuracionMeses(int duracionMeses) {
        if (duracionMeses < 0) {
            throw new IllegalArgumentException(
                    "La duración en meses no puede ser negativa."
            );
        }

        this.duracionMeses = duracionMeses;
    }

    public double getValorMensual() {
        return valorMensual;
    }

    public void setValorMensual(double valorMensual) {
        if (valorMensual < 0) {
            throw new IllegalArgumentException(
                    "El valor mensual no puede ser negativo."
            );
        }

        this.valorMensual = valorMensual;
    }

    public EstadoPrograma getEstado() {
        return estado;
    }

    public void setEstado(EstadoPrograma estado) {
        if (estado == null) {
            throw new IllegalArgumentException(
                    "El estado del programa no puede ser nulo."
            );
        }

        this.estado = estado;
    }

    public Modalidad getModalidad() {
        return modalidad;
    }

    public void setModalidad(Modalidad modalidad) {
        this.modalidad = modalidad;
    }

    public List<IBeneficiable> getBeneficios() {
        return beneficios;
    }

    public void setBeneficios(List<IBeneficiable> beneficios) {
        this.beneficios = beneficios;
    }
}