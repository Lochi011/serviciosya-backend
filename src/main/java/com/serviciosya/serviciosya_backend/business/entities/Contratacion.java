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
    @GeneratedValue(generator = "contratacion_id")
    @GenericGenerator(name = "contratacion_id", strategy = "increment")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_demandante", nullable = false)
    private UsuarioDemandante demandante;

    @ManyToOne
    @JoinColumn(name = "id_servicio", nullable = false)
    private Servicio servicio;

    @OneToMany(mappedBy = "contratacion", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Pago> pagos;

    @Column(nullable = false)
    private LocalDate fechaContratacion;

    @Column(nullable = false)
    private String direccion;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoContratacion estado;

    public enum EstadoContratacion {
        PENDIENTE,
        ACEPTADA,
        RECHAZADA,
        PAGADA,
        TERMINADA;
    }

    @Column(name = "fecha_servicio", nullable = false)
    private LocalDate fechaServicio;



}