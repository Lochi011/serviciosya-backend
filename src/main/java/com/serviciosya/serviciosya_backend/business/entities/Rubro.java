package com.serviciosya.serviciosya_backend.business.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "rubros")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) // Ignorar proxies de Hibernate
public class Rubro {

    @Id
    @GeneratedValue(generator = "rubro_id")
    @GenericGenerator(name = "rubro_id", strategy = "increment")
    private Long id;

    @Column(unique = true)
    private String nombre;


    @OneToMany(mappedBy = "rubro", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Servicio> servicios; // Lista de servicios relacionados con este rubro


    @OneToMany(mappedBy = "rubro", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonBackReference // Indica que es la parte inversa de la relación para evitar recursión
    private List<SolicitudRubro> solicitudesRubro;

    @ManyToMany
    @JoinTable(
            name = "usuario_ofertante_rubro",
            joinColumns = @JoinColumn(name = "rubro_id"),
            inverseJoinColumns = @JoinColumn(name = "usuario_ofertante_id")
    )
    @JsonIgnore // Evitar la serialización en bucle con usuariosOfertantes
    @JsonBackReference
    private List<UsuarioOfertante> usuariosOfertantes;
}
