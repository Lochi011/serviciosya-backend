package com.serviciosya.serviciosya_backend.business.controllers.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class LoginRequest {

    private String usuario;
    private String contraseña;


}
