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
    private String ci;  // Cédula como ID principal
    @Column
    private String nombre;
    @Column
    private String direccion;
    @Column
    private String email;
    @Column
    private String telefono;
    @Column
    private String contrasena;
    @Temporal(TemporalType.DATE)
    @Column
    private String fechaCreacion;

    @OneToMany(mappedBy = "usuario")
    private List<Reseña> reseñas;



}
