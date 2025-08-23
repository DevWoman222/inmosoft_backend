package com.InmoSoft.InmoSoft_Proyect.repository;

import com.InmoSoft.InmoSoft_Proyect.model.CitasEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@Repository
public interface CitasRepository extends JpaRepository<CitasEntity, Long> {

    // Buscar si ya existe una cita en esa propiedad, fecha y hora
    Optional<CitasEntity> findByIdPropiedadAndFechaAndHora(Long idPropiedad, LocalDate fecha, LocalTime hora);
}
