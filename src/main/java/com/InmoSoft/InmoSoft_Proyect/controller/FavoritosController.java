package com.InmoSoft.InmoSoft_Proyect.controller;

import com.InmoSoft.InmoSoft_Proyect.model.FavoritosEntity;
import com.InmoSoft.InmoSoft_Proyect.service.FavoritosService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favoritos")
@Tag(name = "Favoritos", description = "Gestión de propiedades favoritas de los usuarios")
public class FavoritosController {

    private final FavoritosService favoritosService;

    public FavoritosController(FavoritosService favoritosService) {
        this.favoritosService = favoritosService;
    }

    @Operation(summary = "Agregar una propiedad a favoritos")
    @PostMapping("/{idUsuario}/{idPropiedad}")
    public ResponseEntity<FavoritosEntity> agregarAFavoritos(
            @PathVariable Long idUsuario,
            @PathVariable Long idPropiedad) {
        return ResponseEntity.ok(favoritosService.agregarAFavoritos(idUsuario, idPropiedad));
    }

    @Operation(summary = "Obtener la lista de favoritos de un usuario")
    @GetMapping("/{idUsuario}")
    public ResponseEntity<List<FavoritosEntity>> obtenerFavoritos(@PathVariable Long idUsuario) {
        return ResponseEntity.ok(favoritosService.obtenerFavoritos(idUsuario));
    }

    @Operation(summary = "Eliminar una propiedad de favoritos")
    @DeleteMapping("/{idUsuario}/{idPropiedad}")
    public ResponseEntity<Void> eliminarDeFavoritos(
            @PathVariable Long idUsuario,
            @PathVariable Long idPropiedad) {
        favoritosService.eliminarDeFavoritos(idUsuario, idPropiedad);
        return ResponseEntity.noContent().build();
    }
}
