package com.InmoSoft.InmoSoft_Proyect.model.DTO;

import com.InmoSoft.InmoSoft_Proyect.model.TemporalidadesEntity;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public class PropiedadesDTO {

    private Long idPropiedad;

    private int precio;

    private String ciudad;

    private String sector;

    private String codigo;

    private String tipoInmueble;

    private String estado;

    private int habitaciones;

    private int baños;

    private boolean parqueadero;

    private boolean cuartoUtil;

    private boolean piscina;

    private boolean vigilancia;

    private boolean balcon;

    private boolean jacuzzi;

    private boolean asensor;

    private boolean condicion;

    private String descripcion;

    @Schema(description = "Latitud de la propiedad", example = "4.710989")
    private Double latitude;

    @Schema(description = "Longitud de la propiedad", example = "-74.072090")
    private Double longitude;

    private boolean disponibilidad;

    private int areaMetros;

    private TemporalidadesEntity temporalidades;

    private Long idPropietario;

    private List<ImagenDTO> imagenes;



    // Getters y Setters


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

    public String getTipoInmueble() {
        return tipoInmueble;
    }

    public void setTipoInmueble(String tipoInmueble) {
        this.tipoInmueble = tipoInmueble;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getHabitaciones() {
        return habitaciones;
    }

    public void setHabitaciones(int habitaciones) {
        this.habitaciones = habitaciones;
    }

    public int getBaños() {
        return baños;
    }

    public void setBaños(int baños) {
        this.baños = baños;
    }

    public boolean isParqueadero() {
        return parqueadero;
    }

    public void setParqueadero(boolean parqueadero) {
        this.parqueadero = parqueadero;
    }

    public boolean isCuartoUtil() {
        return cuartoUtil;
    }

    public void setCuartoUtil(boolean cuartoUtil) {
        this.cuartoUtil = cuartoUtil;
    }

    public boolean isPiscina() {
        return piscina;
    }

    public void setPiscina(boolean piscina) {
        this.piscina = piscina;
    }

    public boolean isVigilancia() {
        return vigilancia;
    }

    public void setVigilancia(boolean vigilancia) {
        this.vigilancia = vigilancia;
    }

    public boolean isBalcon() {
        return balcon;
    }

    public void setBalcon(boolean balcon) {
        this.balcon = balcon;
    }

    public boolean isJacuzzi() {
        return jacuzzi;
    }

    public void setJacuzzi(boolean jacuzzi) {
        this.jacuzzi = jacuzzi;
    }

    public boolean isAsensor() {
        return asensor;
    }

    public void setAsensor(boolean asensor) {
        this.asensor = asensor;
    }

    public boolean isCondicion() {
        return condicion;
    }

    public void setCondicion(boolean condicion) {
        this.condicion = condicion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public boolean isDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(boolean disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    public int getAreaMetros() {
        return areaMetros;
    }

    public void setAreaMetros(int areaMetros) {
        this.areaMetros = areaMetros;
    }

    public TemporalidadesEntity getTemporalidades() {
        return temporalidades;
    }

    public void setTemporalidades(TemporalidadesEntity temporalidades) {
        this.temporalidades = temporalidades;
    }

    public Long getIdPropietario() {
        return idPropietario;
    }

    public void setIdPropietario(Long idPropietario) {
        this.idPropietario = idPropietario;
    }

    public List<ImagenDTO> getImagenes() {
        return imagenes;
    }

    public void setImagenes(List<ImagenDTO> imagenes) {
        this.imagenes = imagenes;
    }
}