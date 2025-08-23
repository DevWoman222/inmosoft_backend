package com.InmoSoft.InmoSoft_Proyect.model;

import jakarta.persistence.*;

import java.util.Date;

// Entidad para la tabla 'notificaciones'
@Entity
@Table(name = "notificaciones")
public class NotificacionesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_noti")
    private Long idNoti;

    @Column(name = "id_usuario")
    private Long idUsuario;

    @Column(name = "mensaje")
    private String mensaje;

    @Column(name = "tipo")
    private String tipo; // "cita", "recordatorio"

    @Column(name = "receptor")
    private String receptor; // Se puede usar el correo o el teléfono

    @Column(name = "leido")
    private boolean leido;

    @Column(name = "fecha_creacion")
    private Date fechaCreacion;

    // Getters y Setters

    public Long getIdNoti() {
        return idNoti;
    }

    public void setIdNoti(Long idNoti) {
        this.idNoti = idNoti;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getReceptor() {
        return receptor;
    }

    public void setReceptor(String receptor) {
        this.receptor = receptor;
    }

    public boolean isLeido() {
        return leido;
    }

    public void setLeido(boolean leido) {
        this.leido = leido;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }


    // ...
}
