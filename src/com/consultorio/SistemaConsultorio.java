package com.consultorio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase SistemaConsultorio
 * Controlador principal. Gestiona el flujo del programa,
 * los menús y las listas en memoria de doctores, pacientes y citas.
 */
public class SistemaConsultorio {

    private List<Doctor>       doctores  = new ArrayList<>();
    private List<Paciente>     pacientes = new ArrayList<>();
    private List<Cita>         citas     = new ArrayList<>();
    private Administrador      admin;
    private BufferedReader     br        = new BufferedReader(new InputStreamReader(System.in));

    // ══════════════════════════════════════════
    // INICIO Y CARGA DE DATOS
    // ══════════════════════════════════════════

    /**
     * Punto de entrada principal del sistema.
     * Inicializa archivos, carga datos y lanza el login.
     */
    public void iniciar() {
        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║   SISTEMA DE CITAS – CONSULTORIO     ║");
        System.out.println("╚══════════════════════════════════════╝");

        GestorCSV.inicializar();
        cargarDatos();
        login();
    }

    /**
     * Carga todos los datos desde los archivos CSV a las listas en memoria.
     */
    private void cargarDatos() {
        // Cargar administrador
        List<String[]> admins = GestorCSV.leer(GestorCSV.ADMINS);
        if (!admins.isEmpty()) {
            String[] a = admins.get(0);
            admin = new Administrador(a[0].trim(), a[1].trim(), a[2].trim());
        }

        // Cargar doctores
        for (String[] fila : GestorCSV.leer(GestorCSV.DOCTORES)) {
            if (fila.length >= 3) {
                doctores.add(new Doctor(fila[0].trim(), fila[1].trim(), fila[2].trim()));
            }
        }

        // Cargar pacientes
        for (String[] fila : GestorCSV.leer(GestorCSV.PACIENTES)) {
            if (fila.length >= 2) {
                pacientes.add(new Paciente(fila[0].trim(), fila[1].trim()));
            }
        }

        // Cargar citas (relacionar con doctor y paciente ya cargados)
        for (String[] fila : GestorCSV.leer(GestorCSV.CITAS)) {
            if (fila.length >= 5) {
                Doctor  d = buscarDoctor(fila[3].trim());
                Paciente p = buscarPaciente(fila[4].trim());
                if (d != null && p != null) {
                    citas.add(new Cita(fila[0].trim(), fila[1].trim(),
                            fila[2].trim(), d, p));
                }
            }
        }

        System.out.println("Datos cargados: " + doctores.size() + " doctores, "
                + pacientes.size() + " pacientes, " + citas.size() + " citas.\n");
    }

    // ══════════════════════════════════════════
    // CONTROL DE ACCESO
    // ══════════════════════════════════════════

    /**
     * Solicita credenciales hasta que sean correctas.
     */
    private void login() {
        boolean autenticado = false;
        int intentos = 0;

        while (!autenticado) {
            try {
                System.out.print("Usuario: ");
                String id   = br.readLine().trim();
                System.out.print("Contraseña: ");
                String pass = br.readLine().trim();

                if (admin != null && admin.autenticar(id, pass)) {
                    autenticado = true;
                    System.out.println("\n¡Bienvenido, " + admin.getNombreCompleto() + "!\n");
                    mostrarMenu();
                } else {
                    intentos++;
                    System.out.println("Credenciales incorrectas. Intento " + intentos + "/3\n");
                    if (intentos >= 3) {
                        System.out.println("Demasiados intentos fallidos. El sistema se cerrará.");
                        return;
                    }
                }
            } catch (IOException e) {
                System.out.println("Error al leer entrada: " + e.getMessage());
            }
        }
    }

    // ══════════════════════════════════════════
    // MENÚ PRINCIPAL
    // ══════════════════════════════════════════

    private void mostrarMenu() {
        int opcion = -1;
        while (opcion != 0) {
            System.out.println("╔══════════════════════════════════════╗");
            System.out.println("║            MENÚ PRINCIPAL            ║");
            System.out.println("╠══════════════════════════════════════╣");
            System.out.println("║  1. Gestión de doctores              ║");
            System.out.println("║  2. Gestión de pacientes             ║");
            System.out.println("║  3. Gestión de citas                 ║");
            System.out.println("║  0. Salir                            ║");
            System.out.println("╚══════════════════════════════════════╝");
            System.out.print("Opción: ");

            try {
                opcion = Integer.parseInt(br.readLine().trim());
                switch (opcion) {
                    case 1 -> menuDoctores();
                    case 2 -> menuPacientes();
                    case 3 -> menuCitas();
                    case 0 -> System.out.println("¡Hasta pronto!");
                    default -> System.out.println("Opción inválida.\n");
                }
            } catch (NumberFormatException e) {
                System.out.println("Ingresa un número válido.\n");
            } catch (IOException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    // ══════════════════════════════════════════
    // MÓDULO DOCTORES
    // ══════════════════════════════════════════

    private void menuDoctores() throws IOException {
        int opcion = -1;
        while (opcion != 0) {
            System.out.println("\n── Doctores ──────────────────────────");
            System.out.println("1. Listar doctores");
            System.out.println("2. Registrar doctor");
            System.out.println("3. Eliminar doctor");
            System.out.println("0. Volver");
            System.out.print("Opción: ");
            try {
                opcion = Integer.parseInt(br.readLine().trim());
                switch (opcion) {
                    case 1 -> listarDoctores();
                    case 2 -> registrarDoctor();
                    case 3 -> eliminarDoctor();
                    case 0 -> System.out.println();
                    default -> System.out.println("Opción inválida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Ingresa un número válido.");
            }
        }
    }

    private void listarDoctores() {
        if (doctores.isEmpty()) { System.out.println("No hay doctores registrados.\n"); return; }
        System.out.println("\nDoctores registrados:");
        for (Doctor d : doctores) System.out.println("  " + d);
        System.out.println();
    }

    private void registrarDoctor() throws IOException {
        System.out.print("ID del doctor: ");
        String id = br.readLine().trim();
        if (GestorCSV.existe(id, GestorCSV.DOCTORES)) {
            System.out.println("Ya existe un doctor con ese ID.\n"); return;
        }
        System.out.print("Nombre completo: ");
        String nombre = br.readLine().trim();
        System.out.print("Especialidad: ");
        String esp = br.readLine().trim();

        if (id.isEmpty() || nombre.isEmpty() || esp.isEmpty()) {
            System.out.println("Todos los campos son obligatorios.\n"); return;
        }

        Doctor d = new Doctor(id, nombre, esp);
        d.guardarCSV();
        doctores.add(d);
        System.out.println("Doctor registrado correctamente.\n");
    }

    private void eliminarDoctor() throws IOException {
        listarDoctores();
        System.out.print("ID del doctor a eliminar: ");
        String id = br.readLine().trim();
        Doctor encontrado = buscarDoctor(id);
        if (encontrado == null) { System.out.println("Doctor no encontrado.\n"); return; }

        System.out.print("¿Confirmas eliminar a '" + encontrado.getNombreCompleto() + "'? (s/n): ");
        if (!br.readLine().trim().equalsIgnoreCase("s")) { System.out.println("Cancelado.\n"); return; }

        doctores.remove(encontrado);
        List<String> lineas = new ArrayList<>();
        for (Doctor d : doctores) lineas.add(d.getId() + "," + d.getNombreCompleto() + "," + d.getEspecialidad());
        GestorCSV.sobreescribir(GestorCSV.DOCTORES, lineas);
        System.out.println("Doctor eliminado.\n");
    }

    // ══════════════════════════════════════════
    // MÓDULO PACIENTES
    // ══════════════════════════════════════════

    private void menuPacientes() throws IOException {
        int opcion = -1;
        while (opcion != 0) {
            System.out.println("\n── Pacientes ─────────────────────────");
            System.out.println("1. Listar pacientes");
            System.out.println("2. Registrar paciente");
            System.out.println("3. Eliminar paciente");
            System.out.println("0. Volver");
            System.out.print("Opción: ");
            try {
                opcion = Integer.parseInt(br.readLine().trim());
                switch (opcion) {
                    case 1 -> listarPacientes();
                    case 2 -> registrarPaciente();
                    case 3 -> eliminarPaciente();
                    case 0 -> System.out.println();
                    default -> System.out.println("Opción inválida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Ingresa un número válido.");
            }
        }
    }

    private void listarPacientes() {
        if (pacientes.isEmpty()) { System.out.println("No hay pacientes registrados.\n"); return; }
        System.out.println("\nPacientes registrados:");
        for (Paciente p : pacientes) System.out.println("  " + p);
        System.out.println();
    }

    private void registrarPaciente() throws IOException {
        System.out.print("ID del paciente: ");
        String id = br.readLine().trim();
        if (GestorCSV.existe(id, GestorCSV.PACIENTES)) {
            System.out.println("Ya existe un paciente con ese ID.\n"); return;
        }
        System.out.print("Nombre completo: ");
        String nombre = br.readLine().trim();

        if (id.isEmpty() || nombre.isEmpty()) {
            System.out.println("Todos los campos son obligatorios.\n"); return;
        }

        Paciente p = new Paciente(id, nombre);
        p.guardarCSV();
        pacientes.add(p);
        System.out.println("Paciente registrado correctamente.\n");
    }

    private void eliminarPaciente() throws IOException {
        listarPacientes();
        System.out.print("ID del paciente a eliminar: ");
        String id = br.readLine().trim();
        Paciente encontrado = buscarPaciente(id);
        if (encontrado == null) { System.out.println("Paciente no encontrado.\n"); return; }

        System.out.print("¿Confirmas eliminar a '" + encontrado.getNombreCompleto() + "'? (s/n): ");
        if (!br.readLine().trim().equalsIgnoreCase("s")) { System.out.println("Cancelado.\n"); return; }

        pacientes.remove(encontrado);
        List<String> lineas = new ArrayList<>();
        for (Paciente p : pacientes) lineas.add(p.getId() + "," + p.getNombreCompleto());
        GestorCSV.sobreescribir(GestorCSV.PACIENTES, lineas);
        System.out.println("Paciente eliminado.\n");
    }

    // ══════════════════════════════════════════
    // MÓDULO CITAS
    // ══════════════════════════════════════════

    private void menuCitas() throws IOException {
        int opcion = -1;
        while (opcion != 0) {
            System.out.println("\n── Citas ─────────────────────────────");
            System.out.println("1. Listar citas");
            System.out.println("2. Crear cita");
            System.out.println("3. Eliminar cita");
            System.out.println("0. Volver");
            System.out.print("Opción: ");
            try {
                opcion = Integer.parseInt(br.readLine().trim());
                switch (opcion) {
                    case 1 -> listarCitas();
                    case 2 -> crearCita();
                    case 3 -> eliminarCita();
                    case 0 -> System.out.println();
                    default -> System.out.println("Opción inválida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Ingresa un número válido.");
            }
        }
    }

    private void listarCitas() {
        if (citas.isEmpty()) { System.out.println("No hay citas registradas.\n"); return; }
        System.out.println("\nCitas registradas:");
        for (Cita c : citas) System.out.println("  " + c);
        System.out.println();
    }

    private void crearCita() throws IOException {
        if (doctores.isEmpty())  { System.out.println("Registra al menos un doctor primero.\n");  return; }
        if (pacientes.isEmpty()) { System.out.println("Registra al menos un paciente primero.\n"); return; }

        System.out.print("ID de la cita: ");
        String id = br.readLine().trim();
        if (GestorCSV.existe(id, GestorCSV.CITAS)) {
            System.out.println("Ya existe una cita con ese ID.\n"); return;
        }

        System.out.print("Fecha y hora (yyyy-MM-dd HH:mm): ");
        String fechaHora = br.readLine().trim();

        System.out.print("Motivo de la cita: ");
        String motivo = br.readLine().trim();

        // Seleccionar doctor
        listarDoctores();
        System.out.print("ID del doctor: ");
        String idDoc = br.readLine().trim();
        Doctor doctor = buscarDoctor(idDoc);
        if (doctor == null) { System.out.println("Doctor no encontrado.\n"); return; }

        // Seleccionar paciente
        listarPacientes();
        System.out.print("ID del paciente: ");
        String idPac = br.readLine().trim();
        Paciente paciente = buscarPaciente(idPac);
        if (paciente == null) { System.out.println("Paciente no encontrado.\n"); return; }

        Cita cita = new Cita(id, fechaHora, motivo, doctor, paciente);
        cita.guardarCSV();
        citas.add(cita);
        System.out.println("Cita registrada correctamente.\n");
    }

    private void eliminarCita() throws IOException {
        listarCitas();
        System.out.print("ID de la cita a eliminar: ");
        String id = br.readLine().trim();
        Cita encontrada = buscarCita(id);
        if (encontrada == null) { System.out.println("Cita no encontrada.\n"); return; }

        System.out.print("¿Confirmas eliminar la cita '" + id + "'? (s/n): ");
        if (!br.readLine().trim().equalsIgnoreCase("s")) { System.out.println("Cancelado.\n"); return; }

        citas.remove(encontrada);
        List<String> lineas = new ArrayList<>();
        for (Cita c : citas)
            lineas.add(c.getIdCita() + "," + c.getFechaHora() + "," + c.getMotivo()
                    + "," + c.getDoctor().getId() + "," + c.getPaciente().getId());
        GestorCSV.sobreescribir(GestorCSV.CITAS, lineas);
        System.out.println("Cita eliminada.\n");
    }

    // ══════════════════════════════════════════
    // BÚSQUEDAS EN LISTAS
    // ══════════════════════════════════════════

    public Doctor buscarDoctor(String id) {
        for (Doctor d : doctores) if (d.getId().equals(id)) return d;
        return null;
    }

    public Paciente buscarPaciente(String id) {
        for (Paciente p : pacientes) if (p.getId().equals(id)) return p;
        return null;
    }

    public Cita buscarCita(String id) {
        for (Cita c : citas) if (c.getIdCita().equals(id)) return c;
        return null;
    }
}