package com.uniquindio.parcial1p2.modelo.identificacion;

public class CarnetDigital implements IIdentificable {

    private String id;

    public CarnetDigital(String id) {
        this.id = id;
    }

    @Override
    public String obtenerIdentificacion() {
        return id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}