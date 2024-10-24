package com.serviciosya.serviciosya_backend.business.entities.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NotifiacionDemandanteDTO {

        private Long id;

        private Long idDemandante;

        private Long idContratacion;

        private String nombreServicio;

        private String fechaContratacion;

        private String nombreRubro;
}
