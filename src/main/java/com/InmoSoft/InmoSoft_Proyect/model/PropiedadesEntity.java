package com.InmoSoft.InmoSoft_Proyect.model;

import com.InmoSoft.InmoSoft_Proyect.structure.PointDeserializer;
import com.InmoSoft.InmoSoft_Proyect.structure.PointSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import org.locationtech.jts.geom.Point;

@Entity
@Table(name = "propiedades")
public class PropiedadesEntity {

    //Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_propiedad", nullable = false)
    private Long idPropiedad;

    @Column(nullable = false)
    private int precio;

    @Column(nullable = false, length = 50)
    private String ciudad;

    @Column(nullable = false, length = 50)
    private String sector;

    @Column(nullable = false, length = 50)
    private String codigo;

    @Column(name = "tipo_inmueble", nullable = false, length = 50)
    private String tipoInmueble;

    @Column(nullable = false, length = 50)
    private String estado;

    private int habitaciones;

    private int banos;

    private boolean parqueadero;

    @Column(name = "cuarto_util")
    private boolean cuartoUtil;

    private boolean piscina;

    private boolean vigilancia;

    private boolean balcon;

    private boolean jacuzzi;

    private boolean ascensor;

    @Column(length = 5)
    private boolean condicion;

    @Column(length = 500)
    private String descripcion;

    @JsonSerialize(using = PointSerializer.class)
    @JsonDeserialize(using = PointDeserializer.class)
    @Column(nullable = false, columnDefinition = "geometry(Point, 4326)")
    @Schema(description = "Ubicación geográfica de la propiedad (Point con SRID 4326)")
    private Point coordenadas;

    @Transient
    @JsonIgnore
    @Schema(description = "Latitud de la propiedad (usado para entrada/salida JSON)", example = "4.710989")
    private Double latitude;

    @Transient
    @JsonIgnore
    @Schema(description = "Longitud de la propiedad (usado para entrada/salida JSON)", example = "-74.072090")
    private Double longitude;

    @Column(nullable = false)
    private boolean disponibilidad;

    @Column(name = "area_metros")
    private int areaMetros;

    @ManyToOne
    @JoinColumn(name = "id_temporalidad", nullable = false)
    private TemporalidadesEntity temporalidades;


    @ManyToOne
    @JoinColumn(name = "id_propietario", nullable = false)
    private PropietariosEntity propietario;


    /*@ManyToMany(mappedBy = "propiedades")
    private List<FavoritosEntity> favoritos;*/    //Conexión en usuarios

    //Constructor

    public PropiedadesEntity(Long idPropiedad, int precio, String ciudad, String sector, String codigo, String tipoInmueble, String estado, int habitaciones, int banos, boolean parqueadero, boolean cuartoUtil, boolean piscina, boolean vigilancia, boolean balcon, boolean jacuzzi, boolean ascensor, boolean condicion, String descripcion, Point coordenadas, Double latitude, Double longitude, boolean disponibilidad, int areaMetros, PropietariosEntity propietario, TemporalidadesEntity temporalidades) {
        this.idPropiedad = idPropiedad;
        this.precio = precio;
        this.ciudad = ciudad;
        this.sector = sector;
        this.codigo = codigo;
        this.tipoInmueble = tipoInmueble;
        this.estado = estado;
        this.habitaciones = habitaciones;
        this.banos = banos;
        this.parqueadero = parqueadero;
        this.cuartoUtil = cuartoUtil;
        this.piscina = piscina;
        this.vigilancia = vigilancia;
        this.balcon = balcon;
        this.jacuzzi = jacuzzi;
        this.ascensor = ascensor;
        this.condicion = condicion;
        this.descripcion = descripcion;
        this.coordenadas = coordenadas;
        this.latitude = latitude;
        this.longitude = longitude;
        this.disponibilidad = disponibilidad;
        this.areaMetros = areaMetros;
        this.propietario = propietario;
    }

    public PropiedadesEntity() {
    }

    //Getter and setter


    public TemporalidadesEntity getTemporalidades() {
        return temporalidades;
    }

    public void setTemporalidades(TemporalidadesEntity temporalidades) {
        this.temporalidades = temporalidades;
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

    public int getBanos() {
        return banos;
    }

    public void setBanos(int banos) {
        this.banos = banos;
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

    public boolean isAscensor() {
        return ascensor;
    }

    public void setAscensor(boolean ascensor) {
        this.ascensor = ascensor;
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

    public Point getCoordenadas() {
        return coordenadas;
    }

    public void setCoordenadas(Point coordenadas) {
        this.coordenadas = coordenadas;
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

    public PropietariosEntity getPropietario() {
        return propietario;
    }

    public void setPropietario(PropietariosEntity propietario) {
        this.propietario = propietario;
    }


    public Long getId() {
        return idPropiedad;
    }
}
