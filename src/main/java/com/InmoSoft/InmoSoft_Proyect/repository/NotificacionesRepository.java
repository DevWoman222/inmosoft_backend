package com.InmoSoft.InmoSoft_Proyect.repository;

import com.InmoSoft.InmoSoft_Proyect.model.NotificacionesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificacionesRepository extends JpaRepository<NotificacionesEntity, Long> {
    List<NotificacionesEntity> findByIdUsuarioOrderByFechaCreacionDesc(Long idUsuario);
    List<NotificacionesEntity> findByIdUsuarioAndLeidoFalse(Long idUsuario);
}
