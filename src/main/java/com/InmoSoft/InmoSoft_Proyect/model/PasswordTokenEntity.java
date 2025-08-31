package com.InmoSoft.InmoSoft_Proyect.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;


@Entity
@Table(name = "password_tokens")
public class PasswordTokenEntity {

    //Atributos
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "id_password_token", nullable = false)
    private Long idPasswordToken;

    @Column(nullable = false, unique = true, length = 255)
    private String token;

    @Column(nullable = false, length = 255)
    private String email;

    @Column(nullable = false)
    private LocalDateTime expiration;

    private boolean used;


    //Constructor

    public PasswordTokenEntity(Long idPasswordToken, String token, String email, LocalDateTime expiration, boolean used) {
        this.idPasswordToken = idPasswordToken;
        this.token = token;
        this.email = email;
        this.expiration = expiration;
        this.used = used;
    }

    public PasswordTokenEntity() {
    }

    //Getter and setter


    public Long getIdPasswordToken() {
        return idPasswordToken;
    }

    public void setIdPasswordToken(Long idPasswordToken) {
        this.idPasswordToken = idPasswordToken;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getExpiration() {
        return expiration;
    }

    public void setExpiration(LocalDateTime expiration) {
        this.expiration = expiration;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }
}
