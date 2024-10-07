package com.serviciosya.serviciosya_backend.business.controllers.auth;

public class ResetPasswordRequest {
    private String token;      // Agregamos el token
    private String newPassword;

    // Constructor
    public ResetPasswordRequest() {
    }

    // Getters y Setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}

