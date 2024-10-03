package com.serviciosya.serviciosya_backend.persistance;


import com.serviciosya.serviciosya_backend.business.entities.Contratacion;
import com.serviciosya.serviciosya_backend.business.entities.Servicio;
import com.serviciosya.serviciosya_backend.business.entities.UsuarioDemandante;
import com.serviciosya.serviciosya_backend.business.entities.UsuarioOfertante;
import org.springframework.cglib.core.Local;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ContratacionRepository extends CrudRepository<Contratacion, Long> {
    Optional<Contratacion> findByDemandanteAndOfertanteAndServicioAndFechaAndEstado(UsuarioDemandante demandante, UsuarioOfertante ofertante, Servicio servicio, LocalDate fecha, Contratacion.EstadoContratacion estado);

    List<Contratacion> findByDemandante(UsuarioDemandante usuario);


}