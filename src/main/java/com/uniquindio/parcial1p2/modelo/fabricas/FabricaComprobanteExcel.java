package com.uniquindio.parcial1p2.modelo.fabricas;

public class FabricaComprobanteExcel implements IFabricaComprobante {

    @Override
    public IExportable crearComprobante() {
        return new ComprobanteExcel("EXCEL-" + System.currentTimeMillis());
    }
}