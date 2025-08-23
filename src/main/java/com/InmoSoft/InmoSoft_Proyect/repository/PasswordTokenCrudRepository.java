package com.InmoSoft.InmoSoft_Proyect.repository;

import com.InmoSoft.InmoSoft_Proyect.model.PasswordTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PasswordTokenCrudRepository extends JpaRepository<PasswordTokenEntity, Long> {
    void deleteByExpirationBefore(LocalDateTime fecha);
    Optional<PasswordTokenEntity> findByToken(String token);
}
