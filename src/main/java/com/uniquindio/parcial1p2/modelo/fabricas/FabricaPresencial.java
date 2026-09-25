package com.uniquindio.parcial1p2.modelo.fabricas;

import com.uniquindio.parcial1p2.modelo.entrega.IEntregable;
import com.uniquindio.parcial1p2.modelo.identificacion.IIdentificable;

public class FabricaPresencial implements IFabricaModalidad{

    @Override
    public IIdentificable crearCarnet() {
        IIdentificable carnet = new IIdentificable();
        return carnet;
    }

    @Override
    public IEntregable crearEntregable() {
        IEntregable entregable = new IEntregable();
        return entregable;
    }
    
}