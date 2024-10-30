package com.serviciosya.serviciosya_backend.business.entities.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReseñaDto {

    private Long id;
    private String comentario;
    private float puntuacion;
    private String fechaPublicacion;
    private String nombreDemandante;
    private String apellidoDemandante;
}
