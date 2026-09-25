package com.uniquindio.parcial1p2.modelo.comprobante;

public class ComprobantePDF implements IExportable {

    private String numeroComprobante;

    public ComprobantePDF(String numeroComprobante) {
        this.numeroComprobante = numeroComprobante;
    }

    @Override
    public void exportar() {
        System.out.println("Exportando comprobante PDF: " + numeroComprobante);
    }

    public String getNumeroComprobante() {
        return numeroComprobante;
    }

    public void setNumeroComprobante(String numeroComprobante) {
        this.numeroComprobante = numeroComprobante;
    }
}