package com.uniquindio.parcial1p2.modelo.fabricas;

import com.uniquindio.parcial1p2.modelo.entrega.IEntregable;
import com.uniquindio.parcial1p2.modelo.entrega.MaterialImpreso;
import com.uniquindio.parcial1p2.modelo.identificacion.IIdentificable;
import com.uniquindio.parcial1p2.modelo.identificacion.CarnetFisico;

public class FabricaPresencial implements IFabricaModalidad {

    public IIdentificable crearCarnet() {
        return new CarnetFisico("C-" + System.currentTimeMillis());   // o el/los parámetros reales que pida
    }

    public IEntregable crearEntregable() {
        return new MaterialImpreso("Material impreso del programa");  // o el/los parámetros reales
    }
}