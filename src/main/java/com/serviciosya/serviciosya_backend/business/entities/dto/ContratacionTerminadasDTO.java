package com.serviciosya.serviciosya_backend.business.entities.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContratacionTerminadasDTO {

    private Long id_contratacion;
    private String nombreServicio;
    private int precioServicio;
    private String nombreOfertante;
    private String apellidoOfertante;
    private LocalDate fechaContratacion;
    private String hora;
    private String estado;

    private Boolean isFavorite;

    private Float puntuacion;

    private String nombreRubro;
}
