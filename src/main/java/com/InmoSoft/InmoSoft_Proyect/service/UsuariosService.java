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

        UsuariosEntity newUser = new UsuariosEntity();

        newUser.setPrimerNombre(usuario.getPrimerNombre());
        newUser.setSegundoNombre(usuario.getSegundoNombre());
        newUser.setPrimerApellido(usuario.getPrimerApellido());
        newUser.setSegundoApellido(usuario.getSegundoApellido());
        newUser.setEmail(usuario.getEmail());
        newUser.setCedula(usuario.getCedula());
        newUser.setTelefono(usuario.getTelefono());
        newUser.setContraseña(passwordEncoder.encode(usuario.getContraseña())); // Encripta contraseña
        newUser.setEstado(true); // Por defecto activo

        return usuariosRepository.save(newUser);
    }

    // Obtener todos
    public List<UsuariosEntity> listarUsuarios() {
        return usuariosRepository.findAll();
    }

    // Buscar por id
    public Optional<UsuariosEntity> obtenerUsuarioPorId(Long id) {
        return usuariosRepository.findById(id);
    }


    // Update USER
    public UsuariosEntity actualizarUsuario(Long id, UsuariosEntity usuario) {

        UsuariosEntity existingUser = usuariosRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));

        existingUser.setPrimerNombre(usuario.getPrimerNombre());
        existingUser.setSegundoNombre(usuario.getSegundoNombre());
        existingUser.setPrimerApellido(usuario.getPrimerApellido());
        existingUser.setSegundoApellido(usuario.getSegundoApellido());
        existingUser.setEmail(usuario.getEmail());
        existingUser.setCedula(usuario.getCedula());
        existingUser.setTelefono(usuario.getTelefono());
        existingUser.setEstado(true);

        // Si se manda nueva contraseña, la encriptamos
        if (usuario.getContraseña() != null && !usuario.getContraseña().isEmpty()) {
            existingUser.setContraseña(passwordEncoder.encode(usuario.getContraseña()));
        }

        return usuariosRepository.save(existingUser);
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
