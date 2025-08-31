package com.InmoSoft.InmoSoft_Proyect.service;

import com.InmoSoft.InmoSoft_Proyect.model.CitasEntity;
import com.InmoSoft.InmoSoft_Proyect.model.DTO.CitasDTO;
import com.InmoSoft.InmoSoft_Proyect.repository.CitasRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CitasService {

    private CitasRepository citasRepository;

    private NotificacionesService notificationService;

    public CitasService(CitasRepository citasRepository, NotificacionesService notificationService) {
        this.citasRepository = citasRepository;
        this.notificationService = notificationService;
    }

    // Servicio para enviar notificaciones

    /*
     * Agenda una nueva cita con validaciones.
     * @param citas La entidad de la cita a agendar.
     * @return La cita agendada.
     * @throws IllegalStateException Si ya existe una cita en la misma fecha y hora para la misma propiedad.
     */
    public CitasEntity agendarCita(CitasEntity nuevaCita) {
        boolean existe = citasRepository.existsByFechaAndHora(nuevaCita.getFecha(), nuevaCita.getHora());

        if (existe) {
            throw new IllegalStateException("Ya existe una cita agendada en ese horario.");
        }

    // Envío de notificación
        String mensaje = String.format("Cita agendada para la propiedad %d el %s a las %s.",
                nuevaCita.getIdPropiedad(), nuevaCita.getFecha().toString(), nuevaCita.getHora().toString());
        notificationService.sendNotification(nuevaCita.getIdUsuario(), mensaje);

        return citasRepository.save(nuevaCita);
    }

    /**
     * Cancela una cita existente.
     * @param idCita El ID de la cita a cancelar.
     * @return La cita cancelada.
     * @throws IllegalStateException Si la cita no se encuentra o ya está cancelada.
     */
    public CitasEntity cancelarCita(Long idCita) {
        Optional<CitasEntity> citaOptional = citasRepository.findById(idCita);

        if (citaOptional.isEmpty()) {
            throw new IllegalStateException("La cita con ID " + idCita + " no se encontró.");
        }

        CitasEntity cita = citaOptional.get();

        if ("cancelada".equalsIgnoreCase(cita.getEstado())) {
            throw new IllegalStateException("La cita ya ha sido cancelada.");
        }

        cita.setEstado("cancelada");
        CitasEntity citaCancelada = citasRepository.save(cita);

        // Envío de notificación
        String mensaje = String.format("Cita cancelada para la propiedad %d el %s a las %s.",
                citaCancelada.getIdPropiedad(), citaCancelada.getFecha().toString(), citaCancelada.getHora().toString());
        notificationService.sendNotification(citaCancelada.getIdUsuario(), mensaje);

        return citaCancelada;
    }

    // Si necesitas más métodos para buscar, puedes añadirlos aquí
    public List<CitasEntity> findByUsuario(Long idUsuario) {
        return citasRepository.findByIdUsuario(idUsuario);
    }
}
