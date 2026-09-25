package com.uniquindio.parcial1p2.modelo.programa;

import java.util.List;

import com.uniquindio.parcial1p2.modelo.beneficios.IBeneficiable;
import com.uniquindio.parcial1p2.modelo.enums.EstadoPrograma;
import com.uniquindio.parcial1p2.modelo.enums.Modalidad;

public class ProgramaPersonalizado extends ProgramaFormacion{

    private int numeroSesionesTutor;
    private String nivelIdiomaRequerido;
    private String objetivosEstudiante;

    public ProgramaPersonalizado(String codigo, String nombre, String idioma, String descripcion, int duracionMeses,
            double valorMensual, EstadoPrograma estado, Modalidad modalidad, List<IBeneficiable> beneficios, 
            int numeroSesionesTutor, String nivelIdiomaRequerido, String objetivosEstudiante) {
        super(codigo, nombre, idioma, descripcion, duracionMeses, valorMensual, estado, modalidad, beneficios);
        this.numeroSesionesTutor = numeroSesionesTutor;
        this.nivelIdiomaRequerido = nivelIdiomaRequerido;
        this.objetivosEstudiante = objetivosEstudiante;
    }

    public double calcularValorPrograma(){
        double valorPrograma = 0;
        return valorPrograma;
    }

    public int getNumeroSesionesTutor() {
        return numeroSesionesTutor;
    }

    public void setNumeroSesionesTutor(int numeroSesionesTutor) {
        this.numeroSesionesTutor = numeroSesionesTutor;
    }

    public String getNivelIdiomaRequerido() {
        return nivelIdiomaRequerido;
    }

    public void setNivelIdiomaRequerido(String nivelIdiomaRequerido) {
        this.nivelIdiomaRequerido = nivelIdiomaRequerido;
    }

    public String getObjetivosEstudiante() {
        return objetivosEstudiante;
    }

    public void setObjetivosEstudiante(String objetivosEstudiante) {
        this.objetivosEstudiante = objetivosEstudiante;
    }
    
}