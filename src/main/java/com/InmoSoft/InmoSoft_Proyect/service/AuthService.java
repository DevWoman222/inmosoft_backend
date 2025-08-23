package com.InmoSoft.InmoSoft_Proyect.service;

import com.InmoSoft.InmoSoft_Proyect.infraestructur.exceptions.JwtAuthenticationException;
import com.InmoSoft.InmoSoft_Proyect.infraestructur.security.JwtGenerator;
import com.InmoSoft.InmoSoft_Proyect.model.DTO.JwtResponseDTO;
import com.InmoSoft.InmoSoft_Proyect.model.DTO.LoginDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

    // -----Validación de credenciales-----

@Service
public class AuthService {

    // Conexión a la clase que crea el token
    private final JwtGenerator jwtGenerator;

    // Clase de Spring Security que se encarga de verificar las credenciales
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthService(JwtGenerator jwtGenerator, AuthenticationManager authenticationManager) {
        this.jwtGenerator = jwtGenerator;
        this.authenticationManager = authenticationManager;
    }

    public JwtResponseDTO login(LoginDTO loginDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDto.getEmail(),
                            loginDto.getContraseña()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtGenerator.generateToken(authentication);
            return new JwtResponseDTO(token);
        } catch (AuthenticationException e) {
            throw new JwtAuthenticationException("Credenciales inválidas");
        }
    }
}