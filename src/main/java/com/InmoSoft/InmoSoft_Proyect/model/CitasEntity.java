package com.InmoSoft.InmoSoft_Proyect.model;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.Date;

// Entidad para la tabla 'citas'
@Entity
@Table(name = "citas")
public class CitasEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_citas")
    private Long idCitas;

    @Column(name = "id_usuario")
    private Long idUsuario;

    @Column(name = "id_propiedad")
    private Long idPropiedad;

    @Column(name = "fecha")
    private Date fecha;

    @Column(name = "hora")
    private LocalTime hora; // Se puede usar LocalTime, pero String es más sencillo para empezar.

    @Column(name = "estado")
    private String estado; // "agendada", "cancelada"

    // Getters y Setters

    public Long getIdCitas() {
        return idCitas;
    }

    public void setIdCitas(Long idCitas) {
        this.idCitas = idCitas;
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

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
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