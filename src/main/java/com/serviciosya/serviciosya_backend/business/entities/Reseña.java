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
@Table(name = "reseñas")
public class Reseña {

    @Id
    @GeneratedValue
    @GenericGenerator(name = "reseña_id", strategy = "increment")
    private Long id;

    private String comentario;

    private float puntuacion;

    @Temporal(TemporalType.DATE)
    private Date fechaPublicacion;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private UsuarioDemandante usuarioDemandante;

}


