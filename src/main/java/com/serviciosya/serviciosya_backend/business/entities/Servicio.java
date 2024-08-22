package com.serviciosya.serviciosya_backend.business.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "servicios")
public class Servicio {
    @Id
    @GeneratedValue
    @GenericGenerator(name = "servicio_id", strategy = "increment")
    private Long id;

    private String nombre;

    private String descripcion;

    private int precio;

    private float puntuacion;

    private List<String> etiquetas;

    @ManyToOne
    @JoinColumn(name = "usuario_ofertante_id")
    private UsuarioOfertante usuarioOfertante;

    @ManyToOne
    @JoinColumn(name = "sub_rubro_id")
    private SubRubro subRubro;


}
