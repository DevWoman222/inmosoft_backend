package com.InmoSoft.InmoSoft_Proyect.service;

import com.InmoSoft.InmoSoft_Proyect.model.FavoritosEntity;
import com.InmoSoft.InmoSoft_Proyect.model.PropiedadesEntity;
import com.InmoSoft.InmoSoft_Proyect.model.UsuariosEntity;
import com.InmoSoft.InmoSoft_Proyect.repository.FavoritosRepository;
import com.InmoSoft.InmoSoft_Proyect.repository.PropiedadesRepository;
import com.InmoSoft.InmoSoft_Proyect.repository.UsuariosRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoritosService {

    private final FavoritosRepository favoritosRepository;
    private final UsuariosRepository usuariosRepository;
    private final PropiedadesRepository propiedadesRepository;

    public FavoritosService(FavoritosRepository favoritosRepository,
                            UsuariosRepository usuariosRepository,
                            PropiedadesRepository propiedadesRepository) {
        this.favoritosRepository = favoritosRepository;
        this.usuariosRepository = usuariosRepository;
        this.propiedadesRepository = propiedadesRepository;
    }

    public FavoritosEntity agregarAFavoritos(Long idUsuario, Long idPropiedad) {
        if (favoritosRepository.existsByUsuario_IdUsuarioAndPropiedad_IdPropiedad(idUsuario, idPropiedad)) {
            throw new RuntimeException("La propiedad ya está en favoritos.");
        }

        UsuariosEntity usuario = usuariosRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado."));
        PropiedadesEntity propiedad = propiedadesRepository.findById(idPropiedad)
                .orElseThrow(() -> new RuntimeException("Propiedad no encontrada."));

        FavoritosEntity favorito = new FavoritosEntity(usuario, propiedad);
        return favoritosRepository.save(favorito);
    }

    public List<FavoritosEntity> obtenerFavoritos(Long idUsuario) {
        return favoritosRepository.findByUsuario_IdUsuario(idUsuario);
    }

    public void eliminarDeFavoritos(Long idUsuario, Long idPropiedad) {
        if (!favoritosRepository.existsByUsuario_IdUsuarioAndPropiedad_IdPropiedad(idUsuario, idPropiedad)) {
            throw new RuntimeException("La propiedad no está en favoritos.");
        }
        favoritosRepository.deleteByUsuario_IdUsuarioAndPropiedad_IdPropiedad(idUsuario, idPropiedad);
    }
}
