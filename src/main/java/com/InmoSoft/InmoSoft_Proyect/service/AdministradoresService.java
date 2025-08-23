package com.InmoSoft.InmoSoft_Proyect.service;

import com.InmoSoft.InmoSoft_Proyect.model.AdministradoresEntity;
import com.InmoSoft.InmoSoft_Proyect.repository.AdministradoresRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AdministradoresService {

    @Autowired
    private AdministradoresRepository administradoresRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Crear administrador
    public AdministradoresEntity crearAdministrador(AdministradoresEntity admin) {
        admin.setContraseña(passwordEncoder.encode(admin.getContraseña()));
        admin.setEstado(true);
        admin.setFechaRegistro(LocalDateTime.now());
        return administradoresRepository.save(admin);
    }

    // Listar administradores
    public List<AdministradoresEntity> listarAdministradores() {
        return administradoresRepository.findAll();
    }

    // Buscar por id
    public Optional<AdministradoresEntity> obtenerAdministradorPorId(Long id) {
        return administradoresRepository.findById(id);
    }

    // Actualizar administrador
    public AdministradoresEntity actualizarAdministrador(Long id, AdministradoresEntity adminActualizado) {
        return administradoresRepository.findById(id).map(admin -> {
            admin.setEmail(adminActualizado.getEmail());
            admin.setTelefono(adminActualizado.getTelefono());
            admin.setNit(adminActualizado.getNit());
            admin.setDireccion(adminActualizado.getDireccion());
            admin.setNombreComercial(adminActualizado.getNombreComercial());
            admin.setNombreRepresentante(adminActualizado.getNombreRepresentante());
            admin.setRol(adminActualizado.getRol());
            admin.setEstado(adminActualizado.isEstado());

            if (adminActualizado.getContraseña() != null && !adminActualizado.getContraseña().isEmpty()) {
                admin.setContraseña(passwordEncoder.encode(adminActualizado.getContraseña()));
            }

            return administradoresRepository.save(admin);
        }).orElseThrow(() -> new RuntimeException("Administrador no encontrado"));
    }

    // Desactivar administrador
    public void desactivarAdministrador(Long id) {
        administradoresRepository.findById(id).ifPresent(admin -> {
            admin.setEstado(false);
            administradoresRepository.save(admin);
        });
    }

    // Login simple
    public Optional<AdministradoresEntity> login(String email, String contraseña) {
        Optional<AdministradoresEntity> adminOpt = administradoresRepository.findByEmail(email);
        if (adminOpt.isPresent()) {
            AdministradoresEntity admin = adminOpt.get();
            if (passwordEncoder.matches(contraseña, admin.getContraseña()) && admin.isEstado()) {
                return Optional.of(admin);
            }
        }
        return Optional.empty();
    }
}
