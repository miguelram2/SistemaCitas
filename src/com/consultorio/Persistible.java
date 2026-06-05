package com.consultorio;

/**
 * Interfaz Persistible
 * Contrato que obliga a cada entidad a implementar su propio guardado en CSV.
 */
public interface Persistible {
    void guardarCSV();
}