package com.serviciosya.serviciosya_backend.business.controllers.auth;

import jakarta.persistence.Column;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {


    private Long cedula;

    private String nombre;

    private String apellido;

    private String direccion;

    private String email;

    private String telefono;

    private String contrasena;

    private String genero;

    private Date fechaNacimiento;

    private Date fechaCreacion;

}
