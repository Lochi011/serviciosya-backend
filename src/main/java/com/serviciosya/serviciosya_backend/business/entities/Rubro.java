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
@Table(name = "rubros")
public class Rubro {
    @Id
    @GeneratedValue
    @GenericGenerator(name="rubro_id", strategy = "increment")
    private Long id;

    private String nombre;

    @OneToMany(mappedBy = "rubro")
    private List<SubRubro> subRubros;

    @ManyToMany
    @JoinTable(
            name = "usuario_ofertante_rubro",
            joinColumns = @JoinColumn(name = "rubro_id"),
            inverseJoinColumns = @JoinColumn(name = "usuario_ofertante_id")
    )
    private List<UsuarioOfertante> usuarioOfertante;






}
