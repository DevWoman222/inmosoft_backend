package com.InmoSoft.InmoSoft_Proyect.service;

import org.springframework.stereotype.Service;

@Service
public class NotificacionesService {

        public void sendNotification(Long userId, String message) {
            // Lógica para enviar la notificación
            // Por ejemplo, enviar un correo, una notificación push, etc.
            System.out.println("Enviando notificación al usuario " + userId + ": " + message);
        }
    }