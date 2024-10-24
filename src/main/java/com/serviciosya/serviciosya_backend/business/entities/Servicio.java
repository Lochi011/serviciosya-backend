package com.serviciosya.serviciosya_backend.business.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;
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
    @GeneratedValue(generator = "servicio_id")
    @GenericGenerator(name = "servicio_id", strategy = "increment")
    private Long id;

    private String nombre;

    private String descripcion;

    private int precio;

    private float puntuacion;

    private String horaDesde; // Almacena la hora desde la que comienza el servicio

    private String horaHasta; // Almacena la hora hasta la que termina el servicio

    private int duracionServicio; // Almacena la duración del servicio en minutos o en la unidad que decidas

    private Date fechaCreacion;

    // Persistencia de una lista de etiquetas
    @ElementCollection
    @CollectionTable(name = "servicio_etiquetas", joinColumns = @JoinColumn(name = "servicio_id"))
    @Column(name = "etiqueta")
    private List<String> etiquetas;

    @ManyToOne(fetch = FetchType.LAZY) // Usar LAZY para mejorar el rendimiento si es necesario
    @JoinColumn(name = "usuario_ofertante_id")
    @JsonBackReference
    private UsuarioOfertante usuarioOfertante;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "rubro_id") // Cambiado de sub_rubro_id a rubro_id
    private Rubro rubro; // Cambiado de SubRubro a Rubro

    @OneToMany(mappedBy = "servicio", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Contratacion> contrataciones;



    // Nuevos campos para almacenar los días seleccionados, hora desde, hora hasta, y duración del servicio
    @ElementCollection
    @CollectionTable(name = "servicio_dias", joinColumns = @JoinColumn(name = "servicio_id"))
    @Column(name = "dia")
    private List<String> diasSeleccionados; // Almacena los días seleccionados




}

