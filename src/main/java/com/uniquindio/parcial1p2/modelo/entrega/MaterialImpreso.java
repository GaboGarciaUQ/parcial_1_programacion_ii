package com.uniquindio.parcial1p2.modelo.entrega;

public class MaterialImpreso implements IEntregable {

    private String descripcion;

    public MaterialImpreso(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String obtenerDescripcion() {
        return descripcion;
    }

    @Override
    public void entregar() {
        System.out.println("Entregando material impreso: " + descripcion);
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}