package com.serviciosya.serviciosya_backend.business.entities.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ServicioDto {

    private Long id;

    private String nombre;

    private int precio;

    private float puntuacion;

    private String descripcion;

    private String horaDesde;

    private String horaHasta;

    private int duracionServicio;

    private List<String> etiquetas;



    private Long usuarioOfertanteId; // Cambiado a solo ID para simplificar

    private String usuarioOfertanteNombre; // Cambiado a nombre para simplificar

    private String usuarioOfertanteApellido; // Cambiado a apellido para simplificar



    private String rubroNombre;       // Cambiado a nombre para simplificar

    private List<String> diasSeleccionados;

    private String fotoPerfil;

}