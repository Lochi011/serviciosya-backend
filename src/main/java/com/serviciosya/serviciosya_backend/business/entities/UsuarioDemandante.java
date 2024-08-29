package com.serviciosya.serviciosya_backend.business.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;

import java.util.ArrayList;
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

    @OneToMany(mappedBy = "usuarioDemandante")
    private List<Reseña> reseñas;


    public UsuarioDemandante(Long cedula, String nombre, String apellido, String direccion, String email, String telefono, String contrasena, Date fechaCreacion, String genero, Date fechaNacimiento) {
        super(cedula, nombre, apellido, direccion, email, telefono, contrasena, fechaCreacion, genero, fechaNacimiento);
        this.servicios = new ArrayList<>();
        this.pagos = new ArrayList<>();
        this.tarjetas = new ArrayList<>();
        this.reseñas = new ArrayList<>() {

        };

    }


}
