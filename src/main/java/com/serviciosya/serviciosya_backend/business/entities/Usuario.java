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
@Table(name = "usuarios")
public class Usuario {
    @Id
    private Long id;
    @Column(unique = true)
    private String cedula;
    @Column
    private String nombre;
    private String direccion;
    private String email;
    private String telefono;
    private String contrasena;
    @Temporal(TemporalType.DATE)
    private String fechaCreacion;

    @OneToMany(mappedBy = "usuario")
    private List<Reseña> reseñas;



}
