package com.InmoSoft.InmoSoft_Proyect.service;

import com.InmoSoft.InmoSoft_Proyect.infraestructur.exceptions.ConflictException;
import com.InmoSoft.InmoSoft_Proyect.infraestructur.exceptions.NotFoundException;
import com.InmoSoft.InmoSoft_Proyect.model.Imagen;
import com.InmoSoft.InmoSoft_Proyect.model.PropiedadesEntity;
import com.InmoSoft.InmoSoft_Proyect.model.PropietariosEntity;
import com.InmoSoft.InmoSoft_Proyect.model.TemporalidadesEntity;
import com.InmoSoft.InmoSoft_Proyect.model.DTO.PropiedadesCompleteDTO;
import com.InmoSoft.InmoSoft_Proyect.model.DTO.PropiedadesDTO;
import com.InmoSoft.InmoSoft_Proyect.model.DTO.PropiedadesFilterDTO;
import com.InmoSoft.InmoSoft_Proyect.repository.PropiedadesRepository;
import com.InmoSoft.InmoSoft_Proyect.repository.PropietariosRepository;
import com.InmoSoft.InmoSoft_Proyect.repository.TemporalidadesRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PropiedadesService {

    //Atributos

    private final PropietarioService propietarioService;

    private final PropietariosRepository propietariosRepository;

    private PropiedadesRepository propiedadesRepository;

    //Location

    private final GeometryFactory geometryFactory;

    private final ImagenService imagenService;

    private final TemporalidadesService temporalidadesService;

    private final TemporalidadesRepository temporalidadesRepository;


    // Constructor

    @Autowired
    public PropiedadesService(ImagenService imagenService, GeometryFactory geometryFactory, PropietarioService propietarioService, PropietariosRepository propietariosRepository, PropiedadesRepository propiedadesRepository, TemporalidadesService temporalidadesService, TemporalidadesRepository temporalidadesRepository) {
        this.geometryFactory = geometryFactory;
        this.propietariosRepository = propietariosRepository;
        this.propiedadesRepository = propiedadesRepository;
        this.propietarioService = propietarioService;
        this.imagenService = imagenService;
        this.temporalidadesService = temporalidadesService;
        this.temporalidadesRepository = temporalidadesRepository;
    }

    // Metodos

    // CREATE

    public String guardarPropiedad(PropiedadesDTO propiedadDto, List<MultipartFile> files) throws ConflictException {
        try {
            // 1️ Validar coordenadas
            if (propiedadDto.getLatitude() == null || propiedadDto.getLongitude() == null) {
                throw new RuntimeException("Latitud y longitud son obligatorios para crear la ubicación de la propiedad.");
            }

            GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);
            Point location = geometryFactory.createPoint(
                    new Coordinate(propiedadDto.getLongitude(), propiedadDto.getLatitude())
            );

            // 2️ Crear nueva propiedad
            PropiedadesEntity nuevaPropiedad = new PropiedadesEntity();
            nuevaPropiedad.setCoordenadas(location);
            nuevaPropiedad.setPrecio(propiedadDto.getPrecio());
            nuevaPropiedad.setBaños(propiedadDto.getBaños());
            nuevaPropiedad.setCiudad(propiedadDto.getCiudad());
            nuevaPropiedad.setSector(propiedadDto.getSector());
            nuevaPropiedad.setCodigo(generarCodigoAleatorio());
            nuevaPropiedad.setTipoInmueble(propiedadDto.getTipoInmueble());
            nuevaPropiedad.setEstado(propiedadDto.getEstado());
            nuevaPropiedad.setHabitaciones(propiedadDto.getHabitaciones());
            nuevaPropiedad.setParqueadero(propiedadDto.isParqueadero());
            nuevaPropiedad.setCuartoUtil(propiedadDto.isCuartoUtil());
            nuevaPropiedad.setPiscina(propiedadDto.isPiscina());
            nuevaPropiedad.setVigilancia(propiedadDto.isVigilancia());
            nuevaPropiedad.setBalcon(propiedadDto.isBalcon());
            nuevaPropiedad.setJacuzzi(propiedadDto.isJacuzzi());
            nuevaPropiedad.setAscensor(propiedadDto.isAsensor());
            nuevaPropiedad.setCondicion(propiedadDto.isCondicion());
            nuevaPropiedad.setDescripcion(propiedadDto.getDescripcion());
            nuevaPropiedad.setDisponibilidad(propiedadDto.isDisponibilidad());
            nuevaPropiedad.setAreaMetros(propiedadDto.getAreaMetros());

            // 3️ Asignar propietario
            Long idPropietario = propiedadDto.getIdPropietario();
            PropietariosEntity propietario = propietarioService.getByOwner(idPropietario)
                    .orElseThrow(() -> new NotFoundException("Propietario no encontrado con id " + idPropietario));
            nuevaPropiedad.setPropietario(propietario);

            // 4️ Guardar temporalidades
            TemporalidadesEntity temporalidades = temporalidadesService.save(propiedadDto.getTemporalidades());
            nuevaPropiedad.setTemporalidades(temporalidades);

            // 5️ Guardar propiedad
            propiedadesRepository.save(nuevaPropiedad);

            // 6️ Guardar imágenes (si se enviaron)
            if (files != null && !files.isEmpty()) {
                imagenService.uploadImages(files, nuevaPropiedad);
            }

            return "Se creó exitosamente la propiedad";

        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error al crear la propiedad: " + e.getMessage(), e);
        }
    }


    // READ

    //Get All
    public List<PropiedadesFilterDTO> llamarTodasPropiedades() throws Exception {
        List<PropiedadesEntity> propiedades = propiedadesRepository.findAll(); //Propiedades
        List<PropiedadesFilterDTO> propiedadesDto = new ArrayList<>();// Array vacio [], Array lleno     [0,1,2,3,4]

        for (PropiedadesEntity propiedad : propiedades) {

            PropiedadesFilterDTO dto = new PropiedadesFilterDTO();

            dto.setIdPropiedad(propiedad.getIdPropiedad());
            dto.setEstado(propiedad.getEstado());
            dto.setCiudad(propiedad.getCiudad());
            dto.setCodigo(propiedad.getCodigo());
            dto.setPrecio(propiedad.getPrecio());
            dto.setSector(propiedad.getSector());

            Imagen imagen = imagenService.getFirstImage(propiedad.getIdPropiedad());

            dto.setImagen(imagen);

            propiedadesDto.add(dto);
        }

        return propiedadesDto;
    }


    //Get Only
    public Optional<PropiedadesCompleteDTO> llamarPropiedadPorId(Long idPropiedad) {
        Optional<PropiedadesEntity> propiedad = propiedadesRepository.findById(idPropiedad);

        if (propiedad.isEmpty()) {
            return Optional.empty();
        }

        List<Imagen> imagenes = imagenService.getImagesWithUrls(idPropiedad);

        PropiedadesCompleteDTO propiedadCompleta = convertToDto(propiedad.get(), imagenes);

        return Optional.of(propiedadCompleta);
    }

    //Get for location
    public List<PropiedadesCompleteDTO> llamarPropiedadCercanaPor(double lat, double lon, double radius) {
        List<PropiedadesEntity> propiedades = propiedadesRepository.encontrarPropiedadCercanaPor(lat, lon, radius);
        List<PropiedadesCompleteDTO> propiedadesDto = new ArrayList<>();

        for (PropiedadesEntity propiedad : propiedades) {
            List<Imagen> imagenes = imagenService.getImagesWithUrls(propiedad.getIdPropiedad());
            PropiedadesCompleteDTO dto = convertToDto(propiedad, imagenes);
            propiedadesDto.add(dto);
        }

        return propiedadesDto;
    }


    // UPDATE

    @Transactional
    public PropiedadesEntity actualizarPropiedad(PropiedadesDTO editarPropiedadDto, List<MultipartFile> nuevasImagenes, List<Long> idsAEliminar) throws NotFoundException {
        try {
            PropiedadesEntity propiedad = propiedadesRepository.findById(editarPropiedadDto.getIdPropiedad())
                    .orElseThrow(() -> new NotFoundException("Propiedad no encontrada con id " + editarPropiedadDto.getIdPropiedad()));

            // Actualiza campos
            propiedad.setPrecio(editarPropiedadDto.getPrecio());
            propiedad.setBaños(editarPropiedadDto.getBaños());
            propiedad.setCiudad(editarPropiedadDto.getCiudad());
            propiedad.setSector(editarPropiedadDto.getSector());
            propiedad.setTipoInmueble(editarPropiedadDto.getTipoInmueble());
            propiedad.setEstado(editarPropiedadDto.getEstado());
            propiedad.setHabitaciones(editarPropiedadDto.getHabitaciones());
            propiedad.setParqueadero(editarPropiedadDto.isParqueadero());
            propiedad.setCuartoUtil(editarPropiedadDto.isCuartoUtil());
            propiedad.setPiscina(editarPropiedadDto.isPiscina());
            propiedad.setVigilancia(editarPropiedadDto.isVigilancia());
            propiedad.setBalcon(editarPropiedadDto.isBalcon());
            propiedad.setJacuzzi(editarPropiedadDto.isJacuzzi());
            propiedad.setAscensor(editarPropiedadDto.isAsensor());
            propiedad.setCondicion(editarPropiedadDto.isCondicion());
            propiedad.setDescripcion(editarPropiedadDto.getDescripcion());
            propiedad.setDisponibilidad(editarPropiedadDto.isDisponibilidad());
            propiedad.setAreaMetros(editarPropiedadDto.getAreaMetros());

            // Actualiza propietario si se envía uno nuevo

            if (editarPropiedadDto.getIdPropietario() != null) {
                Long idPropietario = editarPropiedadDto.getIdPropietario();
                PropietariosEntity propietario = propietariosRepository.findById(idPropietario)
                        .orElseThrow(() -> new NotFoundException("Propietario no encontrado con id " + idPropietario));
                propiedad.setPropietario(propietario);
            }

            if (editarPropiedadDto.getTemporalidades() != null) {
                Long idTempo = editarPropiedadDto.getTemporalidades().getIdTemporalidad();

                boolean existTempo = temporalidadesRepository.existsById(idTempo);

                if (!existTempo) {
                    new NotFoundException("Temporalidad no encontrada");
                }else {
                    TemporalidadesEntity newTempo = temporalidadesService.update(editarPropiedadDto.getTemporalidades());

                    propiedad.setTemporalidades(newTempo);
                }
            }

            // Actualiza coordenadas si se proporcionan

            if (editarPropiedadDto.getLatitude() != null && editarPropiedadDto.getLongitude() != null) {
                GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);
                Point newLocation = geometryFactory.createPoint(new Coordinate(
                        editarPropiedadDto.getLongitude(), editarPropiedadDto.getLatitude()));
                newLocation.setSRID(4326);
                propiedad.setCoordenadas(newLocation);
            }

            // Guarda la propiedad actualizada

            PropiedadesEntity propiedadActualizada = propiedadesRepository.save(propiedad);

            // Si se enviaron nuevos archivos, los sube y reemplaza los anteriores
            if (nuevasImagenes != null && !nuevasImagenes.isEmpty()) {
                imagenService.updateImages(
                        editarPropiedadDto.getIdPropiedad(),
                        nuevasImagenes,
                        idsAEliminar // puede venir null si no eliminas nada
                );
            }

            return propiedadActualizada;

        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar la propiedad: " + e.getMessage(), e);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    // DELETE

    public void eliminarPropiedadPorId(Long idPropiedad) throws IOException {
        Optional<PropiedadesEntity> propiedadOpt = propiedadesRepository.findById(idPropiedad);

        if (propiedadOpt.isEmpty()) {
            throw new NotFoundException("Propiedad no encontrada con id " + idPropiedad);
        }

        // Eliminar imágenes asociadas de S3 y base de datos
        List<Imagen> imagenes = imagenService.getImagesWithUrls(idPropiedad);
        for (Imagen imagen : imagenes) {
            imagenService.deleteImage(imagen.getIdImagen());
        }

        // Eliminar propiedad
        propiedadesRepository.deleteById(idPropiedad);
    }


    private PropiedadesCompleteDTO convertToDto(PropiedadesEntity propiedadesEntity, List<Imagen> imagenes) {
        PropiedadesCompleteDTO dto = new PropiedadesCompleteDTO();

        dto.setIdPropiedad(propiedadesEntity.getIdPropiedad());
        dto.setPrecio(propiedadesEntity.getPrecio());
        dto.setCiudad(propiedadesEntity.getCiudad());
        dto.setSector(propiedadesEntity.getSector());
        dto.setCodigo(propiedadesEntity.getCodigo());
        dto.setTipoInmueble(propiedadesEntity.getTipoInmueble());
        dto.setEstado(propiedadesEntity.getEstado());
        dto.setHabitaciones(propiedadesEntity.getHabitaciones());
        dto.setBaños(propiedadesEntity.getBaños());
        dto.setParqueadero(propiedadesEntity.isParqueadero());
        dto.setCuartoUtil(propiedadesEntity.isCuartoUtil());
        dto.setPiscina(propiedadesEntity.isPiscina());
        dto.setVigilancia(propiedadesEntity.isVigilancia());
        dto.setBalcon(propiedadesEntity.isBalcon());
        dto.setJacuzzi(propiedadesEntity.isJacuzzi());
        dto.setAsensor(propiedadesEntity.isAscensor());
        dto.setCondicion(propiedadesEntity.isCondicion());
        dto.setDescripcion(propiedadesEntity.getDescripcion());
        dto.setLatitude(propiedadesEntity.getLatitude());
        dto.setLongitude(propiedadesEntity.getLongitude());
        dto.setDisponibilidad(propiedadesEntity.isDisponibilidad());
        dto.setAreaMetros(propiedadesEntity.getAreaMetros());
        dto.setTemporalidades(propiedadesEntity.getTemporalidades());
        dto.setIdPropietario(propiedadesEntity.getPropietario().getIdPropietario());

        dto.setImages(imagenes);

        return dto;
    }

    private static final String CARACTERES = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int LONGITUD_CODIGO = 10; // Puedes ajustar la longitud


    private String generarCodigoAleatorio() {
        SecureRandom random = new SecureRandom();
        StringBuilder codigo = new StringBuilder(LONGITUD_CODIGO);

        for (int i = 0; i < LONGITUD_CODIGO; i++) {
            int index = random.nextInt(CARACTERES.length());
            codigo.append(CARACTERES.charAt(index));
        }

        return codigo.toString();
    }



}