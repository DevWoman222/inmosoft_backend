package com.InmoSoft.InmoSoft_Proyect.repository;

import com.InmoSoft.InmoSoft_Proyect.model.CitasEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Repository
public interface CitasRepository extends JpaRepository<CitasEntity, Long> {

    boolean existsByFechaAndHora(Date fecha, LocalTime hora);

    List<CitasEntity> findByIdUsuario(Long idUsuario);
}
