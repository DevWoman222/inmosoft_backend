package com.InmoSoft.InmoSoft_Proyect.model.DTO;

import java.util.Date;

// DTO para crear o actualizar un contrato
public class ContratosDTO {
    private Long idUsuario;
    private Long idPropiedad;
    private Date fechaInicio;
    private Date fechaFin;
    private double valor;
    private String pdfUrl;

    // Getters, Setters y Constructores

    public ContratosDTO(Long idUsuario, Long idPropiedad, Date fechaInicio, Date fechaFin, double valor, String pdfUrl) {
        this.idUsuario = idUsuario;
        this.idPropiedad = idPropiedad;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.valor = valor;
        this.pdfUrl = pdfUrl;
    }

    public ContratosDTO() {
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

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getPdfUrl() {
        return pdfUrl;
    }

    public void setPdfUrl(String pdfUrl) {
        this.pdfUrl = pdfUrl;
    }

    // ...
}