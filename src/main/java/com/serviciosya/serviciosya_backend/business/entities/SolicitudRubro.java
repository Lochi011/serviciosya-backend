package com.serviciosya.serviciosya_backend.business.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "solicitudes_rubro")
public class SolicitudRubro {

    @Id
    @GeneratedValue
    @GenericGenerator(name = "solicitud_rubro_id", strategy = "increment")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_ofertante_id", nullable = false)
    private UsuarioOfertante usuarioOfertante;

    @ManyToOne
    @JoinColumn(name = "rubro_id", nullable = false)
    private Rubro rubro;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EstadoSolicitud estado;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_creacion", nullable = false)
    private Date fechaCreacion;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_resolucion")
    private Date fechaResolucion;

    @Column(length = 500)
    private String motivo; // Motivo de la solicitud o comentarios adicionales.

    @Column(length = 500)
    private String comentarioAdmin; // Comentario del admin en caso de rechazo.

    public enum EstadoSolicitud {
        PENDIENTE,
        APROBADA,
        RECHAZADA
    }
}
