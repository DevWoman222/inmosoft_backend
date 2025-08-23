package com.InmoSoft.InmoSoft_Proyect.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;


@Entity
@Table(name = "imagen")
public class Imagen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_imagen", nullable = false)
    private Long idImagen;

    @Column(name = "nombre_original", nullable = false)
    private String nombreOriginal;

    @Column(name = "key_s3", nullable = false, unique = true)
    private String keyS3;

    @ManyToOne
    @JoinColumn(name = "id_propiedad", nullable = false)
    @JsonBackReference  // Evita recursión infinita
    private PropiedadesEntity propiedad;

    // Constructores

    public Imagen() {
    }

    public Imagen(Long idImagen, String nombreOriginal, String keyS3, PropiedadesEntity propiedad) {
        this.idImagen = idImagen;
        this.nombreOriginal = nombreOriginal;
        this.keyS3 = keyS3;
        this.propiedad = propiedad;
    }
// Getters y Setters


    public Long getIdImagen() {
        return idImagen;
    }

    public void setIdImagen(Long idImagen) {
        this.idImagen = idImagen;
    }

    public String getNombreOriginal() {
        return nombreOriginal;
    }

    public void setNombreOriginal(String nombreOriginal) {
        this.nombreOriginal = nombreOriginal;
    }

    public String getKeyS3() {
        return keyS3;
    }

    public void setKeyS3(String keyS3) {
        this.keyS3 = keyS3;
    }

    public PropiedadesEntity getIdPropiedad() {
        return propiedad;
    }

    public PropiedadesEntity getPropiedad() {
        return propiedad;
    }

    public void setPropiedad(PropiedadesEntity propiedad) {
        this.propiedad = propiedad;
    }
}