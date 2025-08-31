package com.InmoSoft.InmoSoft_Proyect.controller;

import com.InmoSoft.InmoSoft_Proyect.model.AdministradoresEntity;
import com.InmoSoft.InmoSoft_Proyect.service.AdministradoresService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/administradores")
@Tag(name = "Administradores", description = "Operaciones para la gestión de administradores")
public class AdministradoresController {

    @Autowired
    private AdministradoresService administradoresService;

    @Operation(summary = "Registrar administrador", description = "Crea un nuevo administrador en el sistema")
    @ApiResponse(responseCode = "200", description = "Administrador creado correctamente",
            content = @Content(schema = @Schema(implementation = AdministradoresEntity.class)))
    @PostMapping("/registro")
    public ResponseEntity<AdministradoresEntity> crearAdministrador(@RequestBody AdministradoresEntity admin) {
        return ResponseEntity.ok(administradoresService.crearAdministrador(admin));
    }

    @Operation(summary = "Listar administradores", description = "Obtiene todos los administradores registrados")
    @ApiResponse(responseCode = "200", description = "Lista de administradores obtenida correctamente",
            content = @Content(schema = @Schema(implementation = AdministradoresEntity.class)))
    @GetMapping
    public ResponseEntity<List<AdministradoresEntity>> listarAdministradores() {
        return ResponseEntity.ok(administradoresService.listarAdministradores());
    }

    @Operation(summary = "Buscar administrador por ID", description = "Obtiene un administrador específico mediante su ID")
    @ApiResponse(responseCode = "200", description = "Administrador encontrado",
            content = @Content(schema = @Schema(implementation = AdministradoresEntity.class)))
    @ApiResponse(responseCode = "404", description = "Administrador no encontrado")
    @GetMapping("/{id}")
    public ResponseEntity<AdministradoresEntity> obtenerAdministrador(@PathVariable Long id) {
        return administradoresService.obtenerAdministradorPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Actualizar administrador", description = "Modifica los datos de un administrador existente")
    @ApiResponse(responseCode = "200", description = "Administrador actualizado correctamente",
            content = @Content(schema = @Schema(implementation = AdministradoresEntity.class)))
    @PutMapping("/{id}/actualizar")
    public ResponseEntity<AdministradoresEntity> actualizarAdministrador(
            @PathVariable Long id,
            @RequestBody AdministradoresEntity admin) {
        return ResponseEntity.ok(administradoresService.actualizarAdministrador(id, admin));
    }

    @Operation(summary = "Desactivar administrador", description = "Desactiva un administrador en lugar de eliminarlo físicamente")
    @ApiResponse(responseCode = "204", description = "Administrador desactivado correctamente")
    @DeleteMapping("/{id}/eliminar")
    public ResponseEntity<Void> desactivarAdministrador(@PathVariable Long id) {
        administradoresService.desactivarAdministrador(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Login de administrador", description = "Valida las credenciales de un administrador")
    @ApiResponse(responseCode = "200", description = "Login exitoso",
            content = @Content(schema = @Schema(implementation = AdministradoresEntity.class)))
    @ApiResponse(responseCode = "401", description = "Credenciales inválidas o administrador inactivo")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String email, @RequestParam String contraseña) {
        Optional<AdministradoresEntity> admin = administradoresService.login(email, contraseña);
        if (admin.isPresent()) {
            return ResponseEntity.ok(admin.get());
        } else {
            return ResponseEntity.status(401).body("Credenciales inválidas o administrador inactivo");
        }
    }
}
