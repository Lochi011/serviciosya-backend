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


    private String firstName;

    private String lastName;

    private String gender;

    private Date birthDate;

    private Long identity;

    private String accountType;

    private String address;

    private String email;

    private String password;

    private String confirmPassword;

    private String phone;


}
