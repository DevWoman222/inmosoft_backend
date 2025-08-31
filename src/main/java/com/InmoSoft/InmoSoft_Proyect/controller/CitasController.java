package com.InmoSoft.InmoSoft_Proyect.controller;

import com.InmoSoft.InmoSoft_Proyect.model.CitasEntity;
import com.InmoSoft.InmoSoft_Proyect.service.CitasService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/citas")
@Tag(name = "Citas", description = "API para la gestión de citas de propiedades")
public class CitasController {

    private final CitasService citasService;

    @Autowired
    public CitasController(CitasService citasService) {
        this.citasService = citasService;
    }

    /**
     * Agenda una nueva cita.
     * @param nuevaCita La entidad de la cita a agendar.
     * @return ResponseEntity con la cita agendada o un mensaje de error.
     */
    @Operation(summary = "Agendar una nueva cita")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cita agendada exitosamente",
                    content = @Content(schema = @Schema(implementation = CitasEntity.class))),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida", content = @Content),
            @ApiResponse(responseCode = "409", description = "Conflicto: La cita ya existe en ese horario", content = @Content)
    })
    @PostMapping("/agendar")
    public ResponseEntity<CitasEntity> agendarCita(@RequestBody CitasEntity nuevaCita) {
        try {
            CitasEntity citaAgendada = citasService.agendarCita(nuevaCita);
            return new ResponseEntity<>(citaAgendada, HttpStatus.CREATED);
        } catch (IllegalStateException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    /**
     * Cancela una cita existente por su ID.
     * @param idCita El ID de la cita a cancelar.
     * @return ResponseEntity con la cita cancelada o un mensaje de error.
     */
    @Operation(summary = "Cancelar una cita por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cita cancelada exitosamente",
                    content = @Content(schema = @Schema(implementation = CitasEntity.class))),
            @ApiResponse(responseCode = "404", description = "Cita no encontrada", content = @Content),
            @ApiResponse(responseCode = "409", description = "Conflicto: La cita ya está cancelada", content = @Content)
    })
    @PutMapping("/cancelar/{idCita}")
    public ResponseEntity<CitasEntity> cancelarCita(@PathVariable Long idCita) {
        try {
            CitasEntity citaCancelada = citasService.cancelarCita(idCita);
            return new ResponseEntity<>(citaCancelada, HttpStatus.OK);
        } catch (IllegalStateException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    /**
     * Busca todas las citas de un usuario por su ID.
     * @param idUsuario El ID del usuario.
     * @return Una lista de citas del usuario.
     */
    @Operation(summary = "Obtener todas las citas de un usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de citas obtenida",
                    content = @Content(schema = @Schema(implementation = CitasEntity.class))),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado o sin citas", content = @Content)
    })
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<CitasEntity>> findByUsuario(@PathVariable Long idUsuario) {
        List<CitasEntity> citas = citasService.findByUsuario(idUsuario);
        if (citas.isEmpty()) {
            return new ResponseEntity("No se encontraron citas para el usuario con ID: " + idUsuario, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(citas, HttpStatus.OK);
    }
}