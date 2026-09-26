package com.uniquindio.parcial1p2;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.uniquindio.parcial1p2.modelo.Academia;
import com.uniquindio.parcial1p2.modelo.Estudiante;
import com.uniquindio.parcial1p2.modelo.Matricula;
import com.uniquindio.parcial1p2.modelo.ServicioAdicional;
import com.uniquindio.parcial1p2.modelo.enums.EstadoPrograma;
import com.uniquindio.parcial1p2.modelo.enums.Modalidad;
import com.uniquindio.parcial1p2.modelo.enums.TipoServicio;
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
        Matricula rn001 = new Matricula(0, e001, pi001, LocalDate.of(2026, 07, 03));
        linguaplus.registrarMatricula(rn001);

        //Caso Que Cumple
        rn001.setDescuento(20);
        System.out.println(rn001.getDescuento()+"%");


        //Caso Que Rompe
        //rn001.setDescuento(35);

        //RN-002

        Estudiante e002 = new Estudiante("Roberto","1099282937","31238761287","roberto@correo.com",12,LocalDate.now());
        ProgramaIntensivo pi002  = new ProgramaIntensivo("pi002", "Ingles B1", "Ingles", "", 2, 50000, EstadoPrograma.ACTIVO, Modalidad.VIRTUAL, new ArrayList<>());
        Estudiante e003 = new Estudiante("Roberto","1099282937","31238761287","roberto@correo.com",12,LocalDate.now());
        ProgramaBasico pi003 = new ProgramaBasico("pb001", "Frances 2", "Frances", "", 2, 50000, EstadoPrograma.ACTIVO, Modalidad.VIRTUAL, new ArrayList<>());
        Estudiante e004 = new Estudiante("Roberto","1099282937","31238761287","roberto@correo.com",12,LocalDate.now());
        ProgramaIntensivo pi004 = new ProgramaIntensivo("pi004", "Mandarin 2027", "Mandarin", "", 2, 50000, EstadoPrograma.ACTIVO, Modalidad.VIRTUAL, new ArrayList<>());

        Matricula rn002 = new Matricula(0, e002, pi003, LocalDate.of(2026, 07, 03));
        Matricula rn003 = new Matricula(0, e002, pi003, LocalDate.of(2026, 07, 03));
        Matricula rn004 = new Matricula(0, e002, pi003, LocalDate.of(2026, 07, 03));

        //Caso que cumple
        System.out.println(rn002.getNumeroMatricula());
        System.out.println(rn003.getNumeroMatricula()); 
        System.out.println(rn004.getNumeroMatricula());

        //Caso que rompe
        Matricula rn005 = new Matricula(2, e002, pi003, LocalDate.of(2026, 07, 03));


        //RN-003

        //Caso que cumple

        ProgramaPersonalizado pp001 = new ProgramaPersonalizado("pp001","Japones Deluxe","Japones","",6,100000,EstadoPrograma.SUSPENDIDO,Modalidad.PRESENCIAL,new ArrayList<>(),9,"0","Ir a todas las clases puntualmente");
        if(pp001!=null){ System.out.println(true);
        }

        //Caso que rompe
        //ProgramaPersonalizado pp002 = new ProgramaPersonalizado("pp002","Aleman Nativo","Aleman","",6,100000,EstadoPrograma.FINALIZADO,Modalidad.PRESENCIAL,new ArrayList<>(),9,"0",null);


        //RN-004
        
        //Caso que cumple
        System.out.println(rn001.getCarnet());
        //rn001.getEntregable().entregar();

        //Caso que rompe


        //RN-005

        ServicioAdicional sa001 = new ServicioAdicional("sa001", "taller especial ingles", "Taller creado por nativos de ingles", 20000, false, TipoServicio.TALLER_ESPECIAL);
        ServicioAdicional sa002 = new ServicioAdicional("sa002", "Simulacro de certificacion en italiano", "Simulacro de un examen de certificacion de italiano", 50000, true, TipoServicio.SIMULACRO_CERTIFICACION);

        //Caso que cumple

        rn004.agregarServicio(sa002);

        //Caso que rompe

        rn001.agregarServicio(sa001);

        

        
    }

}
