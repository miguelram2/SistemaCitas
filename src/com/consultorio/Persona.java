package com.consultorio;

/**
 * Clase abstracta Persona
 * Base común para Doctor, Paciente y Administrador.
 */
public abstract class Persona implements Persistible {

    protected String id;
    protected String nombreCompleto;

    public Persona(String id, String nombreCompleto) {
        this.id = id;
        this.nombreCompleto = nombreCompleto;
    }

    public String getId()             { return id; }
    public String getNombreCompleto() { return nombreCompleto; }

    @Override
    public String toString() {
        return id + " - " + nombreCompleto;
    }
}
