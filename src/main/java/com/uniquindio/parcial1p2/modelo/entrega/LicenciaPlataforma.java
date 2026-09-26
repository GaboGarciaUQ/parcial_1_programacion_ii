package com.uniquindio.parcial1p2.modelo.entrega;
public class LicenciaPlataforma implements IEntregable{

    private String codigoLicencia;

    public LicenciaPlataforma(String codigoLicencia){
        this.codigoLicencia = codigoLicencia;
    }

    public String obtenerDescripcion(){
        String licencia = "";
        return licencia;
    }

    public void entregar(){
    }

    public String getCodigoLicencia() {
        return codigoLicencia;
    }

    public void setCodigoLicencia(String codigoLicencia) {
        this.codigoLicencia = codigoLicencia;
    }

}