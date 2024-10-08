package com.serviciosya.serviciosya_backend.business.controllers.auth;

public class ChangePasswordRequest {
    private String oldPassword;
    private String newPassword;

    // Getters y setters
    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}

