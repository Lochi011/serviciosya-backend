package com.serviciosya.serviciosya_backend.business.entities;

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
@Table(name = "sub_rubros")
public class SubRubro {
    @Id
    @GeneratedValue
    @GenericGenerator(name = "sub_rubro_id", strategy = "increment")
    private Long id;

    private String nombre;

    @ManyToOne
    @JoinColumn(name = "rubro_id")
    private Rubro rubro;

    @OneToMany(mappedBy = "subRubro")
    private List<Servicio> servicios;
}
