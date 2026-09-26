package com.uniquindio.parcial1p2.modelo;

import java.util.ArrayList;
import java.util.List;

import com.uniquindio.parcial1p2.modelo.programa.ProgramaFormacion;

public class Academia {

    private static Academia instancia;

    private String nombreComercial;
    private String NIT;
    private String direccion;
    private String telefono;
    private String correoElectronico;
    private String paginaWeb;

    private List<Estudiante> estudiantes;
    private List<Docente> docentes;
    private List<ProgramaFormacion> programas;
    private List<ServicioAdicional> serviciosAdicionales;
    private List<Matricula> matriculas;
    private List<PeriodoAcademico> periodosAcademicos;

    private Academia() {
    estudiantes = new ArrayList<>();
    docentes = new ArrayList<>();
    programas = new ArrayList<>();
    serviciosAdicionales = new ArrayList<>();
    matriculas = new ArrayList<>();
    periodosAcademicos = new ArrayList<>();

    cargarServiciosAdicionales();
}
    public static Academia getInstancia() {
        if (instancia == null) {
            instancia = new Academia();
        }

        return instancia;
    }

    public void registrarEstudiante(Estudiante estudiante) {
        if (estudiante == null) {
            throw new IllegalArgumentException(
                    "El estudiante no puede ser nulo."
            );
        }
        

        if (!estudiantes.contains(estudiante)) {
            estudiantes.add(estudiante);
        }
    }
    public void eliminarEstudiante(Estudiante estudiante) {
    if (estudiante == null) {
        throw new IllegalArgumentException(
                "El estudiante no puede ser nulo."
        );
    }

    estudiantes.remove(estudiante);
}
    public void registrarDocente(Docente docente) {
        if (docente == null) {
            throw new IllegalArgumentException(
                    "El docente no puede ser nulo."
            );
        }

        if (!docentes.contains(docente)) {
            docentes.add(docente);
        }
    }
    public void eliminarDocente(Docente docente) {
    if (docente == null) {
        throw new IllegalArgumentException(
                "El docente no puede ser nulo."
        );
    }

    docentes.remove(docente);
}
    public void registrarPrograma(ProgramaFormacion programa) {
        if (programa == null) {
            throw new IllegalArgumentException(
                    "El programa no puede ser nulo."
            );
        }

        if (!programas.contains(programa)) {
            programas.add(programa);
        }
    }
    public void eliminarPrograma(ProgramaFormacion programa) {
    if (programa == null) {
        throw new IllegalArgumentException(
                "El programa no puede ser nulo."
        );
    }

    programas.remove(programa);
}
    public void registrarServicio(ServicioAdicional servicio) {
        if (servicio == null) {
            throw new IllegalArgumentException(
                    "El servicio no puede ser nulo."
            );
        }

        if (!serviciosAdicionales.contains(servicio)) {
            serviciosAdicionales.add(servicio);
        }
    }
    private void cargarServiciosAdicionales() {

    registrarServicio(
            new ServicioAdicional(
                    "SER001",
                    "Examen de ubicación",
                    "Examen para determinar el nivel de idioma del estudiante.",
                    20000,
                    true,
                    com.uniquindio.parcial1p2.modelo.enums.TipoServicio.EXAMEN_UBICACION
            )
    );

    registrarServicio(
            new ServicioAdicional(
                    "SER002",
                    "Simulacro de certificación",
                    "Simulacro para preparar al estudiante para un examen de certificación.",
                    35000,
                    true,
                    com.uniquindio.parcial1p2.modelo.enums.TipoServicio.SIMULACRO_CERTIFICACION
            )
    );

    registrarServicio(
            new ServicioAdicional(
                    "SER003",
                    "Material de estudio",
                    "Material adicional para reforzar el aprendizaje.",
                    25000,
                    true,
                    com.uniquindio.parcial1p2.modelo.enums.TipoServicio.MATERIAL_ESTUDIO
            )
    );

    registrarServicio(
            new ServicioAdicional(
                    "SER004",
                    "Taller especial",
                    "Taller complementario para fortalecer las habilidades del estudiante.",
                    40000,
                    true,
                    com.uniquindio.parcial1p2.modelo.enums.TipoServicio.TALLER_ESPECIAL
            )
    );
}
    public void eliminarServicio(ServicioAdicional servicio) {
    if (servicio == null) {
        throw new IllegalArgumentException(
                "El servicio no puede ser nulo."
        );
    }

    serviciosAdicionales.remove(servicio);
}
    public void registrarMatricula(Matricula matricula) {
        if (matricula == null) {
            throw new IllegalArgumentException(
                    "La matrícula no puede ser nula."
            );
        }

        if (!matriculas.contains(matricula)) {
            matriculas.add(matricula);
        }
    }

    public void registrarPeriodo(PeriodoAcademico periodo) {
        if (periodo == null) {
            throw new IllegalArgumentException(
                    "El periodo no puede ser nulo."
            );
        }

        if (!periodosAcademicos.contains(periodo)) {
            periodosAcademicos.add(periodo);
        }
    }

    public List<Estudiante> obtenerEstudiantes() {
        return new ArrayList<>(estudiantes);
    }

    public List<Docente> obtenerDocentes() {
        return new ArrayList<>(docentes);
    }

    public List<ProgramaFormacion> obtenerProgramas() {
        return new ArrayList<>(programas);
    }

    public List<ServicioAdicional> obtenerServiciosAdicionales() {
        return new ArrayList<>(serviciosAdicionales);
    }

    public List<Matricula> obtenerMatriculas() {
        return new ArrayList<>(matriculas);
    }

    public List<PeriodoAcademico> obtenerPeriodosAcademicos() {
        return new ArrayList<>(periodosAcademicos);
    }

    public String getNombreComercial() {
        return nombreComercial;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    public String getNIT() {
        return NIT;
    }

    public void setNIT(String NIT) {
        this.NIT = NIT;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getPaginaWeb() {
        return paginaWeb;
    }

    public void setPaginaWeb(String paginaWeb) {
        this.paginaWeb = paginaWeb;
    }
}