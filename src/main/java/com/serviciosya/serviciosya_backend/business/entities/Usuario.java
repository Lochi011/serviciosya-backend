package com.serviciosya.serviciosya_backend.business.entities;


    import jakarta.persistence.*

@Entity
public class Usuario {
    @Id
    private String ci;  // Cédula como ID principal
    private String nombre;
    private String direccion;
    private String email;
    private String telefono;
    private String contrasena;
    private String fechaCreacion;

    @OneToMany(mappedBy = "usuario")
    private List<Reseña> reseñas;



}
