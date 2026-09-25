package com.uniquindio.parcial1p2.modelo;

public class ServicioAdicional {

    private String codigo;
    private String nombre;
    private String descripcion;
    private double precio;
    private boolean disponibilidad;
    private TipoServicio tipo;

    public ServicioAdicional(String codigo,
                             String nombre,
                             String descripcion,
                             double precio,
                             boolean disponibilidad,
                             TipoServicio tipo) {

        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.disponibilidad = disponibilidad;
        this.tipo = tipo;
    }

    public boolean estaDisponible() {
        return disponibilidad;
    }

    public void actualizarDisponibilidad(boolean disponible) {
        this.disponibilidad = disponible;
    }

    public double obtenerPrecio() {
        return precio;
    }

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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public boolean isDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(boolean disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    public TipoServicio getTipo() {
        return tipo;
    }

    public void setTipo(TipoServicio tipo) {
        this.tipo = tipo;
    }
}