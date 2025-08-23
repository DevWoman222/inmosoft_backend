package com.InmoSoft.InmoSoft_Proyect.service;

import com.InmoSoft.InmoSoft_Proyect.model.TemporalidadesEntity;
import com.InmoSoft.InmoSoft_Proyect.repository.TemporalidadesRepository;
import com.InmoSoft.InmoSoft_Proyect.infraestructur.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Service
public class TemporalidadesService {

    //Atributos

    private final TemporalidadesRepository temporalidadesRepository;

    //Constructor
    @Autowired
    public TemporalidadesService(TemporalidadesRepository temporalidadesRepository) {
        this.temporalidadesRepository = temporalidadesRepository;
    }


    // GET - obtener por ID
    public Optional<TemporalidadesEntity> getById(Long idTempo) {
        return temporalidadesRepository.findById(idTempo);
    }

    // POST - guardar
    public TemporalidadesEntity save(TemporalidadesEntity tempo) {

        TemporalidadesEntity newTempo = new TemporalidadesEntity();

        newTempo.setDisponibilidad(tempo.getDisponibilidad());
        newTempo.setFijo(tempo.isFijo());

        return temporalidadesRepository.saveAndFlush(newTempo);
    }

    // PUT - editar
    public TemporalidadesEntity update(TemporalidadesEntity tempo) throws Throwable {
        TemporalidadesEntity existing = temporalidadesRepository.findById(tempo.getIdTemporalidad())
                .orElseThrow(() -> new NotFoundException("Temporalidad no encontrada"));

        existing.setDisponibilidad(tempo.getDisponibilidad());
        existing.setFijo(tempo.isFijo());

        return temporalidadesRepository.saveAndFlush(existing);
    }

    // DELETE - eliminar
    public void delete(Long idTempo) {
        temporalidadesRepository.deleteById(idTempo);
    }
}
