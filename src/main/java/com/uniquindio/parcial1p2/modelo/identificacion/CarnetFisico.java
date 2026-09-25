package com.uniquindio.parcial1p2.modelo.identificacion;

public class CarnetFisico implements IIdentificable{

    private String id;

    public CarnetFisico(String id){
        this.id = id;
    }

    @Override
    public String obtenerIdentificacion() {
        String id = "";
        return id;
    }

}