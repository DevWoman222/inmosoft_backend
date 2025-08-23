package com.InmoSoft.InmoSoft_Proyect.infraestructur.security;

import com.InmoSoft.InmoSoft_Proyect.model.AdministradoresEntity;
import com.InmoSoft.InmoSoft_Proyect.model.PropietariosEntity;
import com.InmoSoft.InmoSoft_Proyect.model.UsuariosEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

    // Control y asignación de roles, puente entre las entity y spring security


public class CustomUserDetails implements UserDetails {

    private UsuariosEntity usuariosEntity;

    private AdministradoresEntity administradoresEntity;

    private PropietariosEntity propietariosEntity;

    private Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(UsuariosEntity usuariosEntity) {
        this.usuariosEntity = usuariosEntity;
        this.authorities = List.of(new SimpleGrantedAuthority(usuariosEntity.getRol()));
    }

    public CustomUserDetails(AdministradoresEntity administradoresEntity) {
        this.administradoresEntity = administradoresEntity;
        this.authorities = List.of(new SimpleGrantedAuthority(administradoresEntity.getRol()));
    }

    public CustomUserDetails(PropietariosEntity propietariosEntity) {
        this.propietariosEntity = propietariosEntity;
        this.authorities = List.of(new SimpleGrantedAuthority(propietariosEntity.getRol()));
    }

    // Métodos auxiliares-----

    public boolean esUsuario() {
        return usuariosEntity != null;
    }

    public boolean esAdministrador() {
        return administradoresEntity != null;
    }

    public boolean esPropietario() {
        return propietariosEntity != null;
    }

    public UsuariosEntity getUsuario() {
        return usuariosEntity;
    }

    public AdministradoresEntity getAdministrador() {
        return administradoresEntity;
    }

    public PropietariosEntity getPropietario() {
        return propietariosEntity;
    }


    // Métodos requeridos por UserDetails-----


    // Devuelve lista de roles
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        if (esUsuario()) {
            return usuariosEntity.getContraseña();

        } else if (esAdministrador()) {
            return  administradoresEntity.getContraseña();

        } else {
            return  propietariosEntity.getContraseña();
        }

    }

    @Override
    public String getUsername() {
        if (esUsuario()) {
            return usuariosEntity.getEmail();

        } else if (esAdministrador()) {
            return  administradoresEntity.getEmail();

        } else {
            return  propietariosEntity.getEmail();
        }
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        if (esUsuario()) {
            return usuariosEntity.isEstado();

        } else if (esAdministrador()) {
            return  administradoresEntity.isEstado();

        } else {
            return  propietariosEntity.isEstado();
        }
    }


}
