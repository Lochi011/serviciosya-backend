package com.serviciosya.serviciosya_backend.business.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Any;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "reseñas")
public class Servicio {
    @Id
    @GeneratedValue
    @GenericGenerator(name = "servicio_id", strategy = "increment")
    private Long id;
    private String nombre;
    private String descripcion;
    private int precio;
    private float puntuacion;
    private List <String> etiquetas;


}
