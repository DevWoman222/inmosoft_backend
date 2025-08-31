package com.InmoSoft.InmoSoft_Proyect.controller;

import com.InmoSoft.InmoSoft_Proyect.model.PropietariosEntity;
import com.InmoSoft.InmoSoft_Proyect.service.PropietarioService;
import com.InmoSoft.InmoSoft_Proyect.infraestructur.exceptions.ConflictException;
import com.InmoSoft.InmoSoft_Proyect.infraestructur.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/propietarios")
public class PropietarioController {

    @Autowired
    private PropietarioService propietariosService;

    // GET todos
    @GetMapping("/all")
    public List<PropietariosEntity> listarTodos() {
        return propietariosService.getAllOwners();
    }

    // GET por ID
    @GetMapping("/{id}")
    public ResponseEntity<PropietariosEntity> obtenerPorId(@PathVariable Long id) {
        return propietariosService.getByOwner(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST crear
    @PostMapping("/guardar")
    public ResponseEntity<String> crear(@RequestBody PropietariosEntity propietario) {
        try {
            String mensaje = propietariosService.saveOwner(propietario);
            return ResponseEntity.ok(mensaje);
        } catch (ConflictException e) {
            return ResponseEntity.status(409).body(e.getMessage());
        }
    }

    // PUT actualizar
    @PutMapping("/{id}/actualizar")
    public ResponseEntity<String> actualizar(@PathVariable Long id, @RequestBody PropietariosEntity propietario) {
        try {
            propietario.setIdPropietario(id);
            propietariosService.editOwner(propietario);
            return ResponseEntity.ok("Propietario actualizado correctamente");
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE eliminar
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (propietariosService.getByOwner(id).isPresent()) {
            propietariosService.deleteOwner(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
