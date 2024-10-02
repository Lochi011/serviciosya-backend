package com.serviciosya.serviciosya_backend.business.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.management.relation.Role;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo", discriminatorType = DiscriminatorType.STRING)
@Table(name = "usuarios")
public class Usuario implements UserDetails {
    @Id
    @GeneratedValue
    @GenericGenerator(name = "usuario_id", strategy = "increment")
    private Long id;

    @Column(unique = true)
    private Long cedula;

    private String nombre;

    private String apellido;

    private String direccion;

    @Column(unique = true, nullable = false)
    private String email;

    private String telefono;

    private String contrasena;

    private String genero;

    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;

    @Temporal(TemporalType.DATE)
    private Date fechaCreacion;

    @Enumerated(EnumType.STRING)
    Role role;

    public enum Role{
        ADMINISTRADOR,
        DEMANDANTE,
        OFERTANTE,
    }




    public Usuario(Long cedula, String nombre, String apellido, String direccion, String email, String telefono, String contrasena, Date fechaCreacion, String genero, Date fechaNacimiento) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.apellido = apellido;
        this.direccion = direccion;
        this.email = email;
        this.telefono = telefono;
        this.contrasena = contrasena;
        this.fechaCreacion = fechaCreacion;
        this.genero = genero;
        this.fechaNacimiento = fechaNacimiento;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority((role.name())));
    }

    @Override
    public String getPassword() {
        return contrasena;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }



}
