package com.InmoSoft.InmoSoft_Proyect.service;

import com.InmoSoft.InmoSoft_Proyect.model.UsuariosEntity;
import com.InmoSoft.InmoSoft_Proyect.repository.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuariosService {

    @Autowired
    private UsuariosRepository usuariosRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // Para encriptar la contraseña

    // Crear usuario (registro)
    public UsuariosEntity crearUsuario(UsuariosEntity usuario) {
        usuario.setContraseña(passwordEncoder.encode(usuario.getContraseña())); // Encripta contraseña
        usuario.setEstado(true); // Por defecto activo
        return usuariosRepository.save(usuario);
    }

    // Obtener todos
    public List<UsuariosEntity> listarUsuarios() {
        return usuariosRepository.findAll();
    }

    // Buscar por id
    public Optional<UsuariosEntity> obtenerUsuarioPorId(Long id) {
        return usuariosRepository.findById(id);
    }

    // Actualizar
    public UsuariosEntity actualizarUsuario(Long id, UsuariosEntity usuarioActualizado) {
        return usuariosRepository.findById(id).map(usuario -> {
            usuario.setPrimerNombre(usuarioActualizado.getPrimerNombre());
            usuario.setSegundoNombre(usuarioActualizado.getSegundoNombre());
            usuario.setPrimerApellido(usuarioActualizado.getPrimerApellido());
            usuario.setSegundoApellido(usuarioActualizado.getSegundoApellido());
            usuario.setEmail(usuarioActualizado.getEmail());
            usuario.setCedula(usuarioActualizado.getCedula());
            usuario.setTelefono(usuarioActualizado.getTelefono());
            usuario.setRol(usuarioActualizado.getRol());
            usuario.setEstado(usuarioActualizado.isEstado());

            // Solo actualiza contraseña si no viene vacía
            if (usuarioActualizado.getContraseña() != null && !usuarioActualizado.getContraseña().isEmpty()) {
                usuario.setContraseña(passwordEncoder.encode(usuarioActualizado.getContraseña()));
            }

            return usuariosRepository.save(usuario);
        }).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    // Eliminar (cambiar estado a false en lugar de borrar físico)
    public void desactivarUsuario(Long id) {
        usuariosRepository.findById(id).ifPresent(usuario -> {
            usuario.setEstado(false);
            usuariosRepository.save(usuario);
        });
    }

    // Login simple (validar email y contraseña)
    public Optional<UsuariosEntity> login(String email, String contraseña) {
        Optional<UsuariosEntity> usuarioOpt = usuariosRepository.findByEmail(email);
        if (usuarioOpt.isPresent()) {
            UsuariosEntity usuario = usuarioOpt.get();
            if (passwordEncoder.matches(contraseña, usuario.getContraseña()) && usuario.isEstado()) {
                return Optional.of(usuario);
            }
        }
        return Optional.empty();
    }
}
