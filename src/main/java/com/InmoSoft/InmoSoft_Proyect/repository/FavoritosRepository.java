package com.InmoSoft.InmoSoft_Proyect.repository;

import com.InmoSoft.InmoSoft_Proyect.model.FavoritosEntity;
import com.InmoSoft.InmoSoft_Proyect.model.FavoritosId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoritosRepository extends JpaRepository<FavoritosEntity, FavoritosId> {

    List<FavoritosEntity> findByUsuario_IdUsuario(Long idUsuario);

    boolean existsByUsuario_IdUsuarioAndPropiedad_IdPropiedad(Long idUsuario, Long idPropiedad);

    void deleteByUsuario_IdUsuarioAndPropiedad_IdPropiedad(Long idUsuario, Long idPropiedad);
}
