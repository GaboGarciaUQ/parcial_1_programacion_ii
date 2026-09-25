package com.uniquindio.parcial1p2.modelo.entrega;
public class MaterialImpreso implements IEntregable{

    private String descripcion;

    public MaterialImpreso(String descripcion){
        this.descripcion = descripcion;
    }

    public String obtenerDescripcion(){
        String desc = "";
        return desc;
    }

    public void entregar(){
    }

}