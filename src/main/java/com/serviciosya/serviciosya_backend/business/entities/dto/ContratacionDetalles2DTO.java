package com.serviciosya.serviciosya_backend.business.entities.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContratacionDetalles2DTO {
    private Long id;
    private String nombreOfertante;
    private String apellidoOfertante;
    private String emailOfertante;
    private String direccion;
    private String apartamento;
    private LocalDate fechaContratacion;
    private String hora;
    private String comentario;
    private String nombreServicio;
    private int precioServicio;
    private String estado;
    private String nombreRubro;
}
