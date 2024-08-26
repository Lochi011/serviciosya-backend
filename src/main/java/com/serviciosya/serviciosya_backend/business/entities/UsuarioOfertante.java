package com.serviciosya.serviciosya_backend.business.entities;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
//@Table(name = "usuarios_ofertantes")
@DiscriminatorValue("OFERTANTE")
public class UsuarioOfertante extends Usuario {

    @ManyToMany (mappedBy = "usuarioOfertante")
    private List <Rubro> rubros;

    @OneToMany(mappedBy = "usuarioOfertante")
    private List<Servicio> servicios;

}
