package com.serviciosya.serviciosya_backend.business.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.sql.Time;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "contrataciones")
public class Contratacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    @ManyToOne
    @JoinColumn(name = "id_demandante", nullable = false)
    private UsuarioDemandante demandante;

    @ManyToOne
    @JoinColumn(name = "id_ofertante", nullable = false)
    private UsuarioOfertante ofertante;

    @ManyToOne
    @JoinColumn(name = "id_servicio", nullable = false)
    private Servicio servicio;

    @OneToOne(mappedBy = "contratacion")
    private Pago pago;

    @Column(nullable = true)
    private LocalDate fechaContratacion;

    @Column(nullable = true)
    private String direccion;


    @Enumerated(EnumType.STRING)

    private EstadoContratacion estado;

    public enum EstadoContratacion {
        PENDIENTE,
        ACEPTADA,
        RECHAZADA,
        PAGADA,
        TERMINADA;
    }



    @Column(name = "direccion_servicio", nullable = false)
    private String direccion;

    @Column(name = "apartamento")
    private String apartamento;

    @Column(name = "hora", nullable = false)
    private String hora;

    @Column(name= "comentario", nullable = false)
    private String comentario;






}