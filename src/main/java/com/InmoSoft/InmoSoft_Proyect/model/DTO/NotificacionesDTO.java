package com.InmoSoft.InmoSoft_Proyect.model.DTO;

// DTO para enviar una notificación
public class NotificacionesDTO {
    private Long idUsuario;
    private String mensaje;
    private String tipo;
    private String receptor;

    // Getters, Setters y Constructores

    public NotificacionesDTO(Long idUsuario, String mensaje, String tipo, String receptor) {
        this.idUsuario = idUsuario;
        this.mensaje = mensaje;
        this.tipo = tipo;
        this.receptor = receptor;
    }

    public NotificacionesDTO() {
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

    // ...
}