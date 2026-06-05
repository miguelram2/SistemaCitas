package com.consultorio;

/**
 * Clase Paciente
 * Extiende Persona. Representa a un paciente del consultorio.
 */
public class Paciente extends Persona {

    public Paciente(String id, String nombreCompleto) {
        super(id, nombreCompleto);
    }

    @Override
    public void guardarCSV() {
        GestorCSV.escribir(GestorCSV.PACIENTES, id + "," + nombreCompleto);
    }

    @Override
    public String toString() {
        return id + " - " + nombreCompleto;
    }
}