package com.serviciosya.serviciosya_backend.business.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
public class RespuestaOfertante {
    @Id
    private Long id;

    private String mensaje;

    private String email;

    private String telefono;

    @OneToOne
    @JoinColumn(name = "contratacion_id")  // Nombre de la columna en la tabla RespuestaOfertante
    private Contratacion contratacion;


}