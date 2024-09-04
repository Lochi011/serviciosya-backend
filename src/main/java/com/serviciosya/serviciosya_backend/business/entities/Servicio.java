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
    @GeneratedValue(generator = "servicio_id")
    @GenericGenerator(name = "servicio_id", strategy = "increment")
    private Long id;

    private String nombre;

    private String descripcion;

    private int precio;

    private float puntuacion;

    // Persistencia de una lista de etiquetas
    @ElementCollection
    @CollectionTable(name = "servicio_etiquetas", joinColumns = @JoinColumn(name = "servicio_id"))
    @Column(name = "etiqueta")
    private List<String> etiquetas;

    @ManyToOne(fetch = FetchType.LAZY) // Usar LAZY para mejorar el rendimiento si es necesario
    @JoinColumn(name = "usuario_ofertante_id")
    private UsuarioOfertante usuarioOfertante;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sub_rubro_id")
    private SubRubro subRubro;

    @OneToMany(mappedBy = "servicio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pago> pagos;
}
