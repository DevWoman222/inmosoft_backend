package com.InmoSoft.InmoSoft_Proyect.service;

import com.InmoSoft.InmoSoft_Proyect.infraestructur.security.CustomUserDetails;
import com.InmoSoft.InmoSoft_Proyect.model.AdministradoresEntity;
import com.InmoSoft.InmoSoft_Proyect.model.PropietariosEntity;
import com.InmoSoft.InmoSoft_Proyect.model.UsuariosEntity;
import com.InmoSoft.InmoSoft_Proyect.repository.AdministradoresRepository;
import com.InmoSoft.InmoSoft_Proyect.repository.PropietariosRepository;
import com.InmoSoft.InmoSoft_Proyect.repository.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

    // Recibe el email devuelve un objeto CustomUserDetails con su contraseña y rol o lanza excepción si no existe

@Service("userDetailService")
@Transactional(readOnly = true)
public class AuthenticatedService implements UserDetailsService {

    private PropietariosRepository propietariosRepository; // Busca en nuestra bd si existe el usuariio

    private AdministradoresRepository administradoresRepository;

    private UsuariosRepository usuariosRepository;

    @Autowired
    public AuthenticatedService(PropietariosRepository propietariosRepository, AdministradoresRepository administradoresRepository, UsuariosRepository usuariosRepository) {
        this.propietariosRepository = propietariosRepository;
        this.administradoresRepository = administradoresRepository;
        this.usuariosRepository = usuariosRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {


        // Buscar primero si es un User
        Optional<UsuariosEntity> usuarioOpt = usuariosRepository.findByEmail(email);
        if (usuarioOpt.isPresent()) {
            return new CustomUserDetails(usuarioOpt.get());
        }

        // Si no es usuario, buscar si es Admin
        Optional<AdministradoresEntity> administrador = administradoresRepository.findByEmail(email);
        if (administrador.isPresent()) {

            return new CustomUserDetails(administrador.get());
        }

        // Si no es usuario, buscar si es Owner
        Optional<PropietariosEntity> propietario = propietariosRepository.findByEmail(email);
        if (propietario.isPresent()) {

            return new CustomUserDetails(propietario.get());
        }

        // Si no se encuentra ni usuario ni admin ni owner
        throw new UsernameNotFoundException("Email no registrado: " + email);
    }
}
