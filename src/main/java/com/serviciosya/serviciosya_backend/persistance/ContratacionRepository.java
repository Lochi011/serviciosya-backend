package com.serviciosya.serviciosya_backend.persistance;


import com.serviciosya.serviciosya_backend.business.entities.Contratacion;
import com.serviciosya.serviciosya_backend.business.entities.Servicio;
import com.serviciosya.serviciosya_backend.business.entities.UsuarioDemandante;
import com.serviciosya.serviciosya_backend.business.entities.UsuarioOfertante;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ContratacionRepository extends CrudRepository<Contratacion, Long> {
    Optional<Contratacion> findByDemandanteAndOfertanteAndServicioAndFechaContratacionAndEstado(UsuarioDemandante demandante, UsuarioOfertante ofertante, Servicio servicio, LocalDate fechaContratacion, Contratacion.EstadoContratacion estado);

    List<Contratacion> findByDemandante(UsuarioDemandante usuario);

    Optional<List<Contratacion>> findAllByOfertante(UsuarioOfertante usuario);

    Optional<List<Contratacion>> findAllByOfertanteAndEstadoNot(UsuarioOfertante ofertante, Contratacion.EstadoContratacion estado);


    @Override
    Optional<Contratacion> findById(Long id);

    Optional<List<Contratacion>> findAllByDemandante(UsuarioDemandante demandante);

    Iterable<Contratacion> findAll();

    int countByOfertanteAndEstado(UsuarioOfertante ofertante, Contratacion.EstadoContratacion estadoContratacion);

    Optional<List<Contratacion>> findAllByDemandanteAndEstado (UsuarioDemandante demandante, Contratacion.EstadoContratacion estadoContratacion);
}