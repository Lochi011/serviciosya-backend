package com.serviciosya.serviciosya_backend.business.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.management.Notification;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@SuperBuilder
@Entity
@DiscriminatorValue("OFERTANTE")
public class UsuarioOfertante extends Usuario {

    @ManyToMany(mappedBy = "usuariosOfertantes", fetch = FetchType.LAZY)
    @JsonBackReference // Indica que es la parte inversa de la relación para evitar recursión
    private List<Rubro> rubros;

    @OneToMany(mappedBy = "usuarioOfertante", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore // Evitar problemas de referencia cruzada en serialización
    private List<Servicio> servicios;

    @OneToMany(mappedBy = "usuarioOfertante", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonBackReference // Evita recursión en la serialización de solicitudes
    private List<SolicitudRubro> solicitudesRubro;

    @OneToMany(mappedBy = "ofertante", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Contratacion> contrataciones;

    @OneToMany(mappedBy = "usuarioOfertante", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Notificacion> notificaciones;

    public void agregarRubro(Rubro rubro) {
        if (this.rubros == null) {
            this.rubros = new ArrayList<>();
        }
        if (!this.rubros.contains(rubro)) {
            this.rubros.add(rubro);
            rubro.getUsuariosOfertantes().add(this);
        }
    }

    public void eliminarRubro(Rubro rubro) {
        if (this.rubros != null && this.rubros.contains(rubro)) {
            this.rubros.remove(rubro);
            rubro.getUsuariosOfertantes().remove(this);
        }
    }

    public UsuarioOfertante(Long cedula, String nombre, String apellido, String direccion, String email, String telefono, String contrasena, Date fechaCreacion, String genero, Date fechaNacimiento) {
        super(cedula, nombre, apellido, direccion, email, telefono, contrasena, fechaCreacion, genero, fechaNacimiento);
        this.servicios = new ArrayList<>();
        this.rubros = new ArrayList<>();
        this.solicitudesRubro = new ArrayList<>();
        this.contrataciones = new ArrayList<>();
        this.notificaciones = new ArrayList<>();
    }

    public UsuarioOfertante(){
        this.servicios = new ArrayList<>();
        this.rubros = new ArrayList<>();
        this.solicitudesRubro = new ArrayList<>();
        this.contrataciones = new ArrayList<>();
        this.notificaciones = new ArrayList<>();

    }
}
