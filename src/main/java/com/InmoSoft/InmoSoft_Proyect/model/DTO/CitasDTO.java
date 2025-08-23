package com.InmoSoft.InmoSoft_Proyect.model.DTO;

import java.util.Date;

// DTO para crear o agendar una cita
public class CitasDTO {
    private Long idUsuario;
    private Long idPropiedad;
    private Date fecha;
    private String hora;
    private String estado; // Opcional, el servicio podría asignarlo

    // Getters, Setters y Constructores

    public CitasDTO(Long idUsuario, Long idPropiedad, Date fecha, String hora, String estado) {
        this.idUsuario = idUsuario;
        this.idPropiedad = idPropiedad;
        this.fecha = fecha;
        this.hora = hora;
        this.estado = estado;
    }

    public CitasDTO() {
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Long getIdPropiedad() {
        return idPropiedad;
    }

    public void setIdPropiedad(Long idPropiedad) {
        this.idPropiedad = idPropiedad;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    // ...
}