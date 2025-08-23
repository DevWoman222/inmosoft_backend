package com.InmoSoft.InmoSoft_Proyect.model.DTO;

public class JwtResponseDTO {

    private String accessToken;

    public JwtResponseDTO(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
