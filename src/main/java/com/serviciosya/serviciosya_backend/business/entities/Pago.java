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
@Table(name = "pagos")
public class Pago {

    @Id
    @GeneratedValue
    @GenericGenerator(name = "pago_id", strategy = "increment")
    private Long id;

    @Temporal(TemporalType.DATE)
    private Date fecha;

    private int monto;

    private String estado;

    @ManyToOne
    @JoinColumn(name = "usuario_demandante_id")
    private UsuarioDemandante usuarioDemandante;



    @ManyToOne
    @JoinColumn(name ="tarjeta_id")
    private Tarjeta tarjeta;

    @ManyToOne
    @JoinColumn(name = "contratacion_id")
    private Contratacion contratacion;

}
