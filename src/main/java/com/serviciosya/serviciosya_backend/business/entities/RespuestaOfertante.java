package com.serviciosya.serviciosya_backend.business.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Builder
@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
public class RespuestaOfertante {
    @Id
    @GeneratedValue
    @GenericGenerator(name = "reseña_id", strategy = "increment")
    private Long id;

    private String mensaje;

    private String email;

    private String telefono;

    @OneToOne
    @JoinColumn(name = "contratacion_id")  // Nombre de la columna en la tabla RespuestaOfertante
    private Contratacion contratacion;


}