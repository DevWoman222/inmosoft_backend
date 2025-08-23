package com.InmoSoft.InmoSoft_Proyect.model;

import jakarta.persistence.*;

@Entity
@Table (name = "usuarios")
public class UsuariosEntity {

    //Atributos-----

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario", nullable = false)
    private Long idUsuario;

    @Column(name = "primer_nombre", nullable = false, length = 25)
    private String primerNombre;

    @Column(name = "segundo_nombre", length = 25)
    private String segundoNombre;

    @Column(name = "primer_apellido", nullable = false, length = 25)
    private String primerApellido;

    @Column(name = "segundo_apellido", length = 25)
    private String segundoApellido;

    @Column(nullable = false)
    private String contraseña;

    @Column(length = 25)
    private String cedula;

    @Column(nullable = false)
    private String email;

    private int telefono;

    @Column(name = "rol", nullable = false)
    private String rol;

    private boolean estado;

    //Constructor-----

    public UsuariosEntity(Long idUsuario, String primerNombre, String segundoNombre, String primerApellido, String segundoApellido, String contraseña, String cedula, String email, int telefono, String rol, boolean estado) {
        this.idUsuario = idUsuario;
        this.primerNombre = primerNombre;
        this.segundoNombre = segundoNombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.contraseña = contraseña;
        this.cedula = cedula;
        this.email = email;
        this.telefono = telefono;
        this.rol = rol;
        this.estado = estado;
    }

    public UsuariosEntity() {
    }

    //Getter and setter-----


    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getPrimerNombre() {
        return primerNombre;
    }

    public void setPrimerNombre(String primerNombre) {
        this.primerNombre = primerNombre;
    }

    public String getSegundoNombre() {
        return segundoNombre;
    }

    public void setSegundoNombre(String segundoNombre) {
        this.segundoNombre = segundoNombre;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
}
