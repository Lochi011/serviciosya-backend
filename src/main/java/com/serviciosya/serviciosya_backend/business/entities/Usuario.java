package com.serviciosya.serviciosya_backend.business.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue
    @GenericGenerator(name = "usuario_id", strategy = "increment")
    private Long id;

    @Column(unique = true)
    private String cedula;

    private String nombre;

    private String direccion;

    @Column(unique = true,nullable = false)
    private String email;

    private String telefono;

    private String contrasena;

    @Temporal(TemporalType.DATE)
    private Date fechaCreacion;

    @OneToMany(mappedBy = "usuario")
    private List<Reseña> reseñas;



}
