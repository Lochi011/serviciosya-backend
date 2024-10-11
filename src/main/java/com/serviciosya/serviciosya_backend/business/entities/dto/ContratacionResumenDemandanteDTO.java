package com.serviciosya.serviciosya_backend.business.entities.dto;

import lombok.*;

import java.time.LocalDate;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContratacionResumenDemandanteDTO {

    private Long id;

    private String nombreOfertante;

    private String apellidoOfertante;

    private String nombreServicio;

    private LocalDate fechaContratacion;

    private String hora;

    private String estado;

    private String nombreRubro;

    private String justificacionRechazo;

}
