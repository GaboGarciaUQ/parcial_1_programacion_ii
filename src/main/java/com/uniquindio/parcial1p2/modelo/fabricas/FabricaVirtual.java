package com.uniquindio.parcial1p2.modelo.fabricas;

import com.uniquindio.parcial1p2.modelo.entrega.IEntregable;
import com.uniquindio.parcial1p2.modelo.entrega.LicenciaPlataforma;
import com.uniquindio.parcial1p2.modelo.identificacion.IIdentificable;
import com.uniquindio.parcial1p2.modelo.identificacion.CarnetDigital;

public class FabricaVirtual implements IFabricaModalidad {

    @Override
    public IIdentificable crearCarnet() {
        return new CarnetDigital("CD-" + System.currentTimeMillis());
    }

    @Override
    public IEntregable crearEntregable() {
        return new LicenciaPlataforma(
                "LP-" + System.currentTimeMillis()
        );
    }
}