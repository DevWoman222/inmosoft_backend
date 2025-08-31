package com.InmoSoft.InmoSoft_Proyect.model;

import jakarta.persistence.*;

@Entity
@Table(name = "temporalidades")
public class TemporalidadesEntity {

    //Atributos

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_temporalidad")
    private Long idTemporalidad;

    private int disponibilidad;

    private boolean fijo;


    // Constructor

    public TemporalidadesEntity(Long idTempo, int disponibilidad, boolean fijo) {
        this.idTemporalidad = idTempo;
        this.disponibilidad = disponibilidad;
        this.fijo = fijo;
    }

    public TemporalidadesEntity() {
    }


    //getter and setter


    public Long getIdTemporalidad() {
        return idTemporalidad;
    }

    public void setIdTemporalidad(Long idTemporalidad) {
        this.idTemporalidad = idTemporalidad;
    }

    public int getDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(int disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    public boolean isFijo() {
        return fijo;
    }

    public void setFijo(boolean fijo) {
        this.fijo = fijo;
    }
}



