package com.serviciosya.serviciosya_backend.business.entities.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NotificacionDTO {

    private Long id;

    private String mensaje;

    private LocalDateTime fechaCreacion;

    private boolean leido;

    private Long usuarioOfertanteId;

    private Long contratacionId;

    private String nombreServicio;

    private String nombreDemandante;

    private String apellidoDemandante;

}