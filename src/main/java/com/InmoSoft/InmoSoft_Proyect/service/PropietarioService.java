package com.InmoSoft.InmoSoft_Proyect.service;

import com.InmoSoft.InmoSoft_Proyect.infraestructur.exceptions.ConflictException;
import com.InmoSoft.InmoSoft_Proyect.infraestructur.exceptions.NotFoundException;
import com.InmoSoft.InmoSoft_Proyect.model.PropietariosEntity;
import com.InmoSoft.InmoSoft_Proyect.repository.PropietariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class PropietarioService {

    //Atributos

    private PasswordEncoder passwordEncoder;

    private PropietariosRepository propietariosRepository;

    //Constructor
    @Autowired
    public PropietarioService(PropietariosRepository propietariosRepository, PasswordEncoder passwordEncoder) {
        this.propietariosRepository = propietariosRepository;
        this.passwordEncoder = passwordEncoder;
    }
    // Metodos //

//READ - LEER
    public List<PropietariosEntity> getAllOwners() {

        List<PropietariosEntity> owners = propietariosRepository.findAll();

        return owners;
    }

    public Optional<PropietariosEntity> getByOwner(Long idPropietario) {

        Optional<PropietariosEntity> owner = propietariosRepository.findById(idPropietario);

        return owner;
    }
// CREATE - CREAR
    public String saveOwner(PropietariosEntity owner) throws ConflictException {

        if (propietariosRepository.existsByEmail(owner.getEmail())) {
            throw new ConflictException("El usuario ya existe");

        }
        //Crear un nuevo objeto "propietarios"
        PropietariosEntity newOwner = new PropietariosEntity();

        newOwner.setPrimerNombre(owner.getPrimerNombre());
        newOwner.setSegundoNombre(owner.getSegundoNombre());
        newOwner.setPrimerApellido(owner.getPrimerApellido());
        newOwner.setSegundoApellido(owner.getSegundoApellido());
        newOwner.setContraseña(passwordEncoder.encode(owner.getContraseña()));
        newOwner.setCedula(owner.getCedula());
        newOwner.setEmail(owner.getEmail());
        newOwner.setTelefono(owner.getTelefono());
        newOwner.setEstado(owner.isEstado());
        newOwner.setRol("USER");

        propietariosRepository.save(newOwner);

        return "Se creo satisfastoriamente el propietario: " + owner.getPrimerNombre() + " " +
                owner.getPrimerApellido();

    }
// UDPDATE - ACTUALIZACIONES

    public void editOwner(PropietariosEntity owner) throws NotFoundException {
        try {
            PropietariosEntity editOwner = propietariosRepository.findById(owner.getIdPropietario())
                    .orElseThrow(() -> new NotFoundException("Propietario no encontrado"));

            editOwner.setIdPropietario(owner.getIdPropietario());
            editOwner.setPrimerNombre(owner.getPrimerNombre());
            editOwner.setSegundoNombre(owner.getSegundoNombre());
            editOwner.setPrimerApellido(owner.getPrimerApellido());
            editOwner.setSegundoApellido(owner.getSegundoApellido());
            editOwner.setContraseña(passwordEncoder.encode(owner.getContraseña()));
            editOwner.setCedula(owner.getCedula());
            editOwner.setEmail(owner.getEmail());
            editOwner.setTelefono(owner.getTelefono());
            editOwner.setEstado(owner.isEstado());

            propietariosRepository.save(editOwner);

        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar el propietario: " + e.getMessage(), e);
        }
    }


    //DELETE - BORRAR
    public void deleteOwner (Long idPropietario){
        propietariosRepository.deleteById(idPropietario);
    }


}






//CREATE - CREAR
//READ - LEER
//UPDATE - ACTUALIZAR
//DELETE - BORRAR