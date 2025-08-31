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

    private AdministradoresRepository administradoresRepository;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public AdministradoresService(AdministradoresRepository administradoresRepository, PasswordEncoder passwordEncoder) {
        this.administradoresRepository = administradoresRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Crear administrador
    public AdministradoresEntity crearAdministrador(AdministradoresEntity admin) {

        AdministradoresEntity newAdmin = new AdministradoresEntity();

        newAdmin.setEmail(admin.getEmail());
        newAdmin.setContraseña(passwordEncoder.encode(admin.getContraseña()));
        newAdmin.setTelefono(admin.getTelefono());
        newAdmin.setNit(admin.getNit());
        newAdmin.setDireccion(admin.getDireccion());
        newAdmin.setEstado(true);
        newAdmin.setNombreComercial(admin.getNombreComercial());
        newAdmin.setNombreRepresentante(admin.getNombreRepresentante());
        newAdmin.setFechaRegistro(LocalDateTime.now());
        newAdmin.setRol("ADMIN");


        return administradoresRepository.save(newAdmin);
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
    public AdministradoresEntity actualizarAdministrador(Long id, AdministradoresEntity admin) {

        AdministradoresEntity existingAdmin = administradoresRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Administrador no encontrado con id: " + id));

        // Actualizamos solo los campos que se pueden modificar
        existingAdmin.setEmail(admin.getEmail());
        existingAdmin.setTelefono(admin.getTelefono());
        existingAdmin.setNit(admin.getNit());
        existingAdmin.setDireccion(admin.getDireccion());
        existingAdmin.setNombreComercial(admin.getNombreComercial());
        existingAdmin.setNombreRepresentante(admin.getNombreRepresentante());
        existingAdmin.setEstado(true);

        // Si llega una nueva contraseña, la encriptamos
        if (admin.getContraseña() != null && !admin.getContraseña().isEmpty()) {
            existingAdmin.setContraseña(passwordEncoder.encode(admin.getContraseña()));
        }

        // Fecha de registro normalmente no se toca, pero puedes actualizar si lo requieres
        // existingAdmin.setFechaRegistro(LocalDateTime.now());

        return administradoresRepository.save(existingAdmin);
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
