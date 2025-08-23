package com.InmoSoft.InmoSoft_Proyect.model;

import com.InmoSoft.InmoSoft_Proyect.model.PropiedadesEntity;
import com.InmoSoft.InmoSoft_Proyect.model.UsuariosEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "favoritos")
public class FavoritosEntity {

    @EmbeddedId
    private FavoritosId id;

    @ManyToOne
    @MapsId("idUsuario") // enlaza con la parte de usuario de la PK
    @JoinColumn(name = "id_usuario", nullable = false)
    private UsuariosEntity usuario;

    @ManyToOne
    @MapsId("idPropiedad") // enlaza con la parte de propiedad de la PK
    @JoinColumn(name = "id_propiedad", nullable = false)
    private PropiedadesEntity propiedad;

    public FavoritosEntity() {}

    public FavoritosEntity(UsuariosEntity usuario, PropiedadesEntity propiedad) {
        this.usuario = usuario;
        this.propiedad = propiedad;
        this.id = new FavoritosId(usuario.getIdUsuario(), propiedad.getIdPropiedad());
    }

    // Getters y setters
    public FavoritosId getId() {
        return id;
    }

    public void setId(FavoritosId id) {
        this.id = id;
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
}
