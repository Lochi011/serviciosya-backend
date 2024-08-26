package com.serviciosya.serviciosya_backend.business.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@DiscriminatorValue("DEMANDANTE")
//@Table(name = "usuarios_demandantes")
public class UsuarioDemandante extends Usuario {

    @ManyToMany
    @JoinTable(
            name = "contrataciones",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "servicio_id")
    )
    private List <Servicio> servicios;

    @OneToMany(mappedBy = "usuarioDemandante")
    private List<Pago> pagos;

    @OneToMany(mappedBy = "usuarioDemandante")
    private List<Tarjeta> tarjetas;


}
