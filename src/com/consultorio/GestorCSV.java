package com.consultorio;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase GestorCSV
 * Centraliza todas las operaciones de lectura y escritura de archivos CSV.
 * Los archivos se almacenan en la carpeta db/.
 */
public class GestorCSV {

    // Rutas de los archivos CSV en la carpeta db/
    public static final String DOCTORES  = "db/doctores.csv";
    public static final String PACIENTES = "db/pacientes.csv";
    public static final String CITAS     = "db/citas.csv";
    public static final String ADMINS    = "db/administradores.csv";

    /**
     * Verifica que exista la carpeta db/ y todos los archivos CSV.
     * Si no existen los crea vacíos. Si el archivo de admins está vacío,
     * genera un administrador por defecto.
     */
    public static void inicializar() {
        File carpeta = new File("db");
        if (!carpeta.exists()) {
            carpeta.mkdirs();
            System.out.println("Carpeta db/ creada.");
        }

        crearSiNoExiste(DOCTORES);
        crearSiNoExiste(PACIENTES);
        crearSiNoExiste(CITAS);

        // Admins: si no existe o está vacío, crea el admin por defecto
        File adminsFile = new File(ADMINS);
        if (!adminsFile.exists() || adminsFile.length() == 0) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(ADMINS))) {
                bw.write("admin,Administrador,admin123");
                bw.newLine();
                System.out.println("Admin por defecto creado (ID: admin | Pass: admin123).");
            } catch (IOException e) {
                System.out.println("Error al crear administradores.csv: " + e.getMessage());
            }
        }
    }

    private static void crearSiNoExiste(String ruta) {
        File archivo = new File(ruta);
        if (!archivo.exists()) {
            try {
                archivo.createNewFile();
            } catch (IOException e) {
                System.out.println("Error al crear " + ruta + ": " + e.getMessage());
            }
        }
    }

    /**
     * Lee todas las líneas del archivo y las devuelve como lista de arreglos.
     * @param ruta Ruta del archivo CSV.
     * @return Lista de String[], cada arreglo es una línea dividida por comas.
     */
    public static List<String[]> leer(String ruta) {
        List<String[]> filas = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                if (!linea.isEmpty()) {
                    filas.add(linea.split(",", -1));
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer " + ruta + ": " + e.getMessage());
        }
        return filas;
    }

    /**
     * Agrega una línea al final del archivo CSV.
     * @param ruta  Ruta del archivo.
     * @param datos Línea a escribir.
     */
    public static void escribir(String ruta, String datos) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ruta, true))) {
            bw.write(datos);
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error al escribir en " + ruta + ": " + e.getMessage());
        }
    }

    /**
     * Sobreescribe el archivo completo con la lista de líneas proporcionada.
     * Usado al eliminar un registro.
     * @param ruta   Ruta del archivo.
     * @param lineas Lista de líneas a escribir.
     */
    public static void sobreescribir(String ruta, List<String> lineas) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ruta, false))) {
            for (String linea : lineas) {
                bw.write(linea);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al sobreescribir " + ruta + ": " + e.getMessage());
        }
    }

    /**
     * Verifica si ya existe un registro con el ID dado en el archivo.
     * @param id   ID a buscar (primera columna).
     * @param ruta Ruta del archivo CSV.
     * @return true si existe, false si no.
     */
    public static boolean existe(String id, String ruta) {
        for (String[] fila : leer(ruta)) {
            if (fila.length > 0 && fila[0].trim().equals(id)) {
                return true;
            }
        }
        return false;
    }
}