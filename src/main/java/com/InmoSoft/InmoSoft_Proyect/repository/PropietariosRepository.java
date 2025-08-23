package com.InmoSoft.InmoSoft_Proyect.repository;

import com.InmoSoft.InmoSoft_Proyect.model.PropietariosEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PropietariosRepository extends JpaRepository<PropietariosEntity, Long> {
    Boolean existsByEmail(String email);

    Optional<PropietariosEntity> findByEmail(String email);
}
