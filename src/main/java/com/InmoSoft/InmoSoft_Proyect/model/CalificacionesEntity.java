package com.InmoSoft.InmoSoft_Proyect.model;

import jakarta.persistence.*;

@Entity
@Table(name = "calificaciones")
public class CalificacionesEntity {

    //Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_calificaciones", nullable = false)
    private Long idCalificaciones;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private UsuariosEntity usuario;

    @ManyToOne
    @JoinColumn(name = "id_propiedad", nullable = false)
    private PropiedadesEntity propiedad;

    private int calificaciones;

    @Column(nullable = false, length = 200)
    private String comentario;

    //Constructor

    public CalificacionesEntity(Long idCalificaciones, UsuariosEntity usuario, PropiedadesEntity propiedad, int calificaciones, String comentario) {
        this.idCalificaciones = idCalificaciones;
        this.usuario = usuario;
        this.propiedad = propiedad;
        this.calificaciones = calificaciones;
        this.comentario = comentario;
    }

    public CalificacionesEntity() {
    }

    //Getter and setter

    public Long getIdCalificaciones() {
        return idCalificaciones;
    }

    public void setIdCalificaciones(Long idCalificaciones) {
        this.idCalificaciones = idCalificaciones;
    }

    public UsuariosEntity getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuariosEntity usuario) {
        this.usuario = usuario;
    }

    public PropiedadesEntity getPropiedad() {
        return propiedad;
    }

    public void setPropiedad(PropiedadesEntity propiedad) {
        this.propiedad = propiedad;
    }

    public int getCalificaciones() {
        return calificaciones;
    }

    public void setCalificaciones(int calificaciones) {
        this.calificaciones = calificaciones;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
}
