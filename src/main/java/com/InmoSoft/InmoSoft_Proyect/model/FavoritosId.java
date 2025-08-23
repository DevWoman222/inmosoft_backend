package com.InmoSoft.InmoSoft_Proyect.model;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class FavoritosId implements Serializable {

    private Long idUsuario;
    private Long idPropiedad;

    public FavoritosId() {}

    public FavoritosId(Long idUsuario, Long idPropiedad) {
        this.idUsuario = idUsuario;
        this.idPropiedad = idPropiedad;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Long getIdPropiedad() {
        return idPropiedad;
    }

    public void setIdPropiedad(Long idPropiedad) {
        this.idPropiedad = idPropiedad;
    }

    // Es necesario implementar equals y hashCode en claves compuestas
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FavoritosId)) return false;
        FavoritosId that = (FavoritosId) o;
        return Objects.equals(idUsuario, that.idUsuario) &&
                Objects.equals(idPropiedad, that.idPropiedad);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUsuario, idPropiedad);
    }
}
