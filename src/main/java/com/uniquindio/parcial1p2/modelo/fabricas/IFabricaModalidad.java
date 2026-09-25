package com.uniquindio.parcial1p2.modelo.fabricas;

import com.uniquindio.parcial1p2.modelo.entrega.IEntregable;
import com.uniquindio.parcial1p2.modelo.identificacion.IIdentificable;

public interface IFabricaModalidad{

    IIdentificable crearCarnet();

    IEntregable crearEntregable();
}