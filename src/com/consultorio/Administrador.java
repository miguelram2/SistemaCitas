package com.consultorio;

/**
 * Clase Administrador
 * Extiende Persona. Agrega contraseña y autenticación.
 */
public class Administrador extends Persona {

    private String contrasena;

    public Administrador(String id, String nombreCompleto, String contrasena) {
        super(id, nombreCompleto);
        this.contrasena = contrasena;
    }

    /**
     * Verifica si el ID y contraseña ingresados coinciden con los del administrador.
     */
    public boolean autenticar(String idIngresado, String passIngresado) {
        return this.id.equals(idIngresado) && this.contrasena.equals(passIngresado);
    }

    @Override
    public void guardarCSV() {
        GestorCSV.escribir(GestorCSV.ADMINS, id + "," + nombreCompleto + "," + contrasena);
    }

    @Override
    public String toString() {
        return id + " - " + nombreCompleto;
    }
}