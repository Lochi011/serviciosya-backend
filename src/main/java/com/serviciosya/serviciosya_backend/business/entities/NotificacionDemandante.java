package com.serviciosya.serviciosya_backend.business.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class NotificacionDemandante {

    @jakarta.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_demandante_id")
    private UsuarioDemandante usuarioDemandante;

    @ManyToOne
    @JoinColumn(name = "contratacion_id")
    private Contratacion contratacion;

    private String mensaje;


    private boolean leido;

    private  boolean esMensaje;

    //private boolean esRechazo;

    private LocalDateTime fechaCreacion;

    public void setId(Long id) {

        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
