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
@Table(name = "tarjetas")
public class Tarjeta {

    @Id
    @GeneratedValue
    @GenericGenerator(name = "tarjeta_id", strategy = "increment")
    private Long id;

    private Long numero;

    @Temporal(TemporalType.DATE)
    private Date fechaExpiracion;

    private String nombreTitular;

    private int CVV;

    private String tipo;

    private String bancoEmisor;

    private String direccionFacturacion;

    @ManyToOne
    @JoinColumn(name="üsuario_demandande_id")
    private UsuarioDemandante usuarioDemandante;

    @OneToMany(mappedBy = "tarjeta")
    private List<Pago> pagos;


}
