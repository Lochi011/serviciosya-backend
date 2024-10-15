package com.serviciosya.serviciosya_backend.business.entities.dto;

import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PerfilOfertanteDTO {

    private Long id;

    private String nombre;

    private String apellido;

    private String direccion;

    private String genero;

    private Date fechaNacimiento;

    private List<ServicioDto> servicios;

    private List<String> rubros;




}
