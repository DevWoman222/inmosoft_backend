package com.InmoSoft.InmoSoft_Proyect.service;

import com.InmoSoft.InmoSoft_Proyect.model.Imagen;
import com.InmoSoft.InmoSoft_Proyect.model.PropiedadesEntity;
import com.InmoSoft.InmoSoft_Proyect.repository.ImagenRepository;
import com.InmoSoft.InmoSoft_Proyect.repository.PropiedadesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ImagenService {

    private final ImagenStorageService imagenStorageService;
    private final ImagenRepository imagenRepository;
    private final PropiedadesRepository propiedadesRepository;

    @Autowired
    public ImagenService(ImagenStorageService imagenStorageService, ImagenRepository imagenRepository, PropiedadesRepository propiedadesRepository) {
        this.imagenStorageService = imagenStorageService;
        this.imagenRepository = imagenRepository;
        this.propiedadesRepository = propiedadesRepository;
    }

    // Subir nuevas imágenes y asociarlas a una propiedad
    public List<Imagen> uploadImages(List<MultipartFile> files, PropiedadesEntity propiedad) throws IOException {
        List<Imagen> imagenesGuardadas = new ArrayList<>();

        for (MultipartFile file : files) {
            String fileName = UUID.randomUUID() + "-" + file.getOriginalFilename();
            String key = "imagenes/" + fileName;

            imagenStorageService.uploadImages(List.of(file), key);

            Imagen imagen = new Imagen();
            imagen.setNombreOriginal(file.getOriginalFilename());
            imagen.setKeyS3(key);
            imagen.setPropiedad(propiedad);

            imagenesGuardadas.add(imagenRepository.save(imagen));
        }

        return imagenesGuardadas;
    }

    public Imagen uploadImage(MultipartFile file, PropiedadesEntity propiedad) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new IOException("Archivo de imagen vacío o nulo");
        }

        String fileName = UUID.randomUUID() + "-" + file.getOriginalFilename();
        String key = "imagenes/" + fileName;

        imagenStorageService.uploadImage(file, key);

        Imagen imagen = new Imagen();
        imagen.setNombreOriginal(file.getOriginalFilename());
        imagen.setKeyS3(key);
        imagen.setPropiedad(propiedad);
        return imagenRepository.save(imagen);
    }

    // Obtener la primera imagen con URL firmada
    public Imagen getFirstImage(Long idPropiedad) {
        List<Imagen> imagenes = imagenRepository.findAllImagenByPropiedadIdPropiedad(idPropiedad);

        if (imagenes.isEmpty()) return null;

        Imagen imagen = imagenes.get(0);
        try {
            String urlFirmada = getPresignedUrl(imagen.getIdImagen());
            Imagen imagenFirmada = new Imagen();
            imagenFirmada.setIdImagen(imagen.getIdImagen());
            imagenFirmada.setNombreOriginal(imagen.getNombreOriginal());
            imagenFirmada.setKeyS3(urlFirmada);
            return imagenFirmada;
        } catch (Exception e) {
            return null;
        }
    }

    // Obtener todas las imágenes con URL firmada
    public List<Imagen> getImagesWithUrls(Long idPropiedad) {
        List<Imagen> imagenes = imagenRepository.findAllImagenByPropiedadIdPropiedad(idPropiedad);
        List<Imagen> resultado = new ArrayList<>();

        for (Imagen imagen : imagenes) {
            try {
                String urlFirmada = getPresignedUrl(imagen.getIdImagen());
                Imagen imagenFirmada = new Imagen();
                imagenFirmada.setIdImagen(imagen.getIdImagen());
                imagenFirmada.setNombreOriginal(imagen.getNombreOriginal());
                imagenFirmada.setKeyS3(urlFirmada);
                resultado.add(imagenFirmada);
            } catch (Exception e) {
                System.err.println("Error firmando URL para imagen ID " + imagen.getIdImagen() + ": " + e.getMessage());
            }
        }
        return resultado;
    }

    // Obtener URL firmada de una imagen
    public String getPresignedUrl(Long idImagen) throws Exception {
        Imagen imagen = imagenRepository.findById(idImagen)
                .orElseThrow(() -> new Exception("Imagen no encontrada"));
        return imagenStorageService.generatePresignedUrl(imagen.getKeyS3(), Duration.ofMinutes(5));
    }

    // Actualizar imágenes de una propiedad

    public List<Imagen> updateImages(Long idPropiedad, List<MultipartFile> nuevasImagenes, List<Long> idsAEliminar) throws IOException {

        // Eliminar solo las imágenes solicitadas
        if (idsAEliminar != null) {
            for (Long idImagen : idsAEliminar) {
                Imagen imagen = imagenRepository.findById(idImagen)
                        .orElseThrow(() -> new IOException("Imagen no encontrada"));

                // Borrar del almacenamiento físico (ejemplo: S3, disco, etc.)
                imagenStorageService.deleteImage(imagen.getKeyS3());

                // Borrar de la base de datos
                imagenRepository.delete(imagen);
            }
        }

        // Subir nuevas imágenes si existen
        List<Imagen> nuevas = new ArrayList<>();

        if (nuevasImagenes != null && !nuevasImagenes.isEmpty()) {
            // Traer la propiedad una sola vez
            PropiedadesEntity propiedad = propiedadesRepository.findById(idPropiedad)
                    .orElseThrow(() -> new IOException("Propiedad no encontrada"));

            for (MultipartFile file : nuevasImagenes) {
                // Subir archivo y devolver la URL o clave
                Imagen imagen = uploadImage(file, propiedad);

                nuevas.add(imagenRepository.save(imagen));
            }
        }

        return nuevas;

    }


        // Eliminar una sola imagen
    public void deleteImage(Long idImagen) throws IOException {
        Imagen imagen = imagenRepository.findById(idImagen)
                .orElseThrow(() -> new IOException("Imagen no encontrada"));
        imagenStorageService.deleteImage(imagen.getKeyS3());
        imagenRepository.delete(imagen);
    }

}
