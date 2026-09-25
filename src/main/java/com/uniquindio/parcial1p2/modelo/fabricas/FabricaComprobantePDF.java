package com.uniquindio.parcial1p2.modelo.fabricas;

public class FabricaComprobantePDF implements IFabricaComprobante {

    @Override
    public IExportable crearComprobante() {
        return new ComprobantePDF("PDF-" + System.currentTimeMillis());
    }
}