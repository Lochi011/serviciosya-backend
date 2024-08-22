package com.serviciosya.serviciosya_backend.business.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "rubros")
public class Rubro {
    @Id
    @GeneratedValue
    @GenericGenerator(name="rubro_id", strategy = "increment")
    private Long id;

    private String nombre;


}
