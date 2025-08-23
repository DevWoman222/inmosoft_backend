package com.InmoSoft.InmoSoft_Proyect.repository;

import com.InmoSoft.InmoSoft_Proyect.model.UsuariosEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuariosRepository extends JpaRepository<UsuariosEntity, Long> {
    Optional<UsuariosEntity> findByEmail(String email);
}
