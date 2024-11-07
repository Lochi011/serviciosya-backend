package com.serviciosya.serviciosya_backend.business.entities.dto;

import com.serviciosya.serviciosya_backend.business.entities.Reseña;
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

    private String fotoPerfil;

    private List<ServicioDto> servicios;

    private List<String> rubros;

    private float puntuacionGLobal;

    private int cantidadServicios;

    private int cantidadContratacionesTerminadas;

    private List<ReseñaDto> resñas;

    private String descripcion;


}
