package com.serviciosya.serviciosya_backend.business.entities.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificacionDemandanteDTO {

        private Long id;

        private String mensaje;

        private LocalDateTime fechaCreacion;

        private boolean leido;

        private Long usuarioDemandanteId;

        private Long contratacionId;

        private String nombreServicio;

        private String nombreOfertante;

        private String apellidoOfertante;
        ;
}
