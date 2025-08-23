package com.InmoSoft.InmoSoft_Proyect.repository;
import com.InmoSoft.InmoSoft_Proyect.model.Imagen;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ImagenRepository extends JpaRepository<Imagen, Long> {

    List<Imagen> findAllImagenByPropiedadIdPropiedad(Long idPropiedad);


}
