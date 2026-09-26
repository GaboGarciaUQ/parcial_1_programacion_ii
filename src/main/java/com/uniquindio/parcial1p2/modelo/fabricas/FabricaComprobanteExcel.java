package com.uniquindio.parcial1p2.modelo.fabricas;

import com.uniquindio.parcial1p2.modelo.comprobante.ComprobanteExcel;
import com.uniquindio.parcial1p2.modelo.comprobante.IExportable;

public class FabricaComprobanteExcel implements IFabricaComprobante {

    @Override
    public IExportable crearComprobante() {
        return new ComprobanteExcel("EXCEL-" + System.currentTimeMillis());
    }
}