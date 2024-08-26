package com.serviciosya.serviciosya_backend.business.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;
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
