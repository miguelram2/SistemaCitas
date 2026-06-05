package com.consultorio;

/**
 * Clase Cita
 * Representa una cita médica con fecha/hora, motivo, doctor y paciente.
 */
public class Cita implements Persistible {

    private String idCita;
    private String fechaHora;   // formato: yyyy-MM-dd HH:mm
    private String motivo;
    private Doctor doctor;
    private Paciente paciente;

    public Cita(String idCita, String fechaHora, String motivo,
                Doctor doctor, Paciente paciente) {
        this.idCita   = idCita;
        this.fechaHora = fechaHora;
        this.motivo   = motivo;
        this.doctor   = doctor;
        this.paciente = paciente;
    }

    public String getIdCita()    { return idCita;    }
    public String getFechaHora() { return fechaHora; }
    public String getMotivo()    { return motivo;    }
    public Doctor getDoctor()    { return doctor;    }
    public Paciente getPaciente(){ return paciente;  }

    @Override
    public void guardarCSV() {
        GestorCSV.escribir(GestorCSV.CITAS,
                idCita + "," + fechaHora + "," + motivo + ","
                        + doctor.getId() + "," + paciente.getId());
    }

    @Override
    public String toString() {
        return "[" + idCita + "] " + fechaHora + " | " + motivo
                + " | Dr. " + doctor.getNombreCompleto()
                + " | Pac. " + paciente.getNombreCompleto();
    }
}