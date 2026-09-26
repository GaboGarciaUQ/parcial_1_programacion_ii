package com.uniquindio.parcial1p2.modelo.programa;

import java.util.List;

import com.uniquindio.parcial1p2.modelo.beneficios.IBeneficiable;
import com.uniquindio.parcial1p2.modelo.enums.EstadoPrograma;
import com.uniquindio.parcial1p2.modelo.enums.Modalidad;

public class ProgramaBasico extends ProgramaFormacion{

    public ProgramaBasico(String codigo, String nombre, String idioma, String descripcion, int duracionMeses,
            double valorMensual, EstadoPrograma estado, Modalidad modalidad, List<IBeneficiable> beneficios) {
        super(codigo, nombre, idioma, descripcion, duracionMeses, valorMensual, estado, modalidad, beneficios);
    }

    public double calcularValorPrograma(){
        double valorPrograma = 0;
        return valorPrograma;
    }
    
}