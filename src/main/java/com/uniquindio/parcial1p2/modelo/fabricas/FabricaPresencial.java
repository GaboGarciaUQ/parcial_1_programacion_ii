package com.uniquindio.parcial1p2.modelo.fabricas;

import com.uniquindio.parcial1p2.modelo.entrega.IEntregable;
import com.uniquindio.parcial1p2.modelo.identificacion.IIdentificable;

public class FabricaPresencial implements IFabricaModalidad{

    public IIdentificable crearCarnet() {
        return new IIdentificable();
    }
    
    public IEntregable crearEntregable() {
        return new IEntregable();
    }
    
}