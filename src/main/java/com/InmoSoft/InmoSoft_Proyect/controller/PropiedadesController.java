package com.InmoSoft.InmoSoft_Proyect.controller;

import com.InmoSoft.InmoSoft_Proyect.infraestructur.exceptions.ConflictException;
import com.InmoSoft.InmoSoft_Proyect.infraestructur.exceptions.NotFoundException;
import com.InmoSoft.InmoSoft_Proyect.model.PropiedadesEntity;
import com.InmoSoft.InmoSoft_Proyect.model.DTO.PropiedadesCompleteDTO;
import com.InmoSoft.InmoSoft_Proyect.model.DTO.PropiedadesDTO;
import com.InmoSoft.InmoSoft_Proyect.model.DTO.PropiedadesFilterDTO;
import com.InmoSoft.InmoSoft_Proyect.service.PropiedadesService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/propiedades")
@Tag(name = "Gestion de Propiedades", description = "Operaciones Crud para la administracion de las propiedades")
public class PropiedadesController {

    private final PropiedadesService propiedadesService;
    private final ObjectMapper objectMapper;

    @Autowired
    public PropiedadesController(PropiedadesService propiedadesService, ObjectMapper objectMapper) {
        this.propiedadesService = propiedadesService;
        this.objectMapper = objectMapper;
    }

    // CREATE
    @Operation(
            summary = "Guardar una propiedad",
            description = "Crea y guarda una nueva propiedad en la base de datos junto con sus imágenes asociadas"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Propiedad creada correctamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PropiedadesDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Propietario no encontrado",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "Conflicto al crear la propiedad",
                    content = @Content),
    })
    @PostMapping(value = "/crear", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> crearPropiedad(
            @Parameter(description = "DTO con los datos de la propiedad en formato JSON", required = true)
            @RequestPart("propiedadDto") String propiedadDto,
            @RequestPart(value = "files", required = false) List<MultipartFile> files
    ) throws ConflictException, JsonProcessingException {
        PropiedadesDTO propertyDto = objectMapper.readValue(propiedadDto, PropiedadesDTO.class);
        String result = propiedadesService.guardarPropiedad(propertyDto, files);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    // READ
    @Operation(summary = "Obtener todas las propiedades", description = "Recupera una lista de todas las propiedades")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de propiedades obtenida exitosamente",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = PropiedadesFilterDTO.class)))),
            @ApiResponse(responseCode = "204", description = "No hay propiedades disponibles")
    })
    @GetMapping("/todas")
    public ResponseEntity<List<PropiedadesFilterDTO>> llamarTodasPropiedades() throws Exception {
        List<PropiedadesFilterDTO> propiedades = propiedadesService.llamarTodasPropiedades();
        if (propiedades.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(propiedades);
    }

    @Operation(summary = "Buscar propiedades por ubicación", description = "Busca propiedades cercanas a una latitud y longitud dadas dentro de un radio")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Propiedades cercanas encontradas",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = PropiedadesCompleteDTO.class)))),
            @ApiResponse(responseCode = "204", description = "No hay propiedades cercanas")
    })
    @GetMapping("/cercanas")
    public ResponseEntity<List<PropiedadesCompleteDTO>> llamarPropiedadCercanaPor(
            @RequestParam double lat,
            @RequestParam double lon,
            @RequestParam(defaultValue = "5000") double radius
    ) {
        List<PropiedadesCompleteDTO> propiedades = propiedadesService.llamarPropiedadCercanaPor(lat, lon, radius);
        if (propiedades.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(propiedades);
    }

    @Operation(summary = "Buscar una propiedad por ID", description = "Busca una propiedad en la base de datos por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Propiedad encontrada exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PropiedadesCompleteDTO.class))),
            @ApiResponse(responseCode = "404", description = "Propiedad no encontrada")
    })
    @GetMapping("/{idPropiedad}")
    public ResponseEntity<PropiedadesCompleteDTO> llamarPropiedadPorId(@PathVariable Long idPropiedad) {
        try {
            Optional<PropiedadesCompleteDTO> propiedad = propiedadesService.llamarPropiedadPorId(idPropiedad);
            return propiedad.map(p -> ResponseEntity.ok().body(p))
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

    // UPDATE
    @Operation(summary = "Actualizar una propiedad", description = "Actualiza y guarda una propiedad existente en la base de datos, incluyendo la posibilidad de reemplazar imágenes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Propiedad actualizada correctamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PropiedadesEntity.class))),
            @ApiResponse(responseCode = "404", description = "Propiedad no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno al actualizar la propiedad")
    })
    @PutMapping(value = "/actualizar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> actualizarPropiedad(
            @RequestPart("propiedadDto") String propiedadDto,
            @RequestPart(value = "files", required = false) List<MultipartFile> files
    ) throws JsonProcessingException {

        try {
            PropiedadesDTO propiedadUpdateDto = objectMapper.readValue(propiedadDto, PropiedadesDTO.class);
            PropiedadesEntity propiedadActualizada = propiedadesService.actualizarPropiedad(propiedadUpdateDto, files);
            return ResponseEntity.ok(propiedadActualizada);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno al actualizar la propiedad: " + e.getMessage());
        }
    }

    // DELETE
    @Operation(summary = "Eliminar una propiedad", description = "Elimina una propiedad de la base de datos por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Propiedad eliminada correctamente"),
            @ApiResponse(responseCode = "404", description = "Propiedad no encontrada")
    })
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/eliminar/{idPropiedad}")
    public ResponseEntity<String> deleteEvent(@PathVariable Long idPropiedad) {
        try {
            propiedadesService.eliminarPropiedadPorId(idPropiedad);
            return ResponseEntity.ok("Propiedad eliminada correctamente.");
        } catch (RuntimeException | IOException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}