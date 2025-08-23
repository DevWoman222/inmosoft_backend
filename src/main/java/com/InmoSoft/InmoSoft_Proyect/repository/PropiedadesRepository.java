package com.InmoSoft.InmoSoft_Proyect.repository;

import com.InmoSoft.InmoSoft_Proyect.model.PropiedadesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PropiedadesRepository extends JpaRepository<PropiedadesEntity, Long> {

    boolean existsById(Long idPropiedad);

    @Query(value = """ 
            SELECT * FROM propiedades 
            WHERE ST_DWithin(
                ubicacion,
                ST_SetSRID(ST_MakePoint(:lon, :lat), 4326)::geography,
                :radius
            ) 
            """, nativeQuery = true)
    List<PropiedadesEntity> encontrarPropiedadCercanaPor(
            @Param("lat") double lat,
            @Param("lon") double lon,
            @Param("radius") double radiusInMeters
    );


}