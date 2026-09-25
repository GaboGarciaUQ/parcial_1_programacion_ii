package com.uniquindio.parcial1p2.modelo;

public class ContadorMatricula {

    private static ContadorMatricula instancia;
    private int siguienteNumero;

    private ContadorMatricula() {
        siguienteNumero = 1;
    }

    public static ContadorMatricula getInstancia() {
        if (instancia == null) {
            instancia = new ContadorMatricula();
        }

        return instancia;
    }

    public int obtenerSiguienteNumero() {
        return siguienteNumero++;
    }

    public void reiniciarContador() {
        siguienteNumero = 1;
    }
}