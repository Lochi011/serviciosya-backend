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
@DiscriminatorValue("OFERTANTE")
public class UsuarioOfertante extends Usuario {

    @ManyToMany(mappedBy = "usuariosOfertantes", fetch = FetchType.LAZY)
    private List<Rubro> rubros; // Relación inversa Many-to-Many

    @OneToMany(mappedBy = "usuarioOfertante", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Servicio> servicios;

    @OneToMany(mappedBy = "usuarioOfertante", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SolicitudRubro> solicitudesRubro;

    public void agregarRubro(Rubro rubro) {
        if (!this.rubros.contains(rubro)) {
            this.rubros.add(rubro);
            rubro.getUsuariosOfertantes().add(this); // Agregar en el otro lado de la relación
        }
    }

    // Método para eliminar un rubro (si es necesario)
    public void eliminarRubro(Rubro rubro) {
        if (this.rubros.contains(rubro)) {
            this.rubros.remove(rubro);
            rubro.getUsuariosOfertantes().remove(this); // Quitar en el otro lado de la relación
        }
    }


    public UsuarioOfertante(Long cedula, String nombre, String apellido, String direccion, String email, String telefono, String contrasena, Date fechaCreacion, String genero, Date fechaNacimiento) {
        super(cedula, nombre, apellido, direccion, email, telefono, contrasena, fechaCreacion, genero, fechaNacimiento);
        this.servicios = new ArrayList<>();
        this.rubros = new ArrayList<>();
    }
}
