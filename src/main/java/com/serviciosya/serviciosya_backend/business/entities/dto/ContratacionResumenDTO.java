package com.serviciosya.serviciosya_backend.business.entities.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContratacionResumenDTO {

    private Long id;

    private String nombreDemandante;

    private String apellidoDemandante;

    private String nombreServicio;

    private LocalDate fechaContratacion;

    private String hora;

    private String estado;

    private String nombreRubro;


}
