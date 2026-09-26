package com.uniquindio.parcial1p2.modelo.fabricas;

import com.uniquindio.parcial1p2.modelo.comprobante.ComprobantePDF;
import com.uniquindio.parcial1p2.modelo.comprobante.IExportable;

public class FabricaComprobantePDF implements IFabricaComprobante {

    @Override
    public IExportable crearComprobante() {
        return new ComprobantePDF("PDF-" + System.currentTimeMillis());
    }
}