package com.InmoSoft.InmoSoft_Proyect.service;

import com.InmoSoft.InmoSoft_Proyect.model.PasswordTokenEntity;
import com.InmoSoft.InmoSoft_Proyect.model.UsuariosEntity;
import com.InmoSoft.InmoSoft_Proyect.repository.UsuariosRepository;
import com.InmoSoft.InmoSoft_Proyect.repository.PasswordTokenCrudRepository;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;


@Service
public class PasswordRecoveryServiceImpl{

    @Autowired
    private PasswordTokenCrudRepository tokenRepo;

    @Autowired
    private EmailServiceImple emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsuariosRepository usuarioRepository;

    public void iniciarRecuperacion(String email) throws MessagingException {
        // Verifica si el usuario existe por email
        Optional<UsuariosEntity> usuarioOpt = usuarioRepository.findByEmail(email);

        if (usuarioOpt.isEmpty()) {
            throw new RuntimeException("El correo no está registrado en la aplicación");
        }

        UsuariosEntity usuario = usuarioOpt.get();

        // Genera el token

        String token = generateToken();

        PasswordTokenEntity tokenEntity = new PasswordTokenEntity();
        tokenEntity.setToken(token);
        tokenEntity.setEmail(email);
        tokenEntity.setExpiration(LocalDateTime.now().plusMinutes(15));
        tokenEntity.setUsed(false);

        // Guarda el token
        tokenRepo.save(tokenEntity);

        // Prepara la URL de recuperación con el token como parámetro
        String url = /*"http://localhost:8080/api/recuperacion/cambiar?token=" +*/ token;

        // Envia el correo con el nombre del usuario y la URL personalizada
        emailService.enviarCorreoRecuperacion(email, usuario.getPrimerNombre() + " " +
                usuario.getPrimerApellido(), url);
    }

    public boolean validarToken(String token) {
        return tokenRepo.findByToken(token)
                .filter(t -> !t.isUsed() && t.getExpiration().isAfter(LocalDateTime.now()))
                .isPresent();
    }

    public void cambiarPassword(String token, String nuevaPassword) {
        PasswordTokenEntity tokenEntity = tokenRepo.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Token inválido"));

        if (tokenEntity.isUsed() || tokenEntity.getExpiration().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Token expirado o ya usado");
        }

        // aquí deberías buscar al usuario por email y cambiarle la contraseña
        UsuariosEntity usuario = usuarioRepository.findByEmail(tokenEntity.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuario.setContraseña(passwordEncoder.encode(nuevaPassword));
        usuarioRepository.save(usuario);

        tokenEntity.setUsed(true);
        tokenRepo.save(tokenEntity);
    }

    // Ejecuta cada hora
    //Tarea automanica para eliminar datos innecesarios de la base de datos
    @Transactional
    @Scheduled(cron = "0 0 * * * *") // (segundo, minuto, hora, día, mes, día_semana)
    public void eliminarTokensExpirados() {
        tokenRepo.deleteByExpirationBefore(LocalDateTime.now());
        System.out.println("Se eliminaron tokens expirados: " + LocalDateTime.now());
    }

    private String generateToken() {
        Random random = new Random();
        int parte1 = 100 + random.nextInt(900); // número entre 100 y 999
        int parte2 = 100 + random.nextInt(900); // número entre 100 y 999
        return parte1 + "-" + parte2;
    }
}