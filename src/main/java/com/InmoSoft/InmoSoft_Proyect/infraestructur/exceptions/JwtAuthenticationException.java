package com.InmoSoft.InmoSoft_Proyect.infraestructur.exceptions;

public class JwtAuthenticationException extends RuntimeException {

    public JwtAuthenticationException(String message) {

        super(message);
    }
}
// Exception para errores de credenciales