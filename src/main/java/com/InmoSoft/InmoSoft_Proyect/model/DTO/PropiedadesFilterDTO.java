package com.InmoSoft.InmoSoft_Proyect.model.DTO;

import com.InmoSoft.InmoSoft_Proyect.model.Imagen;

public class PropiedadesFilterDTO {

    private Long idPropiedad;

    private int precio;

    private String ciudad;

    private String sector;

    private String codigo;

    private String estado;

    private Imagen imagen;

    public PropiedadesFilterDTO() {
    }

    public PropiedadesFilterDTO(Long idPropiedad, int precio, String ciudad, String sector, String codigo, String estado, Imagen imagen) {
        this.idPropiedad = idPropiedad;
        this.precio = precio;
        this.ciudad = ciudad;
        this.sector = sector;
        this.codigo = codigo;
        this.estado = estado;
        this.imagen = imagen;


    }

    public Long getIdPropiedad() {
        return idPropiedad;
    }

    public void setIdPropiedad(Long idPropiedad) {
        this.idPropiedad = idPropiedad;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Imagen getImagen() {
        return imagen;
    }

    public void setImagen(Imagen imagen) {
        this.imagen = imagen;
    }
}
