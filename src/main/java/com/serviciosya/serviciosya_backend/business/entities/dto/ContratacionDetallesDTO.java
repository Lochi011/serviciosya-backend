package com.serviciosya.serviciosya_backend.business.entities.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContratacionDetallesDTO {

    private Long id;
    private String nombreDemandante;
    private String apellidoDemandante;
    private String emailDemandante;
    private String direccion;
    private String apartamento;
    private LocalDate fechaContratacion;
    private String hora;
    private String comentario;
    private String nombreServicio;
    private int precioServicio;
    private String estado;
    private String nombreRubro;



    // Add other relevant fields if necessary, such as service duration, etc.
}
