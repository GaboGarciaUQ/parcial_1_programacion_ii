package com.uniquindio.parcial1p2.modelo.comprobante;

public class ComprobanteExcel implements IExportable {

    private String numeroComprobante;

    public ComprobanteExcel(String numeroComprobante) {
        this.numeroComprobante = numeroComprobante;
    }

    @Override
    public void exportar() {
        System.out.println("Exportando comprobante Excel: " + numeroComprobante);
    }

    public String getNumeroComprobante() {
        return numeroComprobante;
    }

    public void setNumeroComprobante(String numeroComprobante) {
        this.numeroComprobante = numeroComprobante;
    }
}