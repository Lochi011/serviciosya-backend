package com.serviciosya.serviciosya_backend.business.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "notificaciones")

public class Notificacion {

    @jakarta.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_ofertante_id")
    private UsuarioOfertante usuarioOfertante;

    @ManyToOne
    @JoinColumn(name = "contratacion_id")
    @JsonIgnore
    private Contratacion contratacion;

    private String mensaje;
    private LocalDateTime fechaCreacion;
    private boolean leido;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
