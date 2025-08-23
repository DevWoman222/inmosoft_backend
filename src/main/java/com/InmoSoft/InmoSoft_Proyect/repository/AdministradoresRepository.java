package com.InmoSoft.InmoSoft_Proyect.repository;

import com.InmoSoft.InmoSoft_Proyect.model.AdministradoresEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdministradoresRepository extends JpaRepository <AdministradoresEntity, Long > {
    Optional<AdministradoresEntity> findByEmail(String email);
}
