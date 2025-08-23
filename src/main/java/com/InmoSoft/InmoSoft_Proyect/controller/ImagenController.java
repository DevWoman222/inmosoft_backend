package com.InmoSoft.InmoSoft_Proyect.controller;

import com.InmoSoft.InmoSoft_Proyect.model.Imagen;
import com.InmoSoft.InmoSoft_Proyect.model.PropiedadesEntity;
import com.InmoSoft.InmoSoft_Proyect.repository.PropiedadesRepository;
import com.InmoSoft.InmoSoft_Proyect.service.ImagenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/imagen")
public class ImagenController {

    private final ImagenService imagenService;

    private final PropiedadesRepository propiedadesRepository;

    @Autowired
    public ImagenController(ImagenService imagenService, PropiedadesRepository propiedadesRepository) {
        this.imagenService = imagenService;
        this.propiedadesRepository = propiedadesRepository;
    }

    @PostMapping("/upload/{idPropiedad}")
    public ResponseEntity<?> upload(@RequestParam("file") List<MultipartFile> files,
                                    @PathVariable Long idPropiedad) {

        if (files == null || files.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Archivo vacío o no proporcionado"));
        }

        try {
            Optional<PropiedadesEntity> propiedadOpt = propiedadesRepository.findById(idPropiedad);
            if (propiedadOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Propiedad no encontrada"));
            }

            List<Imagen> imagenesGuardadas = imagenService.uploadImages(files, propiedadOpt.get());

            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "mensaje", "Imágenes guardadas correctamente",
                    "imagenes", imagenesGuardadas
            ));
        } catch (Exception e) {
            return handleException(e);
        }
    }


    /*@PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody List<Imagen> imagenes, @RequestParam("file") List<MultipartFile> files) {
        try {

            imagenService.updateImages(imagenes, files);

            return ResponseEntity.ok(Map.of(
                    "Mesaje", "Imagen actualizada correctamente"
            ));
        } catch (Exception e) {
            return handleException(e);
        }
    }*/

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            imagenService.deleteImage(id);
            return ResponseEntity.ok(Map.of("mensaje", "Imagen eliminada correctamente"));
        } catch (Exception e) {
            return handleException(e);
        }
    }

    private ResponseEntity<?> handleException(Exception e) {
        if ("Imagen no encontrada".equals(e.getMessage())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", e.getMessage()));
    }
}