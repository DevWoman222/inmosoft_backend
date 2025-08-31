package com.InmoSoft.InmoSoft_Proyect.model;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "administradores")
public class AdministradoresEntity {

    //Atributos-----

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_administrador")
    private Long idAdministrador;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(length = 15, nullable = false)
    private String telefono;

    @Column(length = 25)
    private String nit;

    @Column(nullable = false)
    private String contraseña;

    @Column(nullable = false)
    private String direccion;

    @Column(name = "nombre_comercial", nullable = false)
    private String nombreComercial;

    @Column(name = "nombre_representante")
    private String nombreRepresentante;

    @Column(nullable = false)
    private boolean estado;

    @CreatedDate
    @Column(name = "fecha_registro", nullable = false, updatable = false)
    private LocalDateTime fechaRegistro;

    @Column(name = "rol", nullable = false)
    private String rol;

    //Constructor-----

    public AdministradoresEntity() {
    }

    public AdministradoresEntity(Long idAdministrador, String email, String telefono, String nit, String contraseña, String direccion, String nombreComercial, String nombreRepresentante, boolean estado, LocalDateTime fechaRegistro, String rol) {
        this.idAdministrador = idAdministrador;
        this.email = email;
        this.telefono = telefono;
        this.nit = nit;
        this.contraseña = contraseña;
        this.direccion = direccion;
        this.nombreComercial = nombreComercial;
        this.nombreRepresentante = nombreRepresentante;
        this.estado = estado;
        this.fechaRegistro = fechaRegistro;
        this.rol = rol;
    }

    // Getters y Setters


    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public Long getIdAdministrador() {
        return idAdministrador;
    }

    public void setIdAdministrador(Long idAdministrador) {
        this.idAdministrador = idAdministrador;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getNombreComercial() {
        return nombreComercial;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    public String getNombreRepresentante() {
        return nombreRepresentante;
    }

    public void setNombreRepresentante(String nombreRepresentante) {
        this.nombreRepresentante = nombreRepresentante;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
}
