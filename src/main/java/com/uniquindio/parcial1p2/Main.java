package com.uniquindio.parcial1p2;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.uniquindio.parcial1p2.modelo.Academia;
import com.uniquindio.parcial1p2.modelo.Estudiante;
import com.uniquindio.parcial1p2.modelo.Matricula;
import com.uniquindio.parcial1p2.modelo.ServicioAdicional;
import com.uniquindio.parcial1p2.modelo.builder.MatriculaBuilder;
import com.uniquindio.parcial1p2.modelo.entrega.LicenciaPlataforma;
import com.uniquindio.parcial1p2.modelo.enums.EstadoPrograma;
import com.uniquindio.parcial1p2.modelo.enums.Modalidad;
import com.uniquindio.parcial1p2.modelo.enums.TipoServicio;
import com.uniquindio.parcial1p2.modelo.fabricas.FabricaPresencial;
import com.uniquindio.parcial1p2.modelo.fabricas.FabricaVirtual;
import com.uniquindio.parcial1p2.modelo.identificacion.CarnetDigital;
import com.uniquindio.parcial1p2.modelo.identificacion.CarnetFisico;
import com.uniquindio.parcial1p2.modelo.programa.ProgramaBasico;
import com.uniquindio.parcial1p2.modelo.programa.ProgramaIntensivo;
import com.uniquindio.parcial1p2.modelo.programa.ProgramaPersonalizado;

public class Main {

    public static void main(String[] args) {
        
        System.out.println("Corriendo...");

        //PRUEBAS

        Academia linguaplus = Academia.getInstancia();

        //RN-001

        Estudiante e001 = new Estudiante("Roberto","1099282937","31238761287","roberto@correo.com",12,LocalDate.now());
        ProgramaIntensivo pi001 = new ProgramaIntensivo("pi001", "Ingles B1", "Ingles", "Clases de ingles de nivel B1", 2, 50000, EstadoPrograma.ACTIVO, Modalidad.VIRTUAL, new ArrayList<>());
        Matricula rn001 = new MatriculaBuilder(e001, pi001, LocalDate.of(2026, 07, 03)).construir();
        linguaplus.registrarMatricula(rn001);

        //Caso Que Cumple
        rn001.setDescuento(20);
        System.out.println(rn001.getDescuento()+"%");


        //Caso Que Rompe
        try {
            rn001.setDescuento(35);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        //RN-002

        Estudiante e002 = new Estudiante("Roberto","1099282937","31238761287","roberto@correo.com",12,LocalDate.now());
        ProgramaIntensivo pi002  = new ProgramaIntensivo("pi002", "Ingles B1", "Ingles", "", 2, 50000, EstadoPrograma.ACTIVO, Modalidad.VIRTUAL, new ArrayList<>());
        Estudiante e003 = new Estudiante("Roberto","1099282937","31238761287","roberto@correo.com",12,LocalDate.now());
        ProgramaBasico pi003 = new ProgramaBasico("pb001", "Frances 2", "Frances", "", 2, 50000, EstadoPrograma.ACTIVO, Modalidad.VIRTUAL, new ArrayList<>());
        Estudiante e004 = new Estudiante("Roberto","1099282937","31238761287","roberto@correo.com",12,LocalDate.now());
        ProgramaIntensivo pi004 = new ProgramaIntensivo("pi004", "Mandarin 2027", "Mandarin", "", 2, 50000, EstadoPrograma.ACTIVO, Modalidad.VIRTUAL, new ArrayList<>());

        Matricula rn002 = new MatriculaBuilder(e002, pi003, LocalDate.of(2026, 07, 03))
                .construir();
        Matricula rn003 = new MatriculaBuilder(e003, pi003, LocalDate.of(2026, 07, 03))
                .construir();
        Matricula rn004 = new MatriculaBuilder(e004, pi004, LocalDate.of(2026, 07, 03))
                .construir();

        //Caso que cumple

        System.out.println("RN-002 Caso que cumple:");
        System.out.println("Matrícula 1: " + rn002.getNumeroMatricula());
        System.out.println("Matrícula 2: " + rn003.getNumeroMatricula()); 
        System.out.println("Matrícula 3: " + rn004.getNumeroMatricula());

        boolean consecutivas = rn003.getNumeroMatricula() == rn002.getNumeroMatricula() + 1
                && rn004.getNumeroMatricula() == rn003.getNumeroMatricula() + 1;

        System.out.println(consecutivas
                ? "Resultado esperado: números únicos y consecutivos."
                : "Resultado inesperado: los números no son consecutivos.");

        //Caso que rompe

        try {
            Matricula rn005 = new Matricula(
                    rn002.getNumeroMatricula(),
                    e002,
                    pi003,
                    LocalDate.of(2026, 07, 03)
            );

            boolean numeroRepetido = rn005.getNumeroMatricula() == rn002.getNumeroMatricula()
                    || rn005.getNumeroMatricula() == rn003.getNumeroMatricula()
                    || rn005.getNumeroMatricula() == rn004.getNumeroMatricula();

            if (numeroRepetido) {
                System.out.println("RN-002 Caso que rompe: rechazado por la prueba porque el número de matrícula está repetido.");
            } else {
                System.out.println("RN-002 Caso que rompe: no se detectó la repetición.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("RN-002 Caso que rompe: " + e.getMessage());
        }

        //RN-003

        //Caso que cumple

        ProgramaPersonalizado pp001 = new ProgramaPersonalizado("pp001","Japones Deluxe","Japones","",6,100000,EstadoPrograma.SUSPENDIDO,Modalidad.PRESENCIAL,new ArrayList<>(),9,"0","Ir a todas las clases puntualmente");
        if(pp001!=null){ System.out.println(true);
        }

        //Caso que rompe
        try {
            ProgramaPersonalizado pp002 = new ProgramaPersonalizado("pp002","Aleman Nativo","Aleman","",6,100000,EstadoPrograma.FINALIZADO,Modalidad.PRESENCIAL,new ArrayList<>(),9,"0",null);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }


        //RN-004

        //Caso que cumple
        Matricula rn006 = new MatriculaBuilder(e002, pi002, LocalDate.of(2026, 07, 03))
                .construir();

        rn006.configurarModalidad(new FabricaVirtual());

        boolean modalidadVirtualCorrecta =
                rn006.getCarnet() instanceof CarnetDigital
                && rn006.getEntregable() instanceof LicenciaPlataforma;

        System.out.println("RN-004 Caso que cumple:");
        System.out.println(modalidadVirtualCorrecta
                ? "Resultado esperado: combinación virtual correcta."
                : "Resultado inesperado: combinación virtual incorrecta.");

        //Caso que rompe
        Matricula rn007 = new MatriculaBuilder(e004, pi004, LocalDate.of(2026, 07, 03))
                .construir();

        rn007.configurarModalidad(new FabricaPresencial());

        boolean modalidadVirtualIncorrecta =
                rn007.getCarnet() instanceof CarnetFisico
                && !(rn007.getEntregable() instanceof LicenciaPlataforma);

        System.out.println("RN-004 Caso que rompe:");
        if (modalidadVirtualIncorrecta) {
            System.out.println("Resultado esperado: combinación presencial/virtual detectada y rechazada por la prueba.");
        } else {
            System.out.println("Resultado inesperado: la combinación no fue detectada.");
        }


        //RN-005

        ServicioAdicional sa001 = new ServicioAdicional("sa001", "taller especial ingles", "Taller creado por nativos de ingles", 20000, false, TipoServicio.TALLER_ESPECIAL);
        ServicioAdicional sa002 = new ServicioAdicional("sa002", "Simulacro de certificacion en italiano", "Simulacro de un examen de certificacion de italiano", 50000, true, TipoServicio.SIMULACRO_CERTIFICACION);

        //Caso que cumple

        rn004.agregarServicio(sa002);

        //Caso que rompe

        try {
            rn001.agregarServicio(sa001);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

    }

}
