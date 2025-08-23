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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/propiedades")
@Tag(name = "Gestion de Propiedades", description = "Operaciones Crud para la administracion de las propiedades")
public class PropiedadesController {

    //Atributo
    private PropiedadesService propiedadesService;
    private ObjectMapper objectMapper;

    //Constructor

    @Autowired
    public PropiedadesController(PropiedadesService propiedadesService, ObjectMapper objectMapper) {
        this.propiedadesService = propiedadesService;
        this.objectMapper = objectMapper;
    }


    //Metodos

    //CREATE

   @Operation(
            summary = "Guardar una propiedad",
            description = "Crea y guarda una nueva propiedad en la base de datos junto con sus imágenes asociadas"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Propiedad creada correctamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PropiedadesDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Propietario no encontrado",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "Conflicto al crear la propiedad",
                    content = @Content),
            @ApiResponse(responseCode = "201", description = "Se creó exitosamente la propiedad",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PropiedadesDTO.class))),
    })
   @PostMapping(value = "/crear", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public ResponseEntity<?> crearPropiedad(
           @Parameter(description = "DTO con los datos de la propiedad", required = true,
                   content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                           schema = @Schema(implementation = PropiedadesDTO.class)))
           @RequestPart("propiedadDto") String propiedadDto,

           @RequestPart(value = "files", required = false) List<MultipartFile> files
   ) throws ConflictException, JsonProcessingException {

       // Convertir JSON a DTO
       PropiedadesDTO propertyDto = objectMapper.readValue(propiedadDto, PropiedadesDTO.class);
       String result = propiedadesService.guardarPropiedad(propertyDto, files);

       return ResponseEntity.status(HttpStatus.CREATED).body(result);
   }

    //READ

    //GET ALL
    @Operation(summary = "Obtener todos las propiedades", description = "Recupera una lista de todos las propiedades" +
            " desde la base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de propiedades obtenida exitosamente",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = PropiedadesEntity.class)))),
            @ApiResponse(responseCode = "204", description = "No hay propiedades disponibles")
    })

    @GetMapping("/todas")

    public List<PropiedadesFilterDTO> llamarTodasPropiedades() throws Exception {
        return propiedadesService.llamarTodasPropiedades();
    }

    //Get for location
    @Operation(summary = "Buscar un propiedad cercana a la localizacion", description =
            "Busca un propiedad cercano a la localizacion actual de la propiedad")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Propiedades cercanas",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PropiedadesEntity.class))),
            @ApiResponse(responseCode = "404", description = "Propiedades cercanas no encontradas"),
            @ApiResponse(responseCode = "500", description = "Error interno al procesar la solicitud")
    })

    @GetMapping("/cercanas")
    public List<PropiedadesCompleteDTO> llamarPropiedadCercanaPor(@RequestParam double lat, @RequestParam double lon, @RequestParam(defaultValue = "5000") double radius) {
        return propiedadesService.llamarPropiedadCercanaPor(lat, lon, radius);
    }

    //GET UNIQUE GET BY ID
    @Operation(summary = "Buscar una propiedad por id", description = "Busca un propiedad por id en la base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Propiedad encontrado exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PropiedadesEntity.class))),
            @ApiResponse(responseCode = "404", description = "Propiedad no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno al procesar la solicitud")
    })

    @GetMapping("/{idPropiedad}")
    public ResponseEntity<?> llamarPropiedadPorId(@PathVariable Long idPropiedad) {
        try {
            Optional<PropiedadesCompleteDTO> propiedad = propiedadesService.llamarPropiedadPorId(idPropiedad);
            if (propiedad.isPresent()) {
                return ResponseEntity.status(HttpStatus.OK).body(propiedad.get());
            } else {
                return ResponseEntity.status(404).body("Propiedad no encontrado");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error interno al procesar la solicitud");
        }
    }

    //UPDATE

    //PUT

    @Operation(summary = "Actualizar una propiedad", description =
            "Actualiza y guarda una propiedad en la base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se actualizo correctamente la propiedad",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PropiedadesDTO.class))),
            @ApiResponse(responseCode = "404", description = "Propiedad no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno al actualizar la propiedad")
    })

    @PutMapping(value = "/actualizar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> actualizarPropiedad(
            @RequestPart("propiedadDto") String propiedadDtoJson,
            @RequestPart(value = "files", required = false) List<MultipartFile> files
    ) {
        try {
            // Convertir JSON a DTO (igual que en el POST)
            PropiedadesDTO propiedadDto = objectMapper.readValue(propiedadDtoJson, PropiedadesDTO.class);

            PropiedadesEntity propiedadActualizada = propiedadesService.actualizarPropiedad(propiedadDto, files,null);
            return ResponseEntity.ok(propiedadActualizada);

        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error interno al actualizar la propiedad: " + e.getMessage());
        }
    }

    //DELETE
    @Operation(summary = "Eliminar una propiedad", description = "Elimina una propiedad en la base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Propiedad eliminada Correctamente.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PropiedadesEntity.class))),
            @ApiResponse(responseCode = "404", description = "Propiedad no encontrado"),
    })

    @DeleteMapping("/eliminar/{idPropiedad}")
    public ResponseEntity<String> deleteEvent(@PathVariable Long idPropiedad) {
        try {
            propiedadesService.eliminarPropiedadPorId(idPropiedad);

            return ResponseEntity.ok("Propiedad eliminada Correctamente.");
        } catch (RuntimeException | IOException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
}