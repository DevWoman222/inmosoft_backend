package com.InmoSoft.InmoSoft_Proyect.controller;

import com.InmoSoft.InmoSoft_Proyect.model.UsuariosEntity;
import com.InmoSoft.InmoSoft_Proyect.service.UsuariosService;
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
@RequestMapping("/api/usuarios")
@Tag(name = "Usuarios", description = "Operaciones relacionadas con la gestión de usuarios")
public class UsuariosController {

    @Autowired
    private UsuariosService usuariosService;

    @Operation(summary = "Registrar un nuevo usuario",
            description = "Crea un usuario en el sistema con contraseña encriptada")
    @ApiResponse(responseCode = "200", description = "Usuario creado correctamente",
            content = @Content(schema = @Schema(implementation = UsuariosEntity.class)))
    @PostMapping("/registro")
    public ResponseEntity<UsuariosEntity> crearUsuario(@RequestBody UsuariosEntity usuario) {
        return ResponseEntity.ok(usuariosService.crearUsuario(usuario));
    }

    @Operation(summary = "Listar usuarios", description = "Obtiene todos los usuarios registrados")
    @ApiResponse(responseCode = "200", description = "Lista de usuarios obtenida correctamente",
            content = @Content(schema = @Schema(implementation = UsuariosEntity.class)))
    @GetMapping
    public ResponseEntity<List<UsuariosEntity>> listarUsuarios() {
        return ResponseEntity.ok(usuariosService.listarUsuarios());
    }

    @Operation(summary = "Buscar usuario por ID", description = "Obtiene un usuario específico mediante su ID")
    @ApiResponse(responseCode = "200", description = "Usuario encontrado",
            content = @Content(schema = @Schema(implementation = UsuariosEntity.class)))
    @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    @GetMapping("/{id}")
    public ResponseEntity<UsuariosEntity> obtenerUsuario(@PathVariable Long id) {
        return usuariosService.obtenerUsuarioPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Actualizar usuario", description = "Actualiza la información de un usuario existente")
    @ApiResponse(responseCode = "200", description = "Usuario actualizado correctamente",
            content = @Content(schema = @Schema(implementation = UsuariosEntity.class)))
    @PutMapping("/actualizar/{id}")

    public ResponseEntity<UsuariosEntity> actualizarUsuario(
            @PathVariable Long id,
            @RequestBody UsuariosEntity usuario) {
        return ResponseEntity.ok(usuariosService.actualizarUsuario(id, usuario));
    }

    @Operation(summary = "Desactivar usuario", description = "Desactiva un usuario (estado = false) en lugar de eliminarlo físicamente")
    @ApiResponse(responseCode = "204", description = "Usuario desactivado correctamente")
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Void> desactivarUsuario(@PathVariable Long id) {
        usuariosService.desactivarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Iniciar sesión", description = "Valida las credenciales de un usuario")
    @ApiResponse(responseCode = "200", description = "Login exitoso",
            content = @Content(schema = @Schema(implementation = UsuariosEntity.class)))
    @ApiResponse(responseCode = "401", description = "Credenciales inválidas o usuario inactivo")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String email, @RequestParam String contraseña) {
        Optional<UsuariosEntity> usuario = usuariosService.login(email, contraseña);
        if (usuario.isPresent()) {
            return ResponseEntity.ok(usuario.get());
        } else {
            return ResponseEntity.status(401).body("Credenciales inválidas o usuario inactivo");
        }
    }
}

