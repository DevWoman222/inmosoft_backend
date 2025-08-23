package com.InmoSoft.InmoSoft_Proyect.model;

import jakarta.persistence.*;

@Entity
@Table(name = "propietarios")
public class PropietariosEntity {

        //Atributos

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id_propietario", nullable = false)
        private Long idPropietario;

        @Column(name = "primer_nombre", nullable = false, length = 25 )
        private String primerNombre;

        @Column(name = "segundo_nombre", length = 25)
        private String segundoNombre;

        @Column(name = "primer_apellido", length = 25)
        private String primerApellido;

        @Column(name = "segundo_apellido", length = 25)
        private String SegundoApellido;

        @Column (nullable = false)
        private String contraseña;

        @Column (nullable = false, length = 25)
        private String cedula;

        @Column (nullable = false, length = 200)
        private String email;

        @Column (nullable = false, length = 20)
        private String telefono;

        @Column(name = "rol", nullable = false)
        private String rol;

        @Column(nullable = false)
        private boolean estado;


        // Constructor


        public PropietariosEntity(Long idPropietario, String primerNombre, String segundoNombre, String primerApellido, String contraseña, String cedula, String email, String telefono, String rol, boolean estado) {
                this.idPropietario = idPropietario;
                this.primerNombre = primerNombre;
                this.segundoNombre = segundoNombre;
                this.primerApellido = primerApellido;
                this.contraseña = contraseña;
                this.cedula = cedula;
                this.email = email;
                this.telefono = telefono;
                this.rol = rol;
                this.estado = estado;
        }

        public PropietariosEntity() {
        }

        //Getter and setter


        public String getRol() {
                return rol;
        }

        public void setRol(String rol) {
                this.rol = rol;
        }

        public Long getIdPropietario() {
                return idPropietario;
        }

        public void setIdPropietario(Long idPropietario) {
                this.idPropietario = idPropietario;
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
                return SegundoApellido;
        }

        public void setSegundoApellido(String getSegundoApellido) {
                this.SegundoApellido = getSegundoApellido;
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

        public String getTelefono() {
                return telefono;
        }

        public void setTelefono(String telefono) {
                this.telefono = telefono;
        }

        public boolean isEstado() {
                return estado;
        }

        public void setEstado(boolean estado) {
                this.estado = estado;
        }
}
