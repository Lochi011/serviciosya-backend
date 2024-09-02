package com.serviciosya.serviciosya_backend.business.entities;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.Date;
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

    @ManyToMany(mappedBy = "usuarioOfertante")
    private List<Rubro> rubros;

    @OneToMany(mappedBy = "usuarioOfertante")
    private List<Servicio> servicios;

    public UsuarioOfertante(Long cedula, String nombre, String apellido, String direccion, String email, String telefono, String contrasena, Date fechaCreacion, String genero, Date fechaNacimiento) {
        super(cedula, nombre, apellido, direccion, email, telefono, contrasena, fechaCreacion, genero, fechaNacimiento);
        this.servicios = new ArrayList<>();
        this.rubros = new ArrayList<>() {

        };

    }

}
