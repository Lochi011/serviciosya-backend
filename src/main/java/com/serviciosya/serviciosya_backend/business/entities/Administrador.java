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
//@NoArgsConstructor
@SuperBuilder
@Entity
@DiscriminatorValue("ADMINISTRADOR")
//@Table(name = "usuarios_demandantes")
public class Administrador extends Usuario {



}
