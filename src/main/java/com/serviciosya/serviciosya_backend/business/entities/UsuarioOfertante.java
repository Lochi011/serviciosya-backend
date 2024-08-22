package com.serviciosya.serviciosya_backend.business.entities;


import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "usuarios_ofertantes")
public class UsuarioOfertante extends Usuario {

    @ManyToMany (mappedBy = "usuarioOfertante")
    @JoinTable(
            name = "usuario_ofertante_rubro",
            joinColumns = @JoinColumn(name = "usuario_ofertante_id"),
            inverseJoinColumns = @JoinColumn(name = "rubro_id")
    )
    private List <Rubro> rubros;

    @OneToMany(mappedBy = "usuarioOfertante")
    private List<Servicio> servicios;

}
